package me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle;

import org.bukkit.Bukkit;
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

	public CirclePetParticleAnimation(float incrementX, float incrementY, float incrementZ,
																		int goalTicks, Location baseLocation,
																		ParticleEffect particleEffect) {
		super(
			incrementX, incrementY, incrementZ,
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
				).display(Bukkit.getOnlinePlayers());
			}
		}
	}

}