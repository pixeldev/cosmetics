package me.pixeldev.ecosmetics.api.cosmetic.pet.properties;

import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;

import org.bukkit.entity.Player;

import javax.inject.Singleton;

@Singleton
public final class PetCosmeticAuthorizer {

	public boolean isAuthorized(Player issuer, CosmeticType cosmeticType) {
		return issuer.hasPermission(cosmeticType.getPermission());
	}

}