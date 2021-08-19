package me.pixeldev.ecosmetics.api.cosmetic.pet.animation;

import org.bukkit.Location;

/**
 * Represents any type of animation which a pet can perform.
 * It's a runnable because we need to update the animation.
 */
public interface CosmeticPetAnimation extends Runnable {

	/**
	 * Changes the current location where the animation
	 * will be displayed.
	 *
	 * @param location New location of the animation.
	 */
	void changeBaseLocation(Location location);

}