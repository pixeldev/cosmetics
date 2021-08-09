package me.pixeldev.ecosmetics.api.cosmetic.pet.skin;

import org.bukkit.entity.Player;
import team.unnamed.gui.core.item.type.ItemBuilder;

/**
 * Creates a {@link SkinProvider} using a specified player skull.
 */
public class PlayerSkinProvider implements SkinProvider {

	private String playerName;

	protected PlayerSkinProvider(String playerName) {
		this.playerName = playerName;
	}

	@Override
	public SkinProviderType getType() {
		return SkinProviderType.PLAYER;
	}

	@Override
	public String getRawValue() {
		return playerName;
	}

	@Override
	public ItemBuilder getItemBuilder(Player viewer) {
		return ItemBuilder.newSkullBuilder(1)
			.setOwner(playerName);
	}

	@Override
	public void changeSkin(String newSkin) {
		this.playerName = newSkin;
	}

}