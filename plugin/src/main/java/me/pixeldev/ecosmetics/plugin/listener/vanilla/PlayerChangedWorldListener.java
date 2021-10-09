package me.pixeldev.ecosmetics.plugin.listener.vanilla;

import me.pixeldev.alya.api.auto.listener.AutoListener;
import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticHandler;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticTypeRegistry;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerChangedWorldEvent;

import javax.inject.Inject;

@AutoListener
public class PlayerChangedWorldListener
	implements Listener {

	@Inject private CosmeticTypeRegistry cosmeticTypeRegistry;
	@Inject private CosmeticUserService userService;
	@Inject private CosmeticHandler cosmeticHandler;

	@EventHandler
	public void onChangedWorld(PlayerChangedWorldEvent event) {
		Player player = event.getPlayer();
		CosmeticUser user = userService.getUserByPlayer(player);

		if (user == null) {
			return;
		}

		CosmeticCategory category = user.getCurrentCategory();


		if (category != null) {
			String typeKey = user.getCurrentTypeKey();

			cosmeticHandler.unequipCosmetic(user, true);

			CosmeticType cosmeticType = cosmeticTypeRegistry.getCosmeticType(
				category, typeKey
			);

			Cosmetic<?> cosmetic = cosmeticHandler.create(user, category, cosmeticType);
			cosmeticHandler.equipCosmetic(user, cosmetic);
		}
	}

}
