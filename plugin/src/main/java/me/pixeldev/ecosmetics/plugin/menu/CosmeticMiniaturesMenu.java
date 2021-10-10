package me.pixeldev.ecosmetics.plugin.menu;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.type.PetCosmeticType;

public class CosmeticMiniaturesMenu
	extends CosmeticTypesMenu {

	public CosmeticMiniaturesMenu() {
		super(
			CosmeticCategory.MINIATURES,
			(player, cosmeticType) -> PetCosmetic.getSkin(
				((PetCosmeticType) cosmeticType).getSkinProvider(),
				player, player
			));
	}

}
