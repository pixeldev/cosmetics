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
import org.bukkit.event.player.PlayerJoinEvent;

import javax.inject.Inject;

@AutoListener
public class PlayerJoinListener implements Listener {

	@Inject private CosmeticHandler cosmeticHandler;
	@Inject private CosmeticUserService userService;
	@Inject private CosmeticTypeRegistry cosmeticTypeRegistry;

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		userService.getUserOrCreate(player)
			.callback(response -> {
				CosmeticUser cosmeticUser = response.getResponse();

				if (cosmeticUser == null) {
					return;
				}

				cosmeticUser.setPlayerReference(player);

				CosmeticCategory cosmeticCategory = cosmeticUser.getCurrentCategory();

				if (cosmeticCategory == null) {
					return;
				}

				CosmeticType cosmeticType = cosmeticTypeRegistry.getCosmeticType(
					cosmeticCategory, cosmeticUser.getCurrentTypeKey()
				);

				if (cosmeticType == null) {
					cosmeticUser.setCurrentCategory(null);
					cosmeticUser.setCurrentTypeKey(null);
					return;
				}

				Cosmetic<?> cosmetic = cosmeticHandler.create(
					cosmeticUser, cosmeticCategory, cosmeticType
				);

				cosmeticHandler.equipCosmetic(cosmeticUser, cosmetic);
			});
	}

}
