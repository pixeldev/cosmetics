package me.pixeldev.ecosmetics.api.cosmetic.pet.equipment;

import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProvider;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SkinEquipmentFrame implements EquipmentFrame {

	private final SkinProvider skinProvider;
	private final int delay;

	public SkinEquipmentFrame(SkinProvider skinProvider, int delay) {
		this.skinProvider = skinProvider;
		this.delay = delay;
	}

	@Override
	public ItemStack getItem(Player viewer) {
		return skinProvider.getSkin(viewer);
	}

	@Override
	public int getDelay() {
		return delay;
	}

	@Override
	public int getSlot() {
		return 4;
	}

}
