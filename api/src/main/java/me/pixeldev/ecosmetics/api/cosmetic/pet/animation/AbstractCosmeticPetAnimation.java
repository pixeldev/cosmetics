package me.pixeldev.ecosmetics.api.cosmetic.pet.animation;

import org.bukkit.Location;

public abstract class AbstractCosmeticPetAnimation
	implements CosmeticPetAnimation {

	protected Location baseLocation;

	public AbstractCosmeticPetAnimation(Location baseLocation) {
		this.baseLocation = baseLocation;
	}

	@Override
	public void changeBaseLocation(Location location) {
		baseLocation = location;
	}

}
