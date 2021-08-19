package me.pixeldev.ecosmetics.plugin.listener.vanilla;

import me.pixeldev.alya.api.auto.AutoListener;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import javax.inject.Inject;

@AutoListener
public class PlayerLeaveListener implements Listener {

	@Inject private CosmeticUserService userService;
	@Inject private PetEntityHandler petEntityHandler;

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		Player player = event.getPlayer();
		userService.saveUser(player)
			.subscribe(user -> {
				if (user != null) {
					if (user.getCurrentCategory() == CosmeticCategory.MINIATURES) {
						petEntityHandler.destroy((PetCosmetic) user.getCurrentCosmetic());
					}
				}
			})
			.query();
	}

}