package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

public enum EffectAnimationType {

	AROUND,
	ENDER_AURA(false),
	LOVE(false),
	MUSIC(false),
	RINGS,
	WAVES,
	WINGS(false)

	;

	private final boolean customEffect;

	EffectAnimationType(boolean customEffect) {
		this.customEffect = customEffect;
	}

	EffectAnimationType() {
		this(true);
	}

	public boolean isCustomEffect() {
		return customEffect;
	}

	public static EffectAnimationType getByName(String name) {
		try {
			return valueOf(name);
		} catch (IllegalArgumentException e) {
			return null;
		}
	}
}
