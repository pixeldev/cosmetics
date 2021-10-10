package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;

import org.bukkit.Material;

public interface CosmeticType {

	String getPermission();

	String getIdentifier();

	Material getMenuIcon();

	CosmeticCategory getCategory();

}
