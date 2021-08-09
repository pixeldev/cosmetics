package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;

public abstract class AbstractCosmeticType
	implements CosmeticType {

	private final String name;
	private final String permission;
	private final String configurationIdentifier;
	private final CosmeticCategory category;

	public AbstractCosmeticType(String name, String permission,
															String configurationIdentifier, CosmeticCategory category) {
		this.name = name;
		this.permission = permission;
		this.configurationIdentifier = configurationIdentifier;
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
	public CosmeticCategory getCategory() {
		return category;
	}

}