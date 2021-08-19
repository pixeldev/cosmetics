package me.pixeldev.ecosmetics.api.cosmetic.pet.entity;

import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;

import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Controller of all kind of action that can be
 * performed by a pet, including packets management.
 */
public interface PetEntityHandler {

	void teleport(PetCosmetic petCosmetic, Location location);

	void spawn(Player viewer, PetCosmetic petCosmetic);

	void displayAnimation(PetCosmetic petCosmetic);

	void destroy(Player viewer, PetCosmetic petCosmetic);

	void destroy(PetCosmetic petCosmetic);

}