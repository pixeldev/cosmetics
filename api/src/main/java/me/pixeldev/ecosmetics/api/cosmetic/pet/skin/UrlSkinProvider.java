package me.pixeldev.ecosmetics.api.cosmetic.pet.skin;

import org.bukkit.entity.Player;
import team.unnamed.gui.core.item.type.ItemBuilder;

/**
 * Creates a {@link SkinProvider} using a skull
 * gotten from "https://textures.minecraft.net/texture/"
 * with the given texture id.
 */
public class UrlSkinProvider implements SkinProvider {

	public static final String BASE_URL = "https://textures.minecraft.net/texture/";

	private String urlId;

	protected UrlSkinProvider(String urlId) {
		this.urlId = urlId;
	}

	@Override
	public SkinProviderType getType() {
		return SkinProviderType.URL;
	}

	@Override
	public String getRawValue() {
		return urlId;
	}

	@Override
	public ItemBuilder getItemBuilder(Player viewer) {
		return ItemBuilder.newSkullBuilder(1)
			.setUrl(BASE_URL + urlId);
	}

	@Override
	public void changeSkin(String newSkin) {
		this.urlId = newSkin;
	}
}