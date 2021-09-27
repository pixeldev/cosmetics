package me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle;

public enum PetParticleAnimationType {

	NORMAL, CIRCLE, AROUND

	;

	public static PetParticleAnimationType getByName(String name) {
		try {
			return valueOf(name);
		} catch (IllegalArgumentException ignored) {
			return null;
		}
	}

}
