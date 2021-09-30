package me.pixeldev.ecosmetics.plugin.cosmetic;

import me.pixeldev.alya.bukkit.translation.sender.SendingModes;
import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticHandler;
import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import me.yushust.message.MessageHandler;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;

public class SimpleCosmeticHandler implements CosmeticHandler {

	@Inject private Plugin plugin;
	@Inject private PetEntityHandler petEntityHandler;
	@Inject private MessageHandler messageHandler;

	@Override
	public void equipCosmetic(CosmeticUser user, Cosmetic<?> cosmetic) {
		user.setCurrentCosmetic(cosmetic);

		switch (cosmetic.getCategory()) {
			case MINIATURES: {
				petEntityHandler.create((PetCosmetic) cosmetic);
				break;
			}
			case EFFECTS: {
				EffectCosmetic effectCosmetic = (EffectCosmetic) cosmetic;
				effectCosmetic.getSpectators().addSpectator(user.getPlayer());
				break;
			}
		}

		messageHandler.sendIn(user.getPlayer(), SendingModes.SUCCESS, "cosmetic.handler.success-equip");
	}

	@Override
	public void unequipCosmetic(CosmeticUser user) {
		Cosmetic<?> currentCosmetic = user.getCurrentCosmetic();

		switch (user.getCurrentCategory()) {
			case MINIATURES: {
				Bukkit.getScheduler().runTaskLater(
					plugin,
					() -> petEntityHandler.destroy((PetCosmetic) currentCosmetic),
					1
				);
				break;
			}
		}
	}

}
