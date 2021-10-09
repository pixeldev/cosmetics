package me.pixeldev.ecosmetics.plugin.listener.vanilla;

import me.pixeldev.alya.api.auto.listener.AutoListener;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticHandler;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;

@AutoListener
public class PlayerLeaveListener implements Listener {

	@Inject private CosmeticHandler cosmeticHandler;
	@Inject private CosmeticUserService userService;

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		userService.saveUser(player)
			.callback(response -> {
				CosmeticUser cosmeticUser = response.getResponse();

				if (cosmeticUser == null) {
					return;
				}

				cosmeticHandler.unequipCosmetic(cosmeticUser, true);
			});
	}

}
