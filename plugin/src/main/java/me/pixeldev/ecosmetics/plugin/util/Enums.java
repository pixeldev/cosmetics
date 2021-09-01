package me.pixeldev.ecosmetics.plugin.util;

public final class Enums {

	private Enums() {}

	public static <T extends Enum<T>> T valueOf(Class<T> enumType, String key) {
		if (key == null) {
			LoggerUtil.warn(
				"Cannot parse type for '" + enumType.getSimpleName() +
					"' because key is null."
			);
			return null;
		}

		try {
			return Enum.valueOf(enumType, key);
		} catch (IllegalArgumentException exception) {
			LoggerUtil.warn(
				"Cannot found a valid type for '" +
					enumType.getSimpleName() + "' with key '" + key + "'"
			);
			return null;
		}
	}

}
