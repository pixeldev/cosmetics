package me.pixeldev.ecosmetics.plugin.menu;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.frame.SkinEquipmentFrame;
import me.pixeldev.ecosmetics.api.cosmetic.type.pet.AnimatedPetCosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.pet.PetCosmeticType;

public class CosmeticMiniaturesMenu
	extends CosmeticTypesMenu {

	public CosmeticMiniaturesMenu() {
		super(
			CosmeticCategory.MINIATURES,
			(player, cosmeticType) -> {
				if (cosmeticType instanceof AnimatedPetCosmeticType) {
					SkinEquipmentFrame skinEquipmentFrame =
						(SkinEquipmentFrame) ((AnimatedPetCosmeticType) cosmeticType).getSkinFrames().get(0);
					return PetCosmetic.getSkin(
						skinEquipmentFrame.getSkinProvider(),
						player, player
					);
				} else {
					return PetCosmetic.getSkin(
						((PetCosmeticType) cosmeticType).getSkinProvider(), player, player
					);
				}
			}
		);
	}

}
