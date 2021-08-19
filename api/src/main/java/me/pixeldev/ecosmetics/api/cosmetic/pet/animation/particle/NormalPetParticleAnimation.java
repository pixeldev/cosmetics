package me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle;

import org.bukkit.Bukkit;
import org.bukkit.Location;

import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

public class NormalPetParticleAnimation
	extends AbstractPetParticleAnimation {

	public NormalPetParticleAnimation(float incrementX, float incrementY, float incrementZ,
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
		new ParticleBuilder(particleEffect)
			.setLocation(baseLocation.clone().add(0, incrementY, 0))
			.setOffset(incrementX, 0.0F, incrementZ)
			.setSpeed(0)
			.setAmount(1)
			.display(Bukkit.getOnlinePlayers());
	}

}