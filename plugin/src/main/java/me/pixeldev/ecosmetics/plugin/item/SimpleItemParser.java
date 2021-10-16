package me.pixeldev.ecosmetics.plugin.item;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.ecosmetics.api.item.ItemParser;
import me.pixeldev.ecosmetics.api.util.ColorUtils;
import me.pixeldev.ecosmetics.api.util.LoggerUtil;

import org.bukkit.Color;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import team.unnamed.gui.core.item.type.ItemBuilder;

import java.util.HashSet;
import java.util.Set;

public class SimpleItemParser implements ItemParser {
	@Override
	public ItemStack parseUsingColors(Material baseMaterial, String color) {
		ItemStack itemStack = new ItemStack(baseMaterial);
		ItemMeta itemMeta = itemStack.getItemMeta();
		if (itemMeta instanceof LeatherArmorMeta) {
			LeatherArmorMeta leatherArmorMeta = (LeatherArmorMeta) itemStack.getItemMeta();
			Color parsedColor = ColorUtils.parseFromString(color);
			leatherArmorMeta.setColor(parsedColor);
			itemStack.setItemMeta(leatherArmorMeta);
			return itemStack;
		} else if (baseMaterial == Material.WOOL) {
			return ItemBuilder.newDyeItemBuilder("WOOL", DyeColor.valueOf(color))
				.build();
		} else {
			return itemStack;
		}
	}

	@Override
	public ItemStack parseFromSection(YamlConfigurationSection section) {
		String materialKey = section.getString("type");
		Material material = Material.getMaterial(materialKey);

		if (material == null) {
			LoggerUtil.warn("Cannot found material with id '" + materialKey + "'");
			return null;
		}

		String colorKey = section.getString("color");

		if (colorKey != null) {
			return parseUsingColors(material, colorKey);
		}

		return new ItemStack(material);
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
