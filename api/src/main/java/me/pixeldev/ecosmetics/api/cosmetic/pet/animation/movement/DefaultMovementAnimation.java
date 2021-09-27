package me.pixeldev.ecosmetics.api.cosmetic.pet.animation.movement;

import org.bukkit.Location;

public class DefaultMovementAnimation
	implements CosmeticPetMovementAnimation {

	private final Location baseLocation;

	private int counter = 0;
	private boolean directionChanged = true;

	public DefaultMovementAnimation(Location baseLocation) {
		this.baseLocation = baseLocation;
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
