package me.pixeldev.ecosmetics.plugin.command.cosmetic;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticHandler;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import javax.inject.Inject;

@Command(names = "clear")
public class ClearCosmeticCommand implements CommandClass {

	@Inject private CosmeticHandler cosmeticHandler;

	@Command(names = "")
	public void run(@Sender CosmeticUser user) {
		cosmeticHandler.clearCosmetic(user.getPlayer(), user);
	}

}
