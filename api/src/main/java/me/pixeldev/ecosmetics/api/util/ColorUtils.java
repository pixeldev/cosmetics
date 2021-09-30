package me.pixeldev.ecosmetics.api.util;

import org.bukkit.Color;

import team.unnamed.gui.core.item.LeatherArmorColor;
import xyz.xenondevs.particle.data.ParticleData;
import xyz.xenondevs.particle.data.color.DustData;

public final class ColorUtils {

	private ColorUtils() {

	}

	public static ParticleData parseColorToData(Color color) {
		return new DustData(color.getRed(), color.getGreen(), color.getBlue(), 1);
	}

	public static Color parseFromString(String key) {
		try {
			LeatherArmorColor armorColor = LeatherArmorColor.valueOf(key);

			return Color.fromRGB(
				armorColor.getRed(),
				armorColor.getGreen(),
				armorColor.getBlue()
			);
		} catch (IllegalArgumentException ignored) {
			String[] rgbColors = key.split(";");

			if (rgbColors.length < 3) {
				return null;
			}

			return Color.fromRGB(
				Integer.parseInt(rgbColors[0]),
				Integer.parseInt(rgbColors[1]),
				Integer.parseInt(rgbColors[2])
			);
		}
	}

}
