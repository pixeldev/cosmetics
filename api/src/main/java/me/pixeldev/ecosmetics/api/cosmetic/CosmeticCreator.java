package me.pixeldev.ecosmetics.api.cosmetic;

import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;

import org.bukkit.entity.Player;

public interface CosmeticCreator {

	Cosmetic<?> create(CosmeticCategory category, Player owner, CosmeticType cosmeticType);

}