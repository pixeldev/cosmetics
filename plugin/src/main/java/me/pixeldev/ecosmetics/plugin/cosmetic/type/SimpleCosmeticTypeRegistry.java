package me.pixeldev.ecosmetics.plugin.cosmetic.type;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.alya.api.yaml.YamlFileConfiguration;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticTypeRegistry;
import me.pixeldev.ecosmetics.api.cosmetic.type.creator.CosmeticTypeCreator;
import me.pixeldev.ecosmetics.api.util.LoggerUtil;

import javax.inject.Inject;
import java.util.HashMap;
import java.util.Map;

public class SimpleCosmeticTypeRegistry implements CosmeticTypeRegistry {

	private static final String[] KNOWN_SECTIONS = {
		"miniatures", "effects", "morphs"
	};

	private final Map<CosmeticCategory, Map<String, CosmeticType>> registeredTypes
		= new HashMap<>();

	@Inject private YamlFileConfiguration configuration;
	@Inject private CosmeticTypeCreator cosmeticTypeCreator;

	@Override
	public void registerFromConfigurationSection(YamlConfigurationSection section) {
		String categoryKey = section.getString("type");
		CosmeticCategory category = CosmeticCategory.getByName(categoryKey);

		if (category == null) {
			LoggerUtil.warn("Cannot found a valid category. Input: '" + categoryKey + "'");

			return;
		}

		Map<String, CosmeticType> parsedTypes = new HashMap<>();
		for (String sectionKey : section.getKeys(false)) {
			if (sectionKey.equals("type")) {
				continue;
			}

			YamlConfigurationSection currentSection = section.getSection(sectionKey);

			CosmeticType parsedType = cosmeticTypeCreator.createFromSection(
				category, sectionKey, currentSection
			);

			if (parsedType == null) {
				continue;
			}

			System.out.println("Adding parsed type, id: " + sectionKey + " type: " + parsedType);
			parsedTypes.put(sectionKey, parsedType);
		}

		registeredTypes.put(category, parsedTypes);
	}

	@Override
	public void registerFromAllKnownSections() {
		for (String sectionKey : KNOWN_SECTIONS) {
			registerFromConfigurationSection(configuration.getSection(sectionKey));
		}
	}

	@Override
	public CosmeticType getCosmeticType(CosmeticCategory category, String typeKey) {
		return getAllTypesFor(category).get(typeKey);
	}

	@Override
	public Map<String, CosmeticType> getAllTypesFor(CosmeticCategory category) {
		return registeredTypes.get(category);
	}

}