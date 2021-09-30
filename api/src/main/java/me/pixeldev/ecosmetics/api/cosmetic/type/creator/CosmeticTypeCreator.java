package me.pixeldev.ecosmetics.api.cosmetic.type.creator;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;

public interface CosmeticTypeCreator {

	/**
	 * Parse a data gotten from a section to an instance
	 * of the corresponding {@link CosmeticType}.
	 *
	 * @param category   Category of the cosmetic type.
	 * @param sectionKey Key of the section.
	 * @param section    Section to get the data.
	 * @return A nullable instance of the parsed {@link CosmeticType}.
	 */
	CosmeticType createFromSection(CosmeticCategory category,
																 String sectionKey,
																 YamlConfigurationSection section,
																 StringBuilder details);

}
