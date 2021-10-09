package me.pixeldev.ecosmetics.plugin.command.cosmetic;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.fixeddev.commandflow.exception.NoPermissionsException;

import me.pixeldev.alya.bukkit.translation.sender.SendingModes;
import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticHandler;
import me.pixeldev.ecosmetics.api.cosmetic.pet.properties.PetCosmeticAuthorizer;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import me.yushust.message.MessageHandler;

import net.kyori.text.TranslatableComponent;

import org.bukkit.entity.Player;

import javax.inject.Inject;

@Command(names = "get")
public class GetCosmeticCommand implements CommandClass {

	@Inject private CosmeticHandler cosmeticHandler;
	@Inject private PetCosmeticAuthorizer petCosmeticAuthorizer;
	@Inject private MessageHandler messageHandler;

	@Command(names = "")
	public void run(@Sender CosmeticUser user,
									CosmeticCategory category,
									CosmeticType cosmeticType) {
		Player sender = user.getPlayer();
		if (!petCosmeticAuthorizer.isAuthorized(sender, cosmeticType)) {
			throw new NoPermissionsException(TranslatableComponent.of("command.no-permission"));
		}

		if (cosmeticHandler.hasAlreadyEquipedCosmetic(user, cosmeticType)) {
			messageHandler.sendIn(sender, SendingModes.ERROR, "cosmetic.handler.already-equipped");
			return;
		}

		if (!cosmeticHandler.canBeEquipped(sender)) {
			messageHandler.sendIn(sender, SendingModes.ERROR, "cosmetic.handler.can-not-be-equipped");
			return;
		}

		Cosmetic<?> currentCosmetic = user.getCurrentCosmetic();

		if (currentCosmetic != null) {
			cosmeticHandler.unequipCosmetic(user, true);
		}

		Cosmetic<?> cosmetic = cosmeticHandler.create(user, category, cosmeticType);

		if (cosmetic == null) {
			return;
		}

		cosmeticHandler.equipCosmetic(user, cosmetic);
		messageHandler.sendIn(sender, SendingModes.SUCCESS, "cosmetic.handler.success-equip");
	}

}
