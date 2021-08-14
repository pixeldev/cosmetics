package me.pixeldev.ecosmetics.api.cosmetic.effect;

public enum EffectAnimationType {
	RINGS, BLOOD, AURA, QUAD_HELIX, SPARKS

	;

	public static EffectAnimationType getByName(String name) {
		try {
			return valueOf(name);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}