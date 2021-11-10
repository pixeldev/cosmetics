package me.pixeldev.ecosmetics.plugin.cosmetic.type.creator;

import com.google.common.collect.ImmutableMap;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.effect.animation.EffectAnimationType;
import me.pixeldev.ecosmetics.api.cosmetic.permission.CosmeticPermissionFormatter;
import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle.PetParticleAnimationType;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.frame.EquipmentFrame;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.frame.SkinEquipmentFrame;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.parse.EquipmentFrameParser;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProvider;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProviderCreator;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProviderType;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.creator.CosmeticTypeCreator;
import me.pixeldev.ecosmetics.api.cosmetic.type.pet.AnimatedPetCosmeticType;
import me.pixeldev.ecosmetics.api.item.ItemParser;
import me.pixeldev.ecosmetics.plugin.util.Enums;
import me.pixeldev.ecosmetics.api.cosmetic.type.EffectCosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.pet.PetCosmeticType;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import xyz.xenondevs.particle.ParticleEffect;

import javax.inject.Inject;
import java.util.*;

import static me.pixeldev.ecosmetics.api.util.ColorUtils.parseFromString;

public class SimpleCosmeticTypeCreator implements CosmeticTypeCreator {

	private static final Map<String, Integer> EQUIPMENT_SECTIONS = ImmutableMap
		.<String, Integer>builder()
		.put("skin", 4)
		.put("chestplate", 3)
		.put("leggings", 2)
		.put("boots", 1)
		.put("hand", 0)
		.build();

	@Inject private SkinProviderCreator skinProviderCreator;
	@Inject private CosmeticPermissionFormatter permissionFormatter;
	@Inject private EquipmentFrameParser equipmentFrameParser;
	@Inject private ItemParser itemParser;

