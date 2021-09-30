package me.pixeldev.ecosmetics.plugin.command.cosmetic;

import me.fixeddev.commandflow.annotated.CommandClass;
import me.fixeddev.commandflow.annotated.annotation.Command;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.fixeddev.commandflow.exception.CommandException;

import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticHandler;
import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.properties.PetCosmeticAuthorizer;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.EffectCosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.PetCosmeticType;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import net.kyori.text.TranslatableComponent;

import org.bukkit.entity.Player;

import javax.inject.Inject;

public class GetCosmeticCommand implements CommandClass {

	@Inject private CosmeticHandler cosmeticHandler;
	@Inject private PetCosmeticAuthorizer petCosmeticAuthorizer;

	@Command(names = "get")
	public void run(@Sender CosmeticUser user,
									CosmeticCategory category,
									CosmeticType cosmeticType) {
		Player sender = user.getPlayer();
		if (!petCosmeticAuthorizer.isAuthorized(sender, cosmeticType)) {
			throw new CommandException(TranslatableComponent.of("command.no-permission"));
		}

		Cosmetic<?> currentCosmetic = user.getCurrentCosmetic();

		if (currentCosmetic != null) {
			cosmeticHandler.unequipCosmetic(user);
		}

		Cosmetic<?> cosmetic = null;
		if (category == CosmeticCategory.MINIATURES) {
			cosmetic = new PetCosmetic(sender, (PetCosmeticType) cosmeticType);
		} else if (category == CosmeticCategory.EFFECTS) {
			cosmetic = new EffectCosmetic(sender, (EffectCosmeticType) cosmeticType);
		}

		if (cosmetic == null) {
			return;
		}

		cosmeticHandler.equipCosmetic(user, cosmetic);
	}

}
