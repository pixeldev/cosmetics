package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.item.MenuIconData;

public interface CosmeticType {

	String getPermission();

	String getIdentifier();

	MenuIconData getMenuIcon();

	CosmeticCategory getCategory();

}
