package me.pixeldev.ecosmetics.plugin.menu;

import me.pixeldev.alya.bukkit.menu.AbstractGUICreator;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class GenericTypeMenu extends AbstractGUICreator {

	public GenericTypeMenu(String menuKey) {
		super(menuKey);
	}

	@Override
	public Inventory create(Player issuer, Object... extraData) {
		return null;
	}

}
