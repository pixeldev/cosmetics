package me.pixeldev.ecosmetics.api.cosmetic.pet.animation.movement;

import org.bukkit.Location;

public class DefaultMovementAnimation
	implements CosmeticPetMovementAnimation {

	private Location baseLocation;

	private int counter = 0;
	private boolean directionChanged = true;

	public DefaultMovementAnimation(Location baseLocation) {
		this.baseLocation = baseLocation;
	}

	@Override
	public void changeBaseLocation(Location location) {
		baseLocation = location;
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

	@Override
	public double getX() {
		return baseLocation.getX();
	}

	@Override
	public double getY() {
		return baseLocation.getY();
	}

	@Override
	public double getZ() {
		return baseLocation.getZ();
	}

	@Override
	public float getYaw() {
		return baseLocation.getYaw();
	}

	@Override
	public float getPitch() {
		return baseLocation.getPitch();
	}

}