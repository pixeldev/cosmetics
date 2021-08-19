package me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle;

import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;

import org.bukkit.Location;

import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class CirclePetParticleAnimation
	extends AbstractPetParticleAnimation {

	private static final double[] ADDING_X_VALUES = {
		0.5, 0.43, 0.25, 0, -0.25, -0.43, -0.5, -0.43, -0.25, 0, 0.25, 0.43
	};

	private static final double[] ADDING_Z_VALUES = {
		0, 0.25, 0.43, 0.5, 0.43, 0.25, 0, -0.25, -0.43, -0.5, -0.43, -0.25
	};

	public CirclePetParticleAnimation(PetCosmetic.Spectators spectators,
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
		if (incrementTicks()) {
			ParticleBuilder particleBuilder = new ParticleBuilder(particleEffect)
				.setOffset(incrementX, 0.0F, incrementZ)
				.setSpeed(0)
				.setAmount(1);

			Location baseLocation = this.baseLocation.clone()
				.add(0, incrementY, 0);

			for (int i = 0; i < ADDING_X_VALUES.length; i++) {
				double incrementX = ADDING_X_VALUES[i];
				double incrementZ = ADDING_Z_VALUES[i];


				particleBuilder.setLocation(baseLocation
					.clone()
					.add(incrementX, 0, incrementZ)
				);

				spectators.consumeAsPlayers(particleBuilder::display);
			}
		}
	}

}