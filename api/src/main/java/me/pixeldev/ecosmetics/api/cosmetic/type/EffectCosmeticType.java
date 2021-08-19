package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectAnimationType;

import org.bukkit.Material;

import xyz.xenondevs.particle.ParticleEffect;

public class EffectCosmeticType extends AbstractCosmeticType {

	private final EffectAnimationType animationType;
	private final ParticleEffect particleEffect;

	public EffectCosmeticType(String name, String permission,
														String configurationIdentifier,
														Material menuIcon, CosmeticCategory category,
														EffectAnimationType animationType, ParticleEffect particleType) {
		super(name, permission, configurationIdentifier, menuIcon, category);
		this.animationType = animationType;
		this.particleEffect = particleType;
	}

	public EffectAnimationType getAnimationType() {
		return animationType;
	}

	public ParticleEffect getParticleEffect() {
		return particleEffect;
	}

}