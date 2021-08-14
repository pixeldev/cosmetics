package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;

import org.bukkit.Material;

public interface CosmeticType {

	String getName();

	String getPermission();

	String getConfigurationIdentifier();

	Material getMenuIcon();

	CosmeticCategory getCategory();

}