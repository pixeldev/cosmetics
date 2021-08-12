package me.pixeldev.ecosmetics.api.cosmetic;

public enum CosmeticCategory {
	EFFECTS, MORPHS, MINIATURES

	;

	public static CosmeticCategory getByName(String name) {
		try {
			return valueOf(name);
		} catch (IllegalArgumentException exception) {
			return null;
		}
	}
}