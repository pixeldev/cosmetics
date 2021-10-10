package me.pixeldev.ecosmetics.plugin.menu;

import me.pixeldev.alya.bukkit.menu.AbstractGUICreator;
import me.pixeldev.alya.bukkit.menu.GUICreator;
import me.pixeldev.alya.bukkit.translation.sender.SendingModes;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticHandler;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

public abstract class CosmeticsGUICreator
	extends AbstractGUICreator {

	@Inject
	@Named("main")
	private GUICreator mainMenu;
	@Inject protected CosmeticHandler cosmeticHandler;

	public CosmeticsGUICreator(String menuKey) {
		super(menuKey);
	}

	public ItemClickable createNextPageItem(Player issuer, int page) {
		return ItemClickable.builderCancellingEvent(50)
			.setItemStack(createInteractPageItem(issuer, "next-page", page))
			.build();
	}

	public ItemClickable createPreviousPageItem(Player issuer, int page) {
		return ItemClickable.builderCancellingEvent(48)
			.setItemStack(createInteractPageItem(issuer, "previous-page", page))
			.build();
	}

	public ItemClickable createItemIfNoCosmetics(Player issuer) {
		return ItemClickable.builderCancellingEvent(22)
			.setItemStack(ItemBuilder.newBuilder(Material.GLASS_BOTTLE)
				.setName(messageHandler.get(issuer, "menu.no-cosmetics-item.name"))
				.setLore(messageHandler.getMany(issuer, "menu.no-cosmetics-item.lore"))
				.build()
			)
			.build();
	}

	public ItemClickable createClearCosmeticItem(Player issuer, CosmeticUser user) {
		return ItemClickable.builder(49)
			.setItemStack(ItemBuilder.newBuilder(Material.BARRIER)
				.setName(messageHandler.get(issuer, "menu.clear-cosmetic-item.name"))
				.setLore(messageHandler.getMany(issuer, "menu.clear-cosmetic-item.lore"))
				.build()
			)
			.setAction(event -> {
				cosmeticHandler.clearCosmetic(issuer, user);
				return true;
			})
			.build();
	}

	public ItemClickable createBackToMainMenuItem(Player issuer, Object... extraData) {
		return createBackItem(
			issuer, 53,
			ItemBuilder.newBuilder(Material.ARROW)
				.setName(messageHandler.get(issuer, "menu.back-main-menu-item.name"))
				.setLore(messageHandler.getMany(issuer, "menu.back-main-menu-item.lore"))
				.build(),
			() -> mainMenu,
			extraData
		);
	}

	public ItemClickable createItemIfNoPermission(Player issuer, String identifier, List<String> lore) {
		lore.addAll(messageHandler.getMany(issuer, "menu.no-permission-lore"));

		return ItemClickable.builder()
			.setItemStack(ItemBuilder.newBuilder(Material.INK_SACK, 1, (short) 8)
				.setName(getItemName(issuer, identifier))
				.setLore(lore)
				.build()
			)
			.setAction(event -> {
				issuer.closeInventory();
				messageHandler.sendIn(issuer, SendingModes.ERROR, "menu.no-permission-message");
				return true;
			})
			.build();
	}

	private ItemStack createInteractPageItem(Player issuer, String path, int page) {
		return ItemBuilder.newBuilder(Material.ARROW)
			.setName(messageHandler.get(issuer, "menu." + path + "-item.name"))
			.setLore(messageHandler.replacingMany(issuer, "menu." + path + "-item.lore", "%page%", page))
			.build();
	}

}
