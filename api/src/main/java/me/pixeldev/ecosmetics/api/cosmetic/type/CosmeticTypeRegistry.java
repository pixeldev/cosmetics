package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.type.creator.CosmeticTypeCreator;

import java.util.Map;

public interface CosmeticTypeRegistry {

	/**
	 * Read and load all the possible types from the given section.
	 * NOTE: Types will be created as {@link CosmeticTypeCreator}
	 * returns.
	 * @param section Section to read from config.
	 */
	void registerFromConfigurationSection(YamlConfigurationSection section);

	/**
	 * Read and load all the types from default section.
	 */
	void registerFromAllKnownSections();

	/**
	 * @param category Category of the finding type.
	 * @param typeKey Key of the type.
	 * @return A nullable instance of the {@link CosmeticType} found.
	 */
	CosmeticType getCosmeticType(CosmeticCategory category, String typeKey);

	/**
	 * @param category Category of the finding types.
	 * @return An instance of the found types for the given category.
	 */
	Map<String, CosmeticType> getAllTypesFor(CosmeticCategory category);

}