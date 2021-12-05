package me.pixeldev.ecosmetics.plugin.command.cosmetic;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;

import me.pixeldev.alya.bukkit.translation.sender.SendingModes;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticHandler;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import me.yushust.message.MessageHandler;
import org.bukkit.entity.Player;

import javax.inject.Inject;

public class RemoveCosmeticCommand implements CommandClass {

	@Inject private MessageHandler messageHandler;
	@Inject private CosmeticHandler cosmeticHandler;

	@Command(names = "remove", permission = "cosmetics.*")
	public void run(@Sender Player issuer, CosmeticUser target) {
		if (cosmeticHandler.clearCosmetic(target)) {
			messageHandler.sendReplacingIn(
				issuer, SendingModes.SUCCESS, "commands.remove.success",
				"%player%", target.getPlayer().getName()
			);
		} else {
			messageHandler.sendIn(issuer, SendingModes.ERROR, "commands.remove.no-equipped");
		}
	}

}
