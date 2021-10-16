package me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.frame;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface EquipmentFrame {

	ItemStack getItem(Player viewer);

	int getDelay();

	int getSlot();

}
