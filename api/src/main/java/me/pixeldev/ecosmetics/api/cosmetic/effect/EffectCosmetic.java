package me.pixeldev.ecosmetics.api.cosmetic.effect;

import me.pixeldev.ecosmetics.api.cosmetic.AbstractCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;
import me.pixeldev.ecosmetics.api.cosmetic.effect.animation.EffectCosmeticAnimation;
import me.pixeldev.ecosmetics.api.cosmetic.type.EffectCosmeticType;

import org.bukkit.entity.Player;

import java.util.Set;

public class EffectCosmetic
	extends AbstractCosmetic<EffectCosmeticType>
	implements Runnable {

	private final CosmeticSpectators spectators;
	private final Set<EffectCosmeticAnimation> animations;

	public EffectCosmetic(Player owner, EffectCosmeticType type) {
		super(owner, type);

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
		animations.forEach(EffectCosmeticAnimation::run);
	}

}
