package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.libraryaddict.disguise.disguisetypes.DisguiseType;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;

import org.bukkit.Material;

public class MorphCosmeticType extends AbstractCosmeticType {

	private final DisguiseType disguiseType;
	private final Material handMaterial;

	public MorphCosmeticType(String name, String permission,
													 String configurationIdentifier,
													 Material menuIcon, CosmeticCategory category,
													 DisguiseType disguiseType, Material handMaterial) {
		super(name, permission, configurationIdentifier, menuIcon, category);
		this.disguiseType = disguiseType;
		this.handMaterial = handMaterial;
	}

	public DisguiseType getDisguiseType() {
		return disguiseType;
	}

	public Material getHandMaterial() {
		return handMaterial;
	}

}