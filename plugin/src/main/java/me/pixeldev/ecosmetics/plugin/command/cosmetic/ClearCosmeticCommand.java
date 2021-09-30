package me.pixeldev.ecosmetics.plugin.command.cosmetic;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;

import me.pixeldev.alya.bukkit.translation.sender.SendingModes;
import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticHandler;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import me.yushust.message.MessageHandler;

import javax.inject.Inject;

@Command(names = "clear")
public class ClearCosmeticCommand implements CommandClass {

	@Inject private CosmeticHandler cosmeticHandler;
	@Inject private MessageHandler messageHandler;

	@Command(names = "")
	public void run(@Sender CosmeticUser user) {
		Cosmetic<?> currentCosmetic = user.getCurrentCosmetic();

		if (currentCosmetic == null) {
			messageHandler.sendIn(user.getPlayer(), SendingModes.ERROR, "cosmetic.handler.no-cosmetic-equiped");
			return;
		}

		if (cosmeticHandler.unequipCosmetic(user)) {
			messageHandler.sendIn(user.getPlayer(), SendingModes.SUCCESS, "cosmetic.handler.success-cleared");
		}
	}

}
