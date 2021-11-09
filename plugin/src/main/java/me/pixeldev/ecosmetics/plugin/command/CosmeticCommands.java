package me.pixeldev.ecosmetics.plugin.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.annotated.annotation.SubCommandClasses;
import me.fixeddev.commandflow.bukkit.annotation.Sender;

import me.pixeldev.alya.api.auto.command.AutoCommand;
import me.pixeldev.alya.api.yaml.YamlFileConfiguration;
import me.pixeldev.alya.bukkit.menu.GUICreator;
import me.pixeldev.alya.bukkit.translation.sender.SendingModes;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticTypeRegistry;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.plugin.command.arg.ReloadType;
import me.pixeldev.ecosmetics.plugin.command.cosmetic.ClearCosmeticCommand;
import me.pixeldev.ecosmetics.plugin.command.cosmetic.ToggleCosmeticCommand;
import me.pixeldev.ecosmetics.plugin.command.cosmetic.GetCosmeticCommand;

import me.yushust.message.MessageHandler;

import org.bukkit.entity.Player;

import javax.inject.Inject;
import javax.inject.Named;

@AutoCommand
@SubCommandClasses({
	GetCosmeticCommand.class, ClearCosmeticCommand.class,
	ToggleCosmeticCommand.class
})
@Command(names = {"cosmetics", "ecosmetics", "cosmetic", "cosmeticos"})
public class CosmeticCommands implements CommandClass {

	@Inject private MessageHandler messageHandler;
	@Inject private YamlFileConfiguration config;
	@Inject private CosmeticTypeRegistry cosmeticTypeRegistry;
	@Inject @Named("main") private GUICreator mainMenu;

	@Command(names = "")
	public void run(@Sender CosmeticUser user) {
		Player sender = user.getPlayer();
		sender.openInventory(mainMenu.create(sender, user));
	}

	@Command(names = {"help", "?"})
	public void runHelp(@Sender Player sender) {
		messageHandler.sendIn(
			sender, SendingModes.SUCCESS,
			"commands.help"
		);
	}

	@Command(names = {"reload"}, permission = "cosmetics.*")
	public void runReload(@Sender Player sender, ReloadType reloadType) {
		try {
			if (reloadType == ReloadType.CONFIG) {
				config.reload();
				cosmeticTypeRegistry.registerFromAllKnownSections();
			} else {
				messageHandler.getSource().load("es");
			}

			messageHandler.sendReplacingIn(
				sender,
				"success",
				"commands.reload.success",
				"%type%", reloadType
			);
		} catch (Exception e) {
			messageHandler.sendReplacingIn(
				sender,
				"success",
				"commands.reload.error",
				"%type%", reloadType
			);

			e.printStackTrace();
		}
	}

}
