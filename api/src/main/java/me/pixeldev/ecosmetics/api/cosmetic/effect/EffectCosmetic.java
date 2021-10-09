package me.pixeldev.ecosmetics.api.cosmetic.effect;

import me.pixeldev.ecosmetics.api.cosmetic.AbstractCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;
import me.pixeldev.ecosmetics.api.cosmetic.effect.animation.EffectCosmeticAnimation;
import me.pixeldev.ecosmetics.api.cosmetic.type.EffectCosmeticType;

import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.Set;

public class EffectCosmetic
	extends AbstractCosmetic<EffectCosmeticType>
	implements Runnable {

	private final CosmeticSpectators spectators;
	private final Set<EffectCosmeticAnimation> animations;

	public EffectCosmetic(WeakReference<Player> ownerReference, EffectCosmeticType type) {
		super(ownerReference, type);

		this.spectators = new CosmeticSpectators();
		this.animations = EffectCosmeticAnimation.of(ownerReference, spectators, type);
	}

	@Override
	public CosmeticCategory getCategory() {
		return CosmeticCategory.EFFECTS;
	}

	public CosmeticSpectators getSpectators() {
		return spectators;
	}

	@Override
	public void run() {
		if (!equipped) {
			return;
		}

		animations.forEach(EffectCosmeticAnimation::run);
	}

}
