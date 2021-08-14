package me.pixeldev.ecosmetics.plugin.cosmetic.type;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProvider;
import me.pixeldev.ecosmetics.api.cosmetic.type.AbstractCosmeticType;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

public class PetCosmeticType extends AbstractCosmeticType {

	private final Set<ItemStack> armorContent;
	private final SkinProvider skinProvider;
	private final Material materialInHand;
	private final boolean invisible;
	private final boolean arms;

	public PetCosmeticType(String name, String permission,
												 String configurationIdentifier,
												 Material menuIcon, CosmeticCategory category,
												 Set<ItemStack> armorContent, SkinProvider skinProvider,
												 Material materialInHand, boolean invisible, boolean arms) {
		super(name, permission, configurationIdentifier, menuIcon, category);
		this.armorContent = armorContent;
		this.skinProvider = skinProvider;
		this.materialInHand = materialInHand;
		this.invisible = invisible;
		this.arms = arms;
	}

	public Set<ItemStack> getArmorContent() {
		return armorContent;
	}

	public SkinProvider getSkinProvider() {
		return skinProvider;
	}

	public Material getMaterialInHand() {
		return materialInHand;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public boolean isArms() {
		return arms;
	}

}