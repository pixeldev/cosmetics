package me.pixeldev.ecosmetics.api.cosmetic.pet.entity;

import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;

import org.bukkit.entity.Player;

public interface PetEntityShowerFilter {

	boolean canBeShown(Player viewer, PetCosmetic petCosmetic);

	void showToFitPlayers(PetCosmetic petCosmetic);

	void showOrDestroyToFitPlayers(PetCosmetic petCosmetic);

}