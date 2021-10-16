package me.pixeldev.ecosmetics.plugin.cosmetic.pet;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.alya.api.yaml.YamlFileConfiguration;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.frame.EquipmentFrame;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.frame.SimpleEquipmentFrame;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.parse.RainbowEquipmentParser;
import me.pixeldev.ecosmetics.api.item.ItemParser;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class SimpleRainbowEquipmentParser
	implements RainbowEquipmentParser {

	@Inject private YamlFileConfiguration config;
	@Inject private ItemParser itemParser;

	@Override
	public List<EquipmentFrame> parse(String rainbowKey, String typeKey,
																		String sectionKey, int slot,
																		StringBuilder details) {
		YamlConfigurationSection rainbowSection = config.getSection("miniatures." + rainbowKey);

		if (rainbowSection == null) {
			details.append("Cannot found a valid rainbow animation in equipment '")
				.append(sectionKey).append("' for '").append(typeKey).append("'\n");
			return null;
		}

		YamlConfigurationSection framesSection = rainbowSection.getSection("frames");
		List<EquipmentFrame> frames = new ArrayList<>();

		Material material;

		if (rainbowKey.split("-")[0].equals("armor")) {
			material = Material.getMaterial(
				"LEATHER_" + sectionKey.toUpperCase(Locale.ROOT)
			);
		} else {
			material = Material.WOOL;
		}

		for (String frameKey : framesSection.getKeys(false)) {
			YamlConfigurationSection currentFrameSection = framesSection.getSection(frameKey);

			ItemStack parsedItem = itemParser.parseUsingColors(
				material, currentFrameSection.getString("color")
			);

			frames.add(new SimpleEquipmentFrame(
				parsedItem, currentFrameSection.getInt("delay"), slot
			));
		}

		return frames;
	}

}
