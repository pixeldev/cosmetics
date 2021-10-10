package me.pixeldev.ecosmetics.plugin.menu;

import me.pixeldev.alya.bukkit.menu.AbstractGUICreator;

import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;

import javax.inject.Inject;

public class CosmeticMainMenu extends AbstractGUICreator {

	@Inject private CosmeticMiniaturesMenu miniaturesMenu;

	public CosmeticMainMenu() {
		super("main");
	}

	@Override
	public Inventory create(Player issuer, Object... extraData) {
		return GUIBuilder.builderStringLayout(getTitle(issuer), 3)
			.setLayoutLines(
				"xxxxxxxxx",
				"xxaxxxbxx",
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
					issuer.openInventory(miniaturesMenu.create(issuer, extraData));
					return true;
				})
				.setItemStack(createItem(issuer, Material.ARMOR_STAND, "miniatures").build())
				.build()
			)
			.setLayoutItem('b', ItemClickable.builder()
				.setAction(event -> {
					//TODO: Open effects menu.
					return true;
				})
				.setItemStack(createItem(issuer, Material.EMERALD, "effects").build())
				.build()
			)
			.build();
	}

}
