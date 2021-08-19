package me.pixeldev.ecosmetics.api.cosmetic.pet.animation.movement;

import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.CosmeticPetAnimation;

/**
 * Represents a movement animation.
 */
public interface CosmeticPetMovementAnimation
	extends CosmeticPetAnimation {

	double getX();

	double getY();

	double getZ();

	float getYaw();

	float getPitch();

}