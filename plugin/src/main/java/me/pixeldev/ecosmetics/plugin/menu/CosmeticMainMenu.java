package me.pixeldev.ecosmetics.plugin.menu;

import me.pixeldev.alya.bukkit.menu.AbstractGUICreator;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;

public class CosmeticMainMenu extends AbstractGUICreator {

	public CosmeticMainMenu() {
		super("main");
	}

	@Override
	public Inventory create(Player issuer, Object... extraData) {
		return GUIBuilder.builderStringLayout(getTitle(issuer))
			.setLayoutLines(
				"xxxxxxxxx",
				"xxaxbxcxx",
				"xxxxdxxxx"
			)
			.setLayoutItem('d', ItemClickable.builder()
				.setAction(event -> {
					issuer.closeInventory();
					return true;
				})
				.setItemStack(createItem(issuer, Material.BARRIER, "exit").build())
				.build()
			)
			.setLayoutItem('a', ItemClickable.builder()
				.setAction(event -> {
					//TODO: Open miniatures menu.
					return true;
				})
				.setItemStack(createItem(issuer, Material.ARMOR_STAND, "miniatures").build())
				.build()
			)
			.build();
	}

}
