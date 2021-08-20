package me.pixeldev.ecosmetics.plugin.cosmetic.pet;

import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityShowerFilter;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import javax.inject.Inject;

public final class PetShowerRunnable implements Runnable {

	@Inject private CosmeticUserService userService;

	@Inject private PetEntityShowerFilter entityShowerFilter;

	@Override
	public void run() {
		for (CosmeticUser user : userService.getAllCachedUsers()) {
			CosmeticCategory currentCategory = user.getCurrentCategory();
			Cosmetic<?> currentCosmetic = user.getCurrentCosmetic();

			if (currentCategory == CosmeticCategory.MINIATURES) {
				PetCosmetic petCosmetic = (PetCosmetic) currentCosmetic;
				entityShowerFilter.showOrDestroyToFitPlayers(petCosmetic);
			}
		}
	}

}