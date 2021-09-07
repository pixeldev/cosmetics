package me.pixeldev.ecosmetics.plugin.command;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.fixeddev.commandflow.exception.CommandException;

import me.pixeldev.alya.api.auto.command.AutoCommand;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;
import me.pixeldev.ecosmetics.api.cosmetic.pet.properties.PetCosmeticAuthorizer;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.PetCosmeticType;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import net.kyori.text.TranslatableComponent;

import org.bukkit.entity.Player;

import javax.inject.Inject;

@AutoCommand
@Command(names = {"cosmetics", "ecosmetics", "cosmetic"})
public class CosmeticCommands implements CommandClass {

	@Inject private CosmeticUserService userService;
	@Inject private PetEntityHandler entityHandler;
	@Inject private PetCosmeticAuthorizer petCosmeticAuthorizer;

	@Command(names = "get")
	public void runGet(@Sender Player sender,
										 CosmeticCategory category,
										 CosmeticType cosmeticType) {
		if (!petCosmeticAuthorizer.isAuthorized(sender, cosmeticType)) {
			throw new CommandException(TranslatableComponent.of("command.no-permission"));
		}

		if (category == CosmeticCategory.MINIATURES) {
			CosmeticUser cosmeticUser = userService.getUserByPlayer(sender);

			if (cosmeticUser == null) {
				return;
			}

			PetCosmetic petCosmetic = new PetCosmetic(sender, (PetCosmeticType) cosmeticType);

			entityHandler.spawn(sender, petCosmetic);
			cosmeticUser.setCurrentCosmetic(petCosmetic);
		}
	}

}
