package me.pixeldev.ecosmetics.api.cosmetic;

import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import org.bukkit.entity.Player;

public interface CosmeticHandler {

	void equipCosmetic(CosmeticUser user, Cosmetic<?> cosmetic);

	boolean unequipCosmetic(CosmeticUser user);

	boolean hasAlreadyEquipedCosmetic(CosmeticUser user, CosmeticType cosmeticType);

	Cosmetic<?> create(CosmeticCategory category, Player owner, CosmeticType cosmeticType);

}
