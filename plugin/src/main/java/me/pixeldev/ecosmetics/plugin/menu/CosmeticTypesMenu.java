package me.pixeldev.ecosmetics.plugin.menu;

import me.pixeldev.alya.bukkit.translation.sender.SendingModes;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.properties.PetCosmeticAuthorizer;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticTypeRegistry;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;

import javax.inject.Inject;
import java.util.List;
import java.util.Map;
import java.util.function.BiFunction;
import java.util.function.Predicate;

public class CosmeticTypesMenu extends CosmeticsGUICreator {

	@Inject private CosmeticTypeRegistry cosmeticTypeRegistry;
	@Inject private PetCosmeticAuthorizer petCosmeticAuthorizer;

	private final CosmeticCategory category;
	private final BiFunction<Player, CosmeticType, ItemStack> skinProvider;

	public CosmeticTypesMenu(CosmeticCategory category,
													 BiFunction<Player, CosmeticType, ItemStack> skinProvider) {
		super(category.name().toLowerCase());
		this.category = category;
		this.skinProvider = skinProvider;
	}

	@Override
	public Inventory create(Player issuer, Object... extraData) {
		CosmeticUser cosmeticUser = (CosmeticUser) extraData[0];
		Map<String, CosmeticType> registeredTypes = cosmeticTypeRegistry.getAllTypesFor(category);

		return GUIBuilder.builderPaginated(CosmeticType.class, getTitle(issuer), 6)
			.setBounds(10, 35)
			.setEntities(registeredTypes.values())
			.setItemIfNotEntities(createItemIfNoCosmetics(issuer))
			.setItemParser(cosmeticType -> {
				String identifier = cosmeticType.getIdentifier();
				List<String> lore = getItemLore(issuer, identifier);

				if (!petCosmeticAuthorizer.isAuthorized(issuer, cosmeticType)) {
					return createItemIfNoPermission(issuer, identifier, lore);
				} else {
					ItemStack skin = skinProvider.apply(issuer, cosmeticType);
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
								category, cosmeticType
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
			.addItem(createBackToMainMenuItem(issuer, extraData))
			.build();
	}

}
