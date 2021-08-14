package me.pixeldev.ecosmetics.plugin.item;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.ecosmetics.api.item.ItemParser;
import me.pixeldev.ecosmetics.api.util.LoggerUtil;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import team.unnamed.gui.core.item.LeatherArmorColor;

import java.util.HashSet;
import java.util.Set;

public class SimpleItemParser implements ItemParser {
	@Override
	public ItemStack parseFromSection(YamlConfigurationSection section) {
		String materialKey = section.getString("type");
		Material material = Material.getMaterial(materialKey);

		if (material == null) {
			LoggerUtil.warn("Cannot found material with id '" + materialKey + "'");
			return null;
		}

		ItemStack itemStack = new ItemStack(material);

		String colorKey = section.getString("color");

		if (colorKey != null) {
			LeatherArmorMeta armorMeta = (LeatherArmorMeta) itemStack.getItemMeta();

			try {
				LeatherArmorColor armorColor = LeatherArmorColor.valueOf(colorKey);

				armorMeta.setColor(Color.fromRGB(
					armorColor.getRed(),
					armorColor.getGreen(),
					armorColor.getBlue()
				));
			} catch (IllegalArgumentException ignored) {
				String[] rgbColors = colorKey.split(";");
				armorMeta.setColor(Color.fromRGB(
					Integer.parseInt(rgbColors[0]),
					Integer.parseInt(rgbColors[1]),
					Integer.parseInt(rgbColors[2])
				));
			}

			itemStack.setItemMeta(armorMeta);
		}

		return itemStack;
	}

	@Override
	public Set<ItemStack> parseAllFromSection(YamlConfigurationSection section) {
		Set<ItemStack> parsedItems = new HashSet<>();

		for (String key : section.getKeys(false)) {
			ItemStack parsedItem = parseFromSection(
				section.getSection(key)
			);

			if (parsedItem == null) {
				continue;
			}

			parsedItems.add(parsedItem);
		}

		return parsedItems;
	}
}