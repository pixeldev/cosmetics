package me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.frame;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SimpleEquipmentFrame implements EquipmentFrame {

	private final ItemStack itemStack;
	private final int delay;
	private final int slot;

	public SimpleEquipmentFrame(ItemStack itemStack, int delay, int slot) {
		this.itemStack = itemStack;
		this.delay = delay;
		this.slot = slot;
	}

	@Override
	public ItemStack getItem(Player viewer) {
		return itemStack;
	}

	@Override
	public int getDelay() {
		return delay;
	}

	@Override
	public int getSlot() {
		return slot;
	}
}
