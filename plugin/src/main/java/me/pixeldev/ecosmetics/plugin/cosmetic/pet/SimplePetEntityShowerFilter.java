package me.pixeldev.ecosmetics.plugin.cosmetic.pet;

import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityShowerFilter;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class SimplePetEntityShowerFilter implements PetEntityShowerFilter {

	@Inject private PetEntityHandler entityHandler;

	@Override
	public boolean canBeShown(Player viewer, PetCosmetic petCosmetic) {
		return internalCanBeShown(viewer, petCosmetic.getActualLocation());
	}

	@Override
	public void showToFitPlayers(PetCosmetic petCosmetic) {
		Location actualLocation = petCosmetic.getActualLocation();

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (!internalCanBeShown(player, actualLocation)) {
				continue;
			}

			entityHandler.spawn(player, petCosmetic);
		}
	}

	@Override
	public void showOrDestroyToFitPlayers(PetCosmetic petCosmetic) {
		Location actualLocation = petCosmetic.getActualLocation();

		for (Player player : Bukkit.getOnlinePlayers()) {
			if (internalCanBeShown(player, actualLocation)) {
				entityHandler.spawn(player, petCosmetic);
			} else {
				entityHandler.destroy(player, petCosmetic);
			}
		}
	}

	private boolean internalCanBeShown(Player viewer, Location location) {
		return location.distance(viewer.getLocation()) < 50;
	}

}