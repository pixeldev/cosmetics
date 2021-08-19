package me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle;

import org.bukkit.Location;

import xyz.xenondevs.particle.ParticleEffect;

abstract class AbstractPetParticleAnimation
	implements CosmeticPetParticleAnimation {

	protected float incrementX;
	protected float incrementY;
	protected float incrementZ;
	protected int goalTicks;
	protected Location baseLocation;
	protected ParticleEffect particleEffect;

	protected int currentTicks;

	public AbstractPetParticleAnimation(float incrementX, float incrementY, float incrementZ,
																			int goalTicks, Location baseLocation,
																			ParticleEffect particleEffect) {
		this.incrementX = incrementX;
		this.incrementY = incrementY;
		this.incrementZ = incrementZ;
		this.goalTicks = goalTicks;
		this.baseLocation = baseLocation;
		this.particleEffect = particleEffect;
	}

	@Override
	public void changeBaseLocation(Location location) {
		baseLocation = location;
	}

	@Override
	public void changeParticle(ParticleEffect particleEffect) {
		this.particleEffect = particleEffect;
	}

	protected boolean incrementTicks() {
		if (currentTicks >= goalTicks) {
			currentTicks = 0;
			return true;
		}

		currentTicks++;
		return false;
	}

}