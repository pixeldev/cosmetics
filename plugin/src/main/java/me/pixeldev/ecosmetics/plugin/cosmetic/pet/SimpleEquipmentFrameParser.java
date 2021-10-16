package me.pixeldev.ecosmetics.plugin.cosmetic.pet;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.EquipmentFrame;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.EquipmentFrameParser;
import me.pixeldev.ecosmetics.api.item.ItemParser;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class SimpleEquipmentFrameParser
	implements EquipmentFrameParser {

	@Inject private ItemParser itemParser;

	@Override
	public List<EquipmentFrame> parseFromSection(String sectionKey,
																							 YamlConfigurationSection section,
																							 StringBuilder details) {
		List<EquipmentFrame> frames = new ArrayList<>();
		YamlConfigurationSection animatedSkinSection = section.getSection("skin-animation");

		if (animatedSkinSection == null) {
			details.append("Cannot found a valid skin for '").append(sectionKey).append("'.");
			return null;
		}

		return null;
	}

}
