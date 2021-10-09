package me.pixeldev.ecosmetics.api.cosmetic.pet.animation.movement;

import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.AbstractCosmeticPetAnimation;

import org.bukkit.Location;

public class DefaultMovementAnimation
	extends AbstractCosmeticPetAnimation {

	private int counter = 0;
	private boolean directionChanged = true;

	public DefaultMovementAnimation(Location baseLocation) {
		super(baseLocation);
	}

	@Override
	public void run() {
		if (directionChanged) {
			counter++;

			baseLocation.add(0, 0.05, 0);

			if (counter > 23) {
				directionChanged = false;
			}
		} else {
			counter--;

			baseLocation.subtract(0, 0.05, 0);

			if (counter <= 0) {
				directionChanged = true;
			}
		}
	}

}
