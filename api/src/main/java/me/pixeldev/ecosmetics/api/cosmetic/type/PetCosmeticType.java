package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProvider;

import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class PetCosmeticType extends AbstractCosmeticType {

	private final Set<ItemStack> armorContent;
	private final SkinProvider skinProvider;

	public PetCosmeticType(String name, String permission,
												 String configurationIdentifier, CosmeticCategory category,
												 Set<ItemStack> armorContent, SkinProvider skinProvider) {
		super(name, permission, configurationIdentifier, category);
		this.armorContent = armorContent;
		this.skinProvider = skinProvider;
	}

	public Set<ItemStack> getArmorContent() {
		return armorContent;
	}

	public SkinProvider getSkinProvider() {
		return skinProvider;
	}

}