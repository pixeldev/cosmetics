package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;
import me.pixeldev.ecosmetics.api.cosmetic.animation.DelayableCosmeticAnimation;

import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

public abstract class AbstractEffectCosmeticAnimation
	extends DelayableCosmeticAnimation
	implements EffectCosmeticAnimation {

	protected final WeakReference<Player> ownerReference;
	protected final CosmeticSpectators spectators;
	protected final ParticleEffect effect;

	public AbstractEffectCosmeticAnimation(WeakReference<Player> ownerReference,
																				 CosmeticSpectators spectators,
																				 ParticleEffect effect,
																				 int goalTicks) {
		super(goalTicks);
		this.ownerReference = ownerReference;
		this.spectators = spectators;
		this.effect = effect;
	}

	@Override
	public void run() {
		Player player = ownerReference.get();

		if (player == null) {
			return;
		}

		if (!incrementTicks()) {
			return;
		}

		runAnimation(player);
	}

	protected abstract void runAnimation(Player player);

}
