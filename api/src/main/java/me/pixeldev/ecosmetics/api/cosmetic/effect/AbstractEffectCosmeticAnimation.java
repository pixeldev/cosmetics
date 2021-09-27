package me.pixeldev.ecosmetics.api.cosmetic.effect;

import me.pixeldev.ecosmetics.api.cosmetic.animation.DelayableCosmeticAnimation;
import xyz.xenondevs.particle.ParticleEffect;

public abstract class AbstractEffectCosmeticAnimation
	extends DelayableCosmeticAnimation
	implements EffectCosmeticAnimation {

	public AbstractEffectCosmeticAnimation(int goalTicks) {
		super(goalTicks);
	}


}
