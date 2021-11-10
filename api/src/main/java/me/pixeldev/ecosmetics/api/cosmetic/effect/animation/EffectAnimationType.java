package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

public enum EffectAnimationType {

	AROUND,
	ENDER_AURA(false),
	LOVE(false),
	MUSIC(false),
	RINGS,
	WAVES,
	COMPANION,
	BEAM,
	CHAINS,
	FEET,
	HALO,
	ORBIT,
	OVERHEAD,
	POINT,
	POPPER,
	NORMAL,
	PULSE,
	QUAD_HELIX,
	ATOM,
	SPHERE,
	SPIN,
	WHIRL,
	WINGS(false)

	;

	private final boolean customEffect;

	EffectAnimationType(boolean customEffect) {
		this.customEffect = customEffect;
	}

	EffectAnimationType() {
		this(true);
	}

	public boolean hasCustomEffect() {
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
