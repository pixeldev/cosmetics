package me.pixeldev.ecosmetics.plugin.listener.vanilla;

import me.pixeldev.alya.api.auto.listener.AutoListener;
import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCreator;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticTypeRegistry;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;

@AutoListener
public class PlayerJoinListener implements Listener {

	@Inject private Plugin plugin;
	@Inject private PetEntityHandler petEntityHandler;
	@Inject private CosmeticUserService userService;
	@Inject private CosmeticTypeRegistry cosmeticTypeRegistry;
	@Inject private CosmeticCreator cosmeticCreator;

	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		userService.getUserOrCreate(player)
			.callback(response -> {
				CosmeticUser cosmeticUser = response.getResponse();

				if (cosmeticUser == null) {
					return;
				}

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

				Cosmetic<?> cosmetic = cosmeticCreator.create(
					cosmeticCategory, player, cosmeticType
				);

				cosmeticUser.setCurrentCosmetic(cosmetic);

				if (cosmetic.getCategory() == CosmeticCategory.MINIATURES) {
					Bukkit.getScheduler().runTaskLater(
						plugin,
						() -> petEntityHandler.create((PetCosmetic) cosmetic),
						1
					);
				}
			});
	}

}
