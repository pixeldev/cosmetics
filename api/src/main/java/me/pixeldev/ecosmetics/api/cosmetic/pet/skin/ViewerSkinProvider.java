package me.pixeldev.ecosmetics.api.cosmetic.pet.skin;

import org.bukkit.entity.Player;
import team.unnamed.gui.core.item.type.ItemBuilder;

/**
 * Creates a {@link SkinProvider} using the skull of
 * the current viewer.
 */
public class ViewerSkinProvider implements SkinProvider {

	protected ViewerSkinProvider() {
	}

	@Override
	public SkinProviderType getType() {
		return SkinProviderType.VIEWER;
	}

	@Override
	public String getRawValue() {
		return "VIEWER";
	}

	@Override
	public ItemBuilder getItemBuilder(Player viewer) {
		return ItemBuilder.newSkullBuilder(1)
			.setOwner(viewer.getName());
	}
}