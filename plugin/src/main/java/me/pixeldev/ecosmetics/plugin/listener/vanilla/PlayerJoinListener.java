package me.pixeldev.ecosmetics.plugin.listener.vanilla;

import me.pixeldev.alya.api.auto.listener.AutoListener;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCreator;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticTypeRegistry;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import javax.inject.Inject;

@AutoListener
public class PlayerJoinListener implements Listener {

	@Inject private CosmeticUserService userService;
	@Inject private CosmeticTypeRegistry cosmeticTypeRegistry;
	@Inject private CosmeticCreator cosmeticCreator;

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		userService.getUserOrCreate(player)
			.subscribe(user -> {
				if (user == null) {
					return;
				}

				CosmeticCategory cosmeticCategory = user.getCurrentCategory();

				if (cosmeticCategory == null) {
					return;
				}

				CosmeticType cosmeticType = cosmeticTypeRegistry.getCosmeticType(
					cosmeticCategory, user.getCurrentTypeKey()
				);

				if (cosmeticType == null) {
					user.setCurrentCategory(null);
					user.setCurrentTypeKey(null);
					return;
				}

				user.setCurrentCosmetic(cosmeticCreator.create(
					cosmeticCategory, player, cosmeticType
				));
			})
			.query();
	}

}
