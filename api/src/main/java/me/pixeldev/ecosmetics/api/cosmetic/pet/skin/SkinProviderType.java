package me.pixeldev.ecosmetics.api.cosmetic.pet.skin;

public enum SkinProviderType {
	VIEWER, URL, PLAYER

	;

	public static SkinProviderType getByName(String name) {
		try {
			return valueOf(name);
		} catch (IllegalArgumentException exception) {
			return null;
		}
	}
}