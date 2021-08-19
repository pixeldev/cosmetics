package me.pixeldev.ecosmetics.api.cosmetic;

import java.util.Locale;

public enum CosmeticCategory {
	EFFECTS, MORPHS, MINIATURES

	;

	public static CosmeticCategory getByName(String name) {
		try {
			return valueOf(name.toUpperCase(Locale.ROOT));
		} catch (IllegalArgumentException exception) {
			return null;
		}
	}
}