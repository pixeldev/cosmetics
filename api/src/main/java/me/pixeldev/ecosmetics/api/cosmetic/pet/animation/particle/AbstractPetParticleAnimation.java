package me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;
import me.pixeldev.ecosmetics.api.cosmetic.animation.DelayableCosmeticAnimation;

import org.bukkit.Location;

import xyz.xenondevs.particle.ParticleEffect;

abstract class AbstractPetParticleAnimation
	extends DelayableCosmeticAnimation
	implements CosmeticPetParticleAnimation {

	protected final CosmeticSpectators spectators;

	protected float incrementX;
	protected float incrementY;
	protected float incrementZ;
	protected Location baseLocation;
	protected ParticleEffect particleEffect;

	public AbstractPetParticleAnimation(CosmeticSpectators spectators,
																			float incrementX, float incrementY, float incrementZ,
																			int goalTicks, Location baseLocation,
																			ParticleEffect particleEffect) {
		super(goalTicks);

		this.spectators = spectators;
		this.incrementX = incrementX;
		this.incrementY = incrementY;
		this.incrementZ = incrementZ;
		this.baseLocation = baseLocation;
		this.particleEffect = particleEffect;
	}

	@Override
	public void changeParticle(ParticleEffect particleEffect) {
		this.particleEffect = particleEffect;
	}

}
