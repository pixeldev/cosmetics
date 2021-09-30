package me.pixeldev.ecosmetics.plugin.cosmetic;

import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticHandler;
import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectHandler;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.EffectCosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.PetCosmeticType;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;

public class SimpleCosmeticHandler implements CosmeticHandler {

	@Inject private Plugin plugin;
	@Inject private PetEntityHandler petEntityHandler;
	@Inject private EffectHandler effectHandler;

	@Override
	public void equipCosmetic(CosmeticUser user, Cosmetic<?> cosmetic) {
		user.setCurrentCosmetic(cosmetic);
		Runnable runnable = null;

		switch (cosmetic.getCategory()) {
			case MINIATURES: {
				runnable = () -> petEntityHandler.create((PetCosmetic) cosmetic);
				break;
			}
			case EFFECTS: {
				EffectCosmetic effectCosmetic = (EffectCosmetic) cosmetic;
				runnable = () -> effectHandler.create(effectCosmetic);
				break;
			}
		}

		if (runnable == null) {
			return;
		}

		Bukkit.getScheduler().runTaskLater(plugin, runnable, 1);
	}

	@Override
	public boolean unequipCosmetic(CosmeticUser user) {
		Cosmetic<?> currentCosmetic = user.getCurrentCosmetic();
		Runnable runnable = null;

		switch (user.getCurrentCategory()) {
			case MINIATURES: {
				runnable = () -> petEntityHandler.destroy((PetCosmetic) currentCosmetic);
				break;
			}
			case EFFECTS: {
				runnable = () -> effectHandler.destroy((EffectCosmetic) currentCosmetic);
				break;
			}
		}

		if (runnable == null) {
			return false;
		}

		user.setCurrentCosmetic(null);
		Bukkit.getScheduler().runTaskLater(plugin, runnable, 1);
		return true;
	}

	@Override
	public boolean hasAlreadyEquipedCosmetic(CosmeticUser user, CosmeticType cosmeticType) {
		String currentTypeKey = user.getCurrentTypeKey();

		if (currentTypeKey == null) {
			return false;
		}

		return currentTypeKey.equals(cosmeticType.getIdentifier());
	}

	@Override
	public Cosmetic<?> create(CosmeticCategory category, Player owner, CosmeticType cosmeticType) {
		switch (category) {
			case MINIATURES: {
				return new PetCosmetic(owner, (PetCosmeticType) cosmeticType);
			}
			case MORPHS: {

			}
			case EFFECTS: {
				return new EffectCosmetic(owner, (EffectCosmeticType) cosmeticType);
			}
			default: return null;
		}
	}

}
