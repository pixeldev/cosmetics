package me.pixeldev.ecosmetics.plugin.cosmetic;

import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCreator;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.PetCosmeticType;

import org.bukkit.entity.Player;

public class SimpleCosmeticCreator implements CosmeticCreator {
	@Override
	public Cosmetic<?> create(CosmeticCategory category, Player owner, CosmeticType cosmeticType) {
		switch (category) {
			case MINIATURES: {
				return new PetCosmetic(owner, (PetCosmeticType) cosmeticType);
			}
			case MORPHS: {

			}
			case EFFECTS: {

			}
			default: return null;
		}
	}
}