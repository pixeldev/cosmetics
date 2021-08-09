package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;

public interface CosmeticType {

	String getName();

	String getPermission();

	String getConfigurationIdentifier();

	CosmeticCategory getCategory();

}