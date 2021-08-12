package me.pixeldev.ecosmetics.plugin.cosmetic.type;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.permission.CosmeticPermissionFormatter;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProvider;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProviderCreator;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProviderType;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.PetCosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.creator.CosmeticTypeCreator;
import me.pixeldev.ecosmetics.api.item.ItemParser;
import me.pixeldev.ecosmetics.api.util.LoggerUtil;

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

				return new PetCosmeticType(
					name, permission, sectionKey, category,
					itemParser.parseAllFromSection(section.getSection("armor")),
					skinProvider
				);
			}
			default: return null;
		}
	}
}