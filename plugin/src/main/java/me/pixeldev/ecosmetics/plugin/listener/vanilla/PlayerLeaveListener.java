package me.pixeldev.ecosmetics.plugin.listener.vanilla;

import me.pixeldev.alya.api.auto.listener.AutoListener;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;

@AutoListener
public class PlayerLeaveListener implements Listener {

	@Inject private Plugin plugin;
	@Inject private CosmeticUserService userService;
	@Inject private PetEntityHandler petEntityHandler;

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		userService.saveUser(player)
			.callback(response -> {
				CosmeticUser cosmeticUser = response.getResponse();

				if (cosmeticUser == null) {
					return;
				}

				if (cosmeticUser.getCurrentCategory() == CosmeticCategory.MINIATURES) {
					Bukkit.getScheduler().runTaskLater(
						plugin,
						() -> petEntityHandler.destroy((PetCosmetic) cosmeticUser.getCurrentCosmetic()),
						1
					);
				}
			});
	}

}
