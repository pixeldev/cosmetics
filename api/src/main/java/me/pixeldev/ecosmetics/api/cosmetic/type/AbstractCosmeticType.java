package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;

import org.bukkit.Material;

public abstract class AbstractCosmeticType
	implements CosmeticType {

	private final String name;
	private final String permission;
	private final String configurationIdentifier;
	private final Material menuIcon;
	private final CosmeticCategory category;

	public AbstractCosmeticType(String name, String permission,
															String configurationIdentifier,
															Material menuIcon, CosmeticCategory category) {
		this.name = name;
		this.permission = permission;
		this.configurationIdentifier = configurationIdentifier;
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
	public String getConfigurationIdentifier() {
		return configurationIdentifier;
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