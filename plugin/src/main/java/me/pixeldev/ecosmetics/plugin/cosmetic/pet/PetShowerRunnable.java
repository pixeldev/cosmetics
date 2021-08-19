package me.pixeldev.ecosmetics.plugin.cosmetic.pet;

import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public final class PetShowerRunnable implements Runnable {

	@Inject private CosmeticUserService userService;
	@Inject private PetEntityHandler entityHandler;

	@Override
	public void run() {
		for (CosmeticUser user : userService.getAllCachedUsers()) {
			CosmeticCategory currentCategory = user.getCurrentCategory();
			Cosmetic<?> currentCosmetic = user.getCurrentCosmetic();

			if (currentCategory == CosmeticCategory.MINIATURES) {
				PetCosmetic petCosmetic = (PetCosmetic) currentCosmetic;
				Location actualLocation = petCosmetic.getActualLocation();

				for (Player player : Bukkit.getOnlinePlayers()) {
					if (actualLocation.distance(player.getLocation()) > 50) {
						entityHandler.destroy(player, petCosmetic);
					} else {
						entityHandler.spawn(player, petCosmetic);
					}
				}
			}
		}
	}

}