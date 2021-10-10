package me.pixeldev.ecosmetics.api.cosmetic;

import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import org.bukkit.entity.Player;

public interface CosmeticHandler {

	void assignAndEquipCosmetic(Player player, CosmeticUser user,
															CosmeticCategory category, CosmeticType cosmeticType);

	void equipCosmetic(CosmeticUser user, Cosmetic<?> cosmetic);

	void unequipCosmetic(CosmeticUser user, boolean removeFromUser);

	void clearCosmetic(Player player, CosmeticUser user);

	boolean hasAlreadyEquipedCosmetic(CosmeticUser user, CosmeticType cosmeticType);

	boolean canBeEquipped(Player player);

	Cosmetic<?> create(CosmeticUser owner, CosmeticCategory category, CosmeticType cosmeticType);

}
