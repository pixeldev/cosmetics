package me.pixeldev.ecosmetics.plugin.util;

public final class Enums {

	private Enums() {}

	public static <T extends Enum<T>> T valueOf(Class<T> enumType, String key) {
		try {
			return Enum.valueOf(enumType, key);
		} catch (IllegalArgumentException exception) {
			return null;
		}
	}

}
