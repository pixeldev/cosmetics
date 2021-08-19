package me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle;

import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.CosmeticPetAnimation;
import me.pixeldev.ecosmetics.api.cosmetic.type.PetCosmeticType;

import org.bukkit.Location;

import xyz.xenondevs.particle.ParticleEffect;

public interface CosmeticPetParticleAnimation
	extends CosmeticPetAnimation {

	void changeParticle(ParticleEffect particleEffect);

	static CosmeticPetParticleAnimation of(Location baseLocation,
																				 PetCosmetic.Spectators spectators,
																				 PetCosmeticType petCosmeticType) {
		PetParticleAnimationType animationType = petCosmeticType.getAnimationType();
		ParticleEffect particleEffect = petCosmeticType.getParticleEffect();
		float incrementX = petCosmeticType.getIncrementX();
		float incrementY = petCosmeticType.getIncrementY();
		float incrementZ = petCosmeticType.getIncrementZ();
		int goalTicks = petCosmeticType.getGoalTicks();

		switch (animationType) {
			case CIRCLE: {
				return new CirclePetParticleAnimation(
					spectators,
					incrementX, incrementY, incrementZ,
					goalTicks, baseLocation, particleEffect
				);
			}
			case AROUND: {
				return new AroundPetParticleAnimation(
					spectators,
					incrementX, incrementY, incrementZ,
					goalTicks, baseLocation, particleEffect
				);
			}
			case NORMAL: {
				return new NormalPetParticleAnimation(
					spectators,
					incrementX, incrementY, incrementZ,
					goalTicks, baseLocation, particleEffect
				);
			}
			default:
				return null;
		}
	}

}