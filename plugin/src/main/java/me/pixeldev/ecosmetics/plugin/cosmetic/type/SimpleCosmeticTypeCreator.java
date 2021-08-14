package me.pixeldev.ecosmetics.plugin.cosmetic.type;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectAnimationType;
import me.pixeldev.ecosmetics.api.cosmetic.permission.CosmeticPermissionFormatter;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProvider;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProviderCreator;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProviderType;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.creator.CosmeticTypeCreator;
import me.pixeldev.ecosmetics.api.item.ItemParser;
import me.pixeldev.ecosmetics.api.util.LoggerUtil;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import xyz.xenondevs.particle.ParticleEffect;

import javax.inject.Inject;

public class SimpleCosmeticTypeCreator implements CosmeticTypeCreator {

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

				YamlConfigurationSection propertiesSection = section.getSection("properties");
				boolean invisible = false;
				boolean arms = false;

				if (propertiesSection != null) {
					invisible = propertiesSection.getBoolean("invisible");
					arms = propertiesSection.getBoolean("arms");
				}

				return new PetCosmeticType(
					name, permission, sectionKey, menuIcon, category,
					itemParser.parseAllFromSection(equipmentSection),
					skinProvider, Material.getMaterial(equipmentSection.getString("hand")),
					invisible, arms
				);
			}
			case EFFECTS: {
				ParticleEffect particleEffect;

				try {
					particleEffect = ParticleEffect.valueOf(section.getString("effect"));
				} catch (IllegalArgumentException e) {
					LoggerUtil.warn(
						"Cannot get particle type for '" + sectionKey + "' check the name."
					);
					return null;
				}

				EffectAnimationType animationType = EffectAnimationType.getByName(
					section.getString("animation")
				);

				if (animationType == null) {
					LoggerUtil.warn(
						"Cannot get particle animation type for '"
							+ sectionKey +
							"' check the name."
					);
					return null;
				}

				return new EffectCosmeticType(
					name, permission, sectionKey, menuIcon, category,
					animationType, particleEffect
				);
			}
			case MORPHS: {
				EntityType entityType;

				try {
					entityType = EntityType.valueOf(section.getString("entity"));
				} catch (IllegalArgumentException e) {
					LoggerUtil.warn(
						"Cannot get particle type for '" + sectionKey + "' check the name."
					);
					return null;
				}

				return new MorphCosmeticType(
					name, permission, sectionKey, menuIcon, category,
					DisguiseType.getType(entityType),
					Material.getMaterial(section.getString("hand"))
				);
			}
			default: return null;
		}
	}
}