package me.pixeldev.ecosmetics.api.cosmetic;

import me.pixeldev.ecosmetics.api.user.CosmeticUser;

public interface CosmeticHandler {

	void equipCosmetic(CosmeticUser user, Cosmetic<?> cosmetic);

	void unequipCosmetic(CosmeticUser user);

}
