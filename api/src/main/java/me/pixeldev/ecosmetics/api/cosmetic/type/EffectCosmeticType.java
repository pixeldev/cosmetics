package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.particle.ParticleType;

public class EffectCosmeticType extends AbstractCosmeticType {

	private ParticleType particleType;

	public EffectCosmeticType(String name, String permission,
														String configurationIdentifier, CosmeticCategory category,
														ParticleType particleType) {
		super(name, permission, configurationIdentifier, category);
		this.particleType = particleType;
	}

	public ParticleType getParticleType() {
		return particleType;
	}

	public void setParticleType(ParticleType particleType) {
		this.particleType = particleType;
	}

}