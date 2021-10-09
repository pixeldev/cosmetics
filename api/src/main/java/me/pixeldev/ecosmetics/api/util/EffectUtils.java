package me.pixeldev.ecosmetics.api.util;

import xyz.xenondevs.particle.ParticleEffect;

public final class EffectUtils {

	private EffectUtils() {
	}

	public static float getSpeedByEffect(ParticleEffect effect) {
		switch (effect) {
			case CRIT:
			case DAMAGE_INDICATOR:
			case ENCHANTMENT_TABLE:
				return 1;
			case ELECTRIC_SPARK:
			case SCRAPE:
				return 2;
			case WAX_OFF:
			case WAX_ON:
				return 3;
			case DRAGON_BREATH:
				return 0.01F;
			case NAUTILUS:
			case PORTAL:
				return 0.5F;
			case END_ROD:
			case SMOKE_NORMAL:
			case SQUID_INK:
				return 0.15F;
			case FIREWORKS_SPARK:
			case SPIT:
			case WATER_SPLASH:
				return 0.25F;
			default:
				return 0.1F; // Flame
		}
	}

}
