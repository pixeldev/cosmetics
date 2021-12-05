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
import java.util.LinkedHashMap;
import java.util.Map;

public class SimpleCosmeticTypeRegistry implements CosmeticTypeRegistry {

	private static final String[] KNOWN_SECTIONS = {
		"miniatures", "effects"
	};

	private final Map<CosmeticCategory, Map<String, CosmeticType>> registeredTypes
		= new HashMap<>();

	@Inject private YamlFileConfiguration configuration;
	@Inject private CosmeticTypeCreator cosmeticTypeCreator;

	@Override
	public void registerFromConfigurationSection(YamlConfigurationSection section, StringBuilder details) {
		String categoryKey = section.getString("type");
		CosmeticCategory category = CosmeticCategory.getByName(categoryKey);

		if (category == null) {
			details.append("Cannot found a valid category. Input: '")
				.append(categoryKey).append("'").append("\n");

			return;
		}

		Map<String, CosmeticType> parsedTypes = new LinkedHashMap<>();
		for (String sectionKey : section.getKeys(false)) {
			if (sectionKey.equals("type") || sectionKey.contains("rainbow-animation")) {
				continue;
			}

			YamlConfigurationSection currentSection = section.getSection(sectionKey);

			CosmeticType parsedType = cosmeticTypeCreator.createFromSection(
				category, sectionKey, currentSection, details
			);

			if (parsedType == null) {
				continue;
			}

			details.append("Successfully added type '").append(sectionKey)
				.append("'").append("\n");

			parsedTypes.put(sectionKey, parsedType);
		}

		registeredTypes.put(category, parsedTypes);
	}

	@Override
	public void registerFromAllKnownSections() {
		StringBuilder details = new StringBuilder();
		details.append("\n=========================")
			.append("\nCOSMETIC PARSING DETAILS:")
			.append("\n");

		for (String sectionKey : KNOWN_SECTIONS) {
			details.append("\nParsing section ").append(sectionKey).append("\n\n");
			registerFromConfigurationSection(configuration.getSection(sectionKey), details);
		}

		details.append("=========================");

		LoggerUtil.info(details.toString());
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
