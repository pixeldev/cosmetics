package me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle;

import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;

import org.bukkit.Location;

import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class NormalPetParticleAnimation
	extends AbstractPetParticleAnimation {

	public NormalPetParticleAnimation(PetCosmetic.Spectators spectators,
																		float incrementX, float incrementY, float incrementZ,
																		int goalTicks, Location baseLocation,
																		ParticleEffect particleEffect) {
		super(
			spectators, incrementX, incrementY, incrementZ,
			goalTicks, baseLocation,
			particleEffect
		);
	}

	@Override
	public void run() {
		ParticleBuilder particleBuilder = new ParticleBuilder(particleEffect)
			.setLocation(baseLocation.clone().add(0, incrementY, 0))
			.setOffset(incrementX, 0.0F, incrementZ)
			.setSpeed(0)
			.setAmount(1);

		spectators.consumeAsPlayers(particleBuilder::display);
	}

}