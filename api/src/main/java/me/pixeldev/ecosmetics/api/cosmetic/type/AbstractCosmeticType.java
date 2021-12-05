package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.item.MenuIconData;

public abstract class AbstractCosmeticType
	implements CosmeticType {

	private final String permission;
	private final String identifier;
	private final MenuIconData menuIcon;
	private final CosmeticCategory category;

	public AbstractCosmeticType(String permission,
															String identifier,
															MenuIconData menuIcon, CosmeticCategory category) {
		this.permission = permission;
		this.identifier = identifier;
		this.menuIcon = menuIcon;
		this.category = category;
	}

	@Override
	public String getPermission() {
		return permission;
	}

	@Override
	public String getIdentifier() {
		return identifier;
	}

	@Override
	public MenuIconData getMenuIcon() {
		return menuIcon;
	}

	@Override
	public CosmeticCategory getCategory() {
		return category;
	}

}
