package me.pixeldev.ecosmetics.plugin.menu;

import me.pixeldev.alya.bukkit.translation.sender.SendingModes;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.properties.PetCosmeticAuthorizer;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticTypeRegistry;
import me.pixeldev.ecosmetics.api.cosmetic.type.PetCosmeticType;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;
import team.unnamed.gui.core.item.type.ItemBuilder;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class CosmeticMiniaturesMenu extends CosmeticsGUICreator {

	@Inject private CosmeticTypeRegistry cosmeticTypeRegistry;
	@Inject private PetCosmeticAuthorizer petCosmeticAuthorizer;

	public CosmeticMiniaturesMenu() {
		super("miniatures");
	}

	@Override
	public Inventory create(Player issuer, Object... extraData) {
		CosmeticUser cosmeticUser = (CosmeticUser) extraData[0];
		Map<String, CosmeticType> registeredTypes = cosmeticTypeRegistry.getAllTypesFor(CosmeticCategory.MINIATURES);

		return GUIBuilder.builderPaginated(CosmeticType.class, getTitle(issuer), 6)
			.setBounds(10, 35)
			.setEntities(registeredTypes.values())
			.setItemIfNotEntities(ItemClickable.builderCancellingEvent(22)
				.setItemStack(createItem(issuer, Material.GLASS_BOTTLE, "no-cosmetics").build())
				.build()
			)
			.setItemParser(cosmeticType -> {
				PetCosmeticType petCosmeticType = (PetCosmeticType) cosmeticType;
				String identifier = cosmeticType.getIdentifier();
				List<String> lore = getItemLore(issuer, identifier);

				if (!petCosmeticAuthorizer.isAuthorized(issuer, cosmeticType)) {
					return createItemIfNoPermission(issuer, identifier, lore);
				} else {
					ItemStack skin = PetCosmetic.getSkin(petCosmeticType.getSkinProvider(), issuer, issuer);
					ItemMeta meta = skin.getItemMeta();
					meta.setDisplayName(getItemName(issuer, identifier));
					Predicate<InventoryClickEvent> action;

					if (cosmeticHandler.hasAlreadyEquipedCosmetic(cosmeticUser, cosmeticType)) {
						lore.addAll(messageHandler.getMany(issuer, "menu.already-equipped-lore"));
						action = event -> {
							issuer.closeInventory();
							messageHandler.sendIn(issuer, SendingModes.ERROR, "cosmetic.handler.already-equipped");
							return true;
						};
					} else {
						lore.addAll(messageHandler.getMany(issuer, "menu.click-to-equip-lore"));
						action = event -> {
							issuer.closeInventory();

							cosmeticHandler.assignAndEquipCosmetic(
								issuer, cosmeticUser,
								CosmeticCategory.MINIATURES, cosmeticType
							);
							return true;
						};
					}

					meta.setLore(lore);
					skin.setItemMeta(meta);

					return ItemClickable.builder()
						.setItemStack(skin)
						.setAction(action)
						.build();
				}
			})
			.setItemsPerRow(7)
			.setNextPageItem(page -> createNextPageItem(issuer, page))
			.setPreviousPageItem(page -> createPreviousPageItem(issuer, page))
			.addItem(createClearCosmeticItem(issuer, cosmeticUser))
			.build();
	}

}
