package me.pixeldev.ecosmetics.plugin.menu;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectCosmetic;

public class CosmeticEffectsMenu extends CosmeticTypesMenu {

	public CosmeticEffectsMenu() {
		super(
			CosmeticCategory.EFFECTS,
			(player, cosmeticType) -> EffectCosmetic.getMenuIcon(cosmeticType)
		);
	}

}
