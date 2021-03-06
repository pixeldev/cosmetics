package me.pixeldev.ecosmetics.plugin.listener.cosmetic;

import me.pixeldev.alya.api.auto.listener.AutoListener;
import me.pixeldev.alya.bukkit.tick.ServerTickCause;
import me.pixeldev.alya.bukkit.tick.ServerTickEvent;
import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

import javax.inject.Inject;

@AutoListener
public class CosmeticAnimationListener implements Listener {

	@Inject private CosmeticUserService userService;
	@Inject private PetEntityHandler petEntityHandler;

	@EventHandler
	public void onTick(ServerTickEvent event) {
		if (event.getServerTickCause() != ServerTickCause.TICK) {
			return;
		}

		for (CosmeticUser cosmeticUser : userService.getAllCachedUsers()) {
			Cosmetic<?> cosmetic = cosmeticUser.getCurrentCosmetic();
			CosmeticCategory category = cosmeticUser.getCurrentCategory();

			if (cosmetic == null) {
				continue;
			}

			if (cosmetic.isEquipped()) {
				switch (category) {
					case MINIATURES: {
						PetCosmetic petCosmetic = (PetCosmetic) cosmetic;

						petCosmetic.run();
						petEntityHandler.displayAnimation(petCosmetic);
						break;
					}
					case EFFECTS: {
						EffectCosmetic effectCosmetic = (EffectCosmetic) cosmetic;

						effectCosmetic.run();
						break;
					}
				}
			}
		}
	}

}
