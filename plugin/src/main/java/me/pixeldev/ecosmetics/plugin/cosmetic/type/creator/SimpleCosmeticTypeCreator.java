package me.pixeldev.ecosmetics.plugin.cosmetic.type.creator;

import com.google.common.collect.ImmutableMap;
import me.libraryaddict.disguise.disguisetypes.DisguiseType;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectAnimationType;
import me.pixeldev.ecosmetics.api.cosmetic.permission.CosmeticPermissionFormatter;
import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle.PetParticleAnimationType;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProvider;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProviderCreator;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProviderType;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.creator.CosmeticTypeCreator;
import me.pixeldev.ecosmetics.api.item.ItemParser;
import me.pixeldev.ecosmetics.plugin.util.Enums;
import me.pixeldev.ecosmetics.plugin.util.LoggerUtil;
import me.pixeldev.ecosmetics.api.cosmetic.type.EffectCosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.MorphCosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.PetCosmeticType;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import xyz.xenondevs.particle.ParticleEffect;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

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
																				YamlConfigurationSection section) {
		String name = section.getString("name");
		String permission = permissionFormatter.format(category, sectionKey);
		Material menuIcon = Material.getMaterial(section.getString("icon"));

		if (menuIcon == null && category != CosmeticCategory.MINIATURES) {
			LoggerUtil.warn(
				"Cannot parse cosmetic type '"
					+ sectionKey +
					"' because cannot found the menu icon."
			);
			return null;
		}

		switch (category) {
			case MINIATURES: {
				SkinProviderType skinProviderType = SkinProviderType.getByName(
					section.getString("skin")
				);

				SkinProvider skinProvider;

				if (skinProviderType == null) {
					LoggerUtil.warn(
						"Cannot create a skin provider type from cosmetic '"
							+ sectionKey +
							"' using a default one."
					);

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
					LoggerUtil.warn(
						"Cannot create cosmetic type from '"
							+ sectionKey +
							"' because MINIATURES must have an equipment section."
					);
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
					LoggerUtil.warn("Cannot found particle animation section for '" + sectionKey + "'");
					return null;
				}

				String animationKey = particleAnimationSection.getString("type");

				if (animationKey == null) {
					LoggerUtil.warn("Particle animation in '" + sectionKey + "' cannot be null.");
					return null;
				}

				PetParticleAnimationType animationType = Enums.valueOf(
					PetParticleAnimationType.class, animationKey
				);

				if (animationType == null) {
					return null;
				}

				ParticleEffect particleEffect = Enums.valueOf(
					ParticleEffect.class, particleAnimationSection.getString("effect")
				);

				if (particleEffect == null) {
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
				ParticleEffect particleEffect = Enums.valueOf(
					ParticleEffect.class, section.getString("effect")
				);

				EffectAnimationType animationType = Enums.valueOf(
					EffectAnimationType.class, section.getString("animation")
				);

				if (particleEffect == null || animationType == null) {
					return null;
				}

				return new EffectCosmeticType(
					name, permission, sectionKey, menuIcon, category,
					animationType, particleEffect
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
