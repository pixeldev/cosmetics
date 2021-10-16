package me.pixeldev.ecosmetics.plugin.cosmetic.pet;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.frame.EquipmentFrame;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.frame.SimpleEquipmentFrame;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.parse.EquipmentFrameParser;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.parse.RainbowEquipmentParser;
import me.pixeldev.ecosmetics.api.item.ItemParser;

import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class SimpleEquipmentFrameParser
	implements EquipmentFrameParser {

	@Inject private ItemParser itemParser;
	@Inject private RainbowEquipmentParser rainbowEquipmentParser;

	@Override
	public List<EquipmentFrame> parseFromSection(String typeKey, String sectionKey, int slot,
																							 YamlConfigurationSection section,
																							 StringBuilder details) {
		List<EquipmentFrame> frames = new ArrayList<>();
		YamlConfigurationSection framesSection = section.getSection("frames");

		if (framesSection == null) {
			String type = section.getString("type");
			if (type.contains("rainbow")) {
				List<EquipmentFrame> parsedRainbowFrames = rainbowEquipmentParser.parse(
					type, typeKey,
					sectionKey, slot,
					details
				);

				if (parsedRainbowFrames == null) {
					return null;
				}

				frames = parsedRainbowFrames;
			} else {
				frames.add(new SimpleEquipmentFrame(
					itemParser.parseFromSection(section),
					0, slot
				));
			}
		} else {
			for (String frameKey : framesSection.getKeys(false)) {
				YamlConfigurationSection currentFrameSection = framesSection.getSection(frameKey);
				ItemStack itemStack = itemParser.parseFromSection(currentFrameSection);

				if (itemStack == null) {
					continue;
				}

				frames.add(new SimpleEquipmentFrame(
					itemStack, currentFrameSection.getInt("delay"), slot
				));
			}
		}

		return frames;
	}

}
