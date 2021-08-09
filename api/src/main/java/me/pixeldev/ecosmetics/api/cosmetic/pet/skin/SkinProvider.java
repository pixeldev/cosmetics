package me.pixeldev.ecosmetics.api.cosmetic.pet.skin;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import team.unnamed.gui.core.item.type.ItemBuilder;
import team.unnamed.gui.core.item.type.SkullItemBuilder;

/**
 * Represents the way in which the skin of a
 * pet will be created.
 */
public interface SkinProvider {

	/**
	 * @return Type of the current skin provider.
	 */
	SkinProviderType getType();

	/**
	 * @return Current value of the skin without modification.
	 */
	String getRawValue();

	/**
	 * Creates the corresponding {@link ItemBuilder} based on
	 * the current skin value.
	 * @param viewer Viewer who will be displayed the skin.
	 * @return A not null instance of {@link SkullItemBuilder} with the properties set.
	 */
	ItemBuilder getItemBuilder(Player viewer);

	/**
	 * @param viewer Viewer who will be displayed the skin.
	 * @return A built instance of the {@link SkinProvider#getItemBuilder(Player)}
	 */
	default ItemStack getSkin(Player viewer) {
		return getItemBuilder(viewer).build();
	}

	/**
	 * Changes the value of the data that it currently have to create the skin.
	 * @param newSkin New value of the skin data.
	 */
	default void changeSkin(String newSkin) {

	}

	static SkinProvider viewerProvider() {
		return new ViewerSkinProvider();
	}

	static SkinProvider urlProvider(String urlId) {
		return new UrlSkinProvider(urlId);
	}

	static SkinProvider playerProvider(String playerName) {
		return new PlayerSkinProvider(playerName);
	}

}