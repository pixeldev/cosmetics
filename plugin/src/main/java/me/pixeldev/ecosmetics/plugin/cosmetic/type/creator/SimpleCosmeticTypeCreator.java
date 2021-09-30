package me.pixeldev.ecosmetics.plugin.cosmetic.type.creator;

import com.google.common.collect.ImmutableMap;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.effect.animation.EffectAnimationType;
import me.pixeldev.ecosmetics.api.cosmetic.permission.CosmeticPermissionFormatter;
import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle.PetParticleAnimationType;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProvider;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProviderCreator;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProviderType;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.creator.CosmeticTypeCreator;
import me.pixeldev.ecosmetics.api.item.ItemParser;
import me.pixeldev.ecosmetics.api.util.ColorUtils;
import me.pixeldev.ecosmetics.plugin.util.Enums;
import me.pixeldev.ecosmetics.api.cosmetic.type.EffectCosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.MorphCosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.PetCosmeticType;

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
		.put("chestplate", 3)
		.put("leggings", 2)
		.put("boots", 1)
		.build();

	@Inject private SkinProviderCreator skinProviderCreator;
	@Inject private CosmeticPermissionFormatter permissionFormatter;
	@Inject private ItemParser itemParser;

	@Override
	public CosmeticType createFromSection(CosmeticCategory category,
																				String sectionKey,
																				YamlConfigurationSection section,
																				StringBuilder details) {
		String name = section.getString("name");
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
				SkinProviderType skinProviderType = SkinProviderType.getByName(
					section.getString("skin")
				);

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

				YamlConfigurationSection equipmentSection = section.getSection("equipment");

				if (equipmentSection == null) {
					details.append("Cannot create cosmetic type from '")
						.append(sectionKey).append("' because MINIATURES must have an equipment section.")
						.append("\n");
					return null;
				}

				Map<Integer, ItemStack> equipment = new HashMap<>();

				for (Map.Entry<String, Integer> equipmentEntry : EQUIPMENT_SECTIONS.entrySet()) {
					String equipmentSectionKey = equipmentEntry.getKey();
					int equipmentSlot = equipmentEntry.getValue();

					YamlConfigurationSection currentEquipmentSection = equipmentSection
						.getSection(equipmentSectionKey);

					if (currentEquipmentSection == null) {
						continue;
					}

					equipment.put(equipmentSlot, itemParser.parseFromSection(currentEquipmentSection));
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

				return new PetCosmeticType(
					name, permission, sectionKey, menuIcon, category,
					equipment, skinProvider,
					Material.getMaterial(equipmentSection.getString("hand")),
					invisible, arms,
					particleEffect, animationType,
					incrementX, incrementY, incrementZ,
					goalTicks
				);
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
				Set<EffectCosmeticType.Data> animationTypes = new HashSet<>();

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

					ParticleEffect effectType = null;
					if (animationType.isCustomEffect()) {
						String effectTypeKey = animationSection.getString("effect");

						if (effectTypeKey == null) {
							details.append("Effect type in '").append(sectionKey)
								.append("' animation: '").append(animationKey).append("' cannot be null.")
								.append("\n");
							continue;
						}

						effectType = Enums.valueOf(ParticleEffect.class, effectTypeKey);

						if (effectType == null) {
							details.append("Cannot parse effect for '").append(sectionKey)
								.append("' given value: '").append(animationTypeKey).append("'")
								.append("\n");
							continue;
						}
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
						Color thirColor = parseFromString(colorsSection.getString("third"));

						if (firstColor == null || secondColor == null || thirColor == null) {
							details.append("Cannot parse any of colors for '").append(sectionKey)
								.append("' in wings animation, check values and try again.")
								.append("\n");
							continue;
						}

						animationTypes.add(new EffectCosmeticType.WingsData(
							effectType, animationType,
							firstColor, secondColor, thirColor
						));
					} else {
						animationTypes.add(new EffectCosmeticType.Data(effectType, animationType));
					}
				}

				if (animationTypes.isEmpty()) {
					return null;
				}

				return new EffectCosmeticType(
					name, permission, sectionKey, menuIcon, category,
					animationTypes
				);
			}
			case MORPHS: {
				EntityType entityType = Enums.valueOf(EntityType.class, section.getString("entity"));

				if (entityType == null) {
					return null;
				}

				return new MorphCosmeticType(
					name, permission, sectionKey, menuIcon, category,
					DisguiseType.getType(entityType),
					Material.getMaterial(section.getString("hand"))
				);
			}
			default:
				return null;
		}
	}

}
