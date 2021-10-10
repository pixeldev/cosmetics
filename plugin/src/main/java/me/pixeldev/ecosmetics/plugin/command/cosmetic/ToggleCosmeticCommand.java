package me.pixeldev.ecosmetics.plugin.command.cosmetic;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;

import me.pixeldev.alya.bukkit.translation.sender.SendingModes;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import me.yushust.message.MessageHandler;

import org.bukkit.entity.Player;

import javax.inject.Inject;

@Command(names = "toggle")
public class ToggleCosmeticCommand implements CommandClass {

	@Inject private MessageHandler messageHandler;

	@Command(names = "")
	public void run(@Sender CosmeticUser user) {
		Player sender = user.getPlayer();
		String messagePath = "commands.toggle-cosmetics.success-";

		if (user.hasCosmeticsEnabled()) {
			user.setCosmeticsEnabled(false);
			messagePath += "disabled";
		} else {
			user.setCosmeticsEnabled(true);
			messagePath += "enabled";
		}

		messageHandler.sendIn(sender, SendingModes.SUCCESS, messagePath);
	}

}
