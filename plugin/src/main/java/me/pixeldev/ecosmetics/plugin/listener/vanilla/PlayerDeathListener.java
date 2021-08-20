package me.pixeldev.ecosmetics.plugin.listener.vanilla;

import me.pixeldev.alya.api.auto.AutoListener;
import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import javax.inject.Inject;
import java.util.Optional;
import java.util.UUID;

@AutoListener
public class PlayerDeathListener implements Listener {

	@Inject private PetEntityHandler petEntityHandler;
	@Inject private CosmeticUserService userService;

	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		Player player = event.getEntity();
		userService.getUserByPlayerSync(player)
			.ifPresent(user -> {
				Cosmetic<?> currentCosmetic = user.getCurrentCosmetic();

				if (currentCosmetic != null) {
					petEntityHandler.destroy(player, (PetCosmetic) currentCosmetic);
				}

				for (UUID renderedMiniature : user.getRenderedMiniatures()) {
					Optional<CosmeticUser> renderedMiniatureOwnerOptional
						= userService.getUserByIdSync(renderedMiniature);

					if (!renderedMiniatureOwnerOptional.isPresent()) {
						return;
					}

					CosmeticUser renderedMiniatureOwner = renderedMiniatureOwnerOptional.get();
					petEntityHandler.destroy(
						player,
						(PetCosmetic) renderedMiniatureOwner.getCurrentCosmetic()
					);
				}
			});
	}

}