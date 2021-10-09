package me.pixeldev.ecosmetics.api.cosmetic.pet.animation;

import me.pixeldev.ecosmetics.api.cosmetic.animation.CosmeticAnimation;

import org.bukkit.Location;

public interface CosmeticPetAnimation
	extends CosmeticAnimation {

	void changeBaseLocation(Location location);

}
