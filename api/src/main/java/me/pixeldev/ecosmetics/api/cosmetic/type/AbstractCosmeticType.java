package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;

import org.bukkit.Material;

public abstract class AbstractCosmeticType
	implements CosmeticType {

	private final String name;
	private final String permission;
	private final String identifier;
	private final Material menuIcon;
	private final CosmeticCategory category;

	public AbstractCosmeticType(String name, String permission,
															String identifier,
															Material menuIcon, CosmeticCategory category) {
		this.name = name;
		this.permission = permission;
		this.identifier = identifier;
		this.menuIcon = menuIcon;
		this.category = category;
	}

	@Override
	public String getName() {
		return name;
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
	public Material getMenuIcon() {
		return menuIcon;
	}

	@Override
	public CosmeticCategory getCategory() {
		return category;
	}

}