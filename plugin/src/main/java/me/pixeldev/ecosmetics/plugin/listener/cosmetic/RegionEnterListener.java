package me.pixeldev.ecosmetics.plugin.listener.cosmetic;

import me.pixeldev.alya.api.auto.listener.AutoListener;
import me.pixeldev.alya.bukkit.tick.ServerTickCause;
import me.pixeldev.alya.bukkit.tick.ServerTickEvent;
import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticHandler;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.inject.Inject;

@AutoListener
public class RegionEnterListener
	implements Listener {

	@Inject private CosmeticHandler cosmeticHandler;
	@Inject private CosmeticUserService userService;

	@EventHandler
	public void onTick(ServerTickEvent event) {
		if (event.getServerTickCause() != ServerTickCause.SECOND) {
			return;
		}

		for (CosmeticUser cosmeticUser : userService.getAllCachedUsers()) {
			Cosmetic<?> cosmetic = cosmeticUser.getCurrentCosmetic();

			if (cosmetic == null) {
				continue;
			}

			Player player = cosmeticUser.getPlayer();

			if (!cosmeticHandler.canBeEquipped(player)) {
				if (cosmetic.isEquipped()) {
					cosmeticHandler.unequipCosmetic(cosmeticUser, false);
					cosmetic.setEquipped(false);
				}
			} else {
				if (!cosmetic.isEquipped()) {
					cosmeticHandler.equipCosmetic(cosmeticUser, cosmetic);
					cosmetic.setEquipped(true);
				}
			}
		}
	}

}
