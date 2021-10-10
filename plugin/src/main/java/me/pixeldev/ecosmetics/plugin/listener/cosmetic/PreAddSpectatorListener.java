package me.pixeldev.ecosmetics.plugin.listener.cosmetic;

import me.pixeldev.alya.api.auto.listener.AutoListener;
import me.pixeldev.ecosmetics.api.cosmetic.event.PreAddCosmeticSpectatorEvent;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.inject.Inject;

@AutoListener
public class PreAddSpectatorListener implements Listener {

	@Inject private CosmeticUserService userService;

	@EventHandler
	public void onPreAdd(PreAddCosmeticSpectatorEvent event) {
		Player player = event.getPlayer();
		CosmeticUser cosmeticUser = userService.getUserByPlayer(player);

		if (cosmeticUser == null) {
			event.setCancelled(true);
			return;
		}

		if (cosmeticUser.getUuid().equals(event.getCosmetic().getOwnerId())) {
			return;
		}

		event.setCancelled(!cosmeticUser.hasCosmeticsEnabled());
	}

}