	@Override
	public CosmeticType createFromSection(CosmeticCategory category,
																				String sectionKey,
																				YamlConfigurationSection section,
																				StringBuilder details) {
		String permission = permissionFormatter.format(category, sectionKey);
		Material menuIcon = Material.getMaterial(section.getString("icon"));

		if (menuIcon == null && category != CosmeticCategory.MINIATURES) {
			details.append("Cannot parse cosmetic type '")
				.append(sectionKey).append("' because hasn't been set the menu icon.")
				.append("\n");
			return null;
		}

		switch (category) {
			case MINIATURES: {
				YamlConfigurationSection equipmentSection = section.getSection("equipment");

				if (equipmentSection == null) {
					details.append("Cannot create cosmetic type from '")
						.append(sectionKey).append("' because MINIATURES must have an equipment section.")
						.append("\n");
					return null;
				}

				YamlConfigurationSection propertiesSection = section.getSection("properties");
				boolean invisible = false;
				boolean arms = false;

				if (propertiesSection != null) {
					invisible = propertiesSection.getBoolean("invisible");
					arms = propertiesSection.getBoolean("arms");
				}

				YamlConfigurationSection particleAnimationSection = section.getSection("particle-animation");

				if (particleAnimationSection == null) {
					details.append("Cannot found particle animation section for '")
						.append(sectionKey).append("'").append("\n");
					return null;
				}

				String animationKey = particleAnimationSection.getString("type");

				if (animationKey == null) {
					details.append("Particle animation in '")
						.append(sectionKey).append("' cannot be null.")
						.append("\n");
					return null;
				}

				PetParticleAnimationType animationType = Enums.valueOf(PetParticleAnimationType.class, animationKey);

				if (animationType == null) {
					details.append("Cannot parse animation type for '")
						.append(sectionKey).append("'")
						.append("\n");
					return null;
				}

				String particleEffectKey = particleAnimationSection.getString("effect");

				if (particleEffectKey == null) {
					details.append("Particle effect in '")
						.append(sectionKey).append("' cannot be null.")
						.append("\n");
					return null;
				}

				ParticleEffect particleEffect = Enums.valueOf(ParticleEffect.class, particleEffectKey);

				if (particleEffect == null) {
					details.append("Cannot parse effect type for '")
						.append(sectionKey).append("'")
						.append("\n");
					return null;
				}

				float incrementX = particleAnimationSection.getFloat("x");
				float incrementY = particleAnimationSection.getFloat("y");
				float incrementZ = particleAnimationSection.getFloat("z");
				int goalTicks = particleAnimationSection.getInt("ticks");

				boolean animated = section.getBoolean("animated");

				if (animated) {
					List<EquipmentFrame> skinFrames = new ArrayList<>();
					List<EquipmentFrame> handFrames = new ArrayList<>();
					List<EquipmentFrame> chestFrames = new ArrayList<>();
					List<EquipmentFrame> leggingsFrames = new ArrayList<>();
					List<EquipmentFrame> bootsFrames = new ArrayList<>();

					for (Map.Entry<String, Integer> equipmentEntry : EQUIPMENT_SECTIONS.entrySet()) {
						String currentKey = equipmentEntry.getKey();
						int currentSlot = equipmentEntry.getValue();
						YamlConfigurationSection currentSection = equipmentSection.getSection(currentKey);
						switch (currentKey) {
							case "skin": {
								YamlConfigurationSection framesSection = currentSection.getSection("frames");

								if (framesSection == null) {
									SkinEquipmentFrame parsedFrame = parseSkinFrame(sectionKey, currentSection, details);

									if (parsedFrame == null) {
										return null;
									}

									skinFrames.add(parsedFrame);
								} else {
									for (String frameKey : framesSection.getKeys(false)) {
										SkinEquipmentFrame parsedFrame = parseSkinFrame(
											sectionKey, framesSection.getSection(frameKey), details
										);

										if (parsedFrame == null) {
											continue;
										}

										skinFrames.add(parsedFrame);
									}
								}
								break;
							}
							case "hand": {
								handFrames.addAll(equipmentFrameParser.parseFromSection(
									sectionKey, currentKey, currentSlot, currentSection, details
								));
								break;
							}
							case "chestplate": {
								chestFrames.addAll(equipmentFrameParser.parseFromSection(
									sectionKey, currentKey, currentSlot, currentSection, details
								));
								break;
							}
							case "leggings": {
								leggingsFrames.addAll(equipmentFrameParser.parseFromSection(
									sectionKey, currentKey, currentSlot, currentSection, details
								));
								break;
							}
							case "boots": {
								bootsFrames.addAll(equipmentFrameParser.parseFromSection(
									sectionKey, currentKey, currentSlot, currentSection, details
								));
								break;
							}
						}
					}

					return new AnimatedPetCosmeticType(
						permission, sectionKey, menuIcon, category,
						invisible, arms,
						particleEffect, animationType,
						incrementX, incrementY, incrementZ, goalTicks,
						skinFrames, handFrames, chestFrames, leggingsFrames, bootsFrames
					);
				} else {
					String skinProviderTypeKey = section.getString("skin");

					SkinProviderType skinProviderType = SkinProviderType.getByName(skinProviderTypeKey);
					SkinProvider skinProvider;

					if (skinProviderType == null) {
						details.append("Cannot create a skin provider type from cosmetic '")
							.append(sectionKey).append("' using a default one.")
							.append("\n");

						skinProvider = SkinProvider.urlProvider(
							"beb588b21a6f98ad1ff4e085c552dcb050efc9cab427f46048f18fc803475f7"
						);
					} else {
						skinProvider = skinProviderCreator.create(
							skinProviderType, section.getString("skin-value")
						);
					}

					Map<Integer, ItemStack> equipment = new HashMap<>();

					for (Map.Entry<String, Integer> equipmentEntry : EQUIPMENT_SECTIONS.entrySet()) {
						String equipmentSectionKey = equipmentEntry.getKey();

						if (equipmentSectionKey.equals("skin")) {
							 continue;
						}

						int equipmentSlot = equipmentEntry.getValue();

						YamlConfigurationSection currentEquipmentSection = equipmentSection
							.getSection(equipmentSectionKey);

						if (currentEquipmentSection == null) {
							continue;
						}

						equipment.put(equipmentSlot, itemParser.parseFromSection(currentEquipmentSection));
					}

					return new PetCosmeticType(
						permission, sectionKey, menuIcon, category,
						equipment, skinProvider,
						invisible, arms,
						particleEffect, animationType,
						incrementX, incrementY, incrementZ,
						goalTicks
					);
				}
			}
			case EFFECTS: {
				YamlConfigurationSection animationsSection = section.getSection("animations");

				if (animationsSection == null) {
					details.append("Cannot parse type '").append(sectionKey)
						.append("' because animations hasn't been set.")
						.append("\n");
					return null;
				}

				Set<String> animationKeys = animationsSection.getKeys(false);
				List<EffectCosmeticType.Data> animationTypes = new ArrayList<>();

				for (String animationKey : animationKeys) {
					YamlConfigurationSection animationSection = animationsSection.getSection(animationKey);
					String animationTypeKey = animationSection.getString("animation");

					if (animationTypeKey == null) {
						details.append("Animation type in '").append(sectionKey)
							.append("' animation: '").append(animationKey).append("' cannot be null.")
							.append("\n");
						continue;
					}

					EffectAnimationType animationType = Enums.valueOf(
						EffectAnimationType.class, animationTypeKey
					);

					if (animationType == null) {
						details.append("Cannot parse animation for '").append(sectionKey)
							.append("' given value: '").append(animationTypeKey).append("'")
							.append(" animation: '").append(animationKey)
							.append("\n");
						continue;
					}

					if (animationType == EffectAnimationType.WINGS) {
						YamlConfigurationSection colorsSection = animationSection.getSection("colors");

						if (colorsSection == null) {
							details.append("Cannot parse animation for '").append(sectionKey)
								.append("' because the animation type is 'wings' and there aren't colors section")
								.append("\n");
							continue;
						}

						Color firstColor = parseFromString(colorsSection.getString("first"));
						Color secondColor = parseFromString(colorsSection.getString("second"));
						Color thirdColor = parseFromString(colorsSection.getString("third"));

						if (firstColor == null || secondColor == null || thirdColor == null) {
							details.append("Cannot parse any of colors for '").append(sectionKey)
								.append("' in wings animation, check values and try again.")
								.append("\n");
							continue;
						}

						animationTypes.add(new EffectCosmeticType.WingsData(
							animationType, firstColor, secondColor, thirdColor
						));
					} else {
						animationTypes.add(new EffectCosmeticType.DefaultData(animationType));
					}
				}

				if (animationTypes.isEmpty()) {
					return null;
				}

				return new EffectCosmeticType(
					permission, sectionKey, menuIcon, category,
					animationTypes
				);
			}
			default:
				return null;
		}
	}

	private SkinEquipmentFrame parseSkinFrame(String sectionKey,
																						YamlConfigurationSection section,
																						StringBuilder details) {
		String skinProviderTypeKey = section.getString("type");
		SkinProviderType skinProviderType = SkinProviderType.getByName(skinProviderTypeKey);

		if (skinProviderType == null) {
			details.append("Cannot parse skin for '").append(sectionKey).append("'\n");
			return null;
		}

		SkinProvider skinProvider = skinProviderCreator.create(
			skinProviderType, section.getString("value")
		);

		return new SkinEquipmentFrame(skinProvider, section.getInt("delay"));
	}

}
