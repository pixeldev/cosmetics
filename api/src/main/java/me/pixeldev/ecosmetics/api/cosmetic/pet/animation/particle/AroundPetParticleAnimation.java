package me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class AroundPetParticleAnimation
	extends AbstractPetParticleAnimation {

	private int counter;

	public AroundPetParticleAnimation(float incrementX, float incrementY, float incrementZ,
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
			if (counter >= 50) {
				counter = 0;
			}

			double lx = Math.sin(0.39269908169872414D * counter) * 0.4D;
			double lz = Math.cos(0.39269908169872414D * counter) * 0.4D;

			new ParticleBuilder(particleEffect)
				.setLocation(baseLocation.clone().add(
					lx, incrementY, lz
				))
				.setOffset(incrementX, 0.0F, incrementZ)
				.setSpeed(0)
				.setAmount(1)
				.display(Bukkit.getOnlinePlayers());

			counter++;
		}
	}

}