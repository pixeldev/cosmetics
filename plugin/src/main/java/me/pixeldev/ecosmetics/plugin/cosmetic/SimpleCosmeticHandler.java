package me.pixeldev.ecosmetics.plugin.cosmetic;

import com.sk89q.worldguard.bukkit.WGBukkit;
import com.sk89q.worldguard.protection.ApplicableRegionSet;
import com.sk89q.worldguard.protection.regions.ProtectedRegion;

import me.pixeldev.alya.api.yaml.YamlFileConfiguration;
import me.pixeldev.alya.bukkit.translation.sender.SendingModes;
import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticHandler;
import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectHandler;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.EffectCosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.pet.PetCosmeticType;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import me.yushust.message.MessageHandler;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import java.util.HashSet;
import java.util.Set;

public class SimpleCosmeticHandler implements CosmeticHandler {

	@Inject private Plugin plugin;
	@Inject private YamlFileConfiguration config;
	@Inject private MessageHandler messageHandler;
	@Inject private PetEntityHandler petEntityHandler;
	@Inject private EffectHandler effectHandler;

	@Override
	public void assignAndEquipCosmetic(Player player, CosmeticUser user,
																		 CosmeticCategory category, CosmeticType cosmeticType) {
		if (!canBeEquipped(player)) {
			messageHandler.sendIn(player, SendingModes.ERROR, "cosmetic.handler.can-not-be-equipped");
			return;
		}

		Cosmetic<?> currentCosmetic = user.getCurrentCosmetic();

		if (currentCosmetic != null) {
			unequipCosmetic(user, true);
		}

		Cosmetic<?> cosmetic = create(user, category, cosmeticType);

		if (cosmetic == null) {
			return;
		}

		equipCosmetic(user, cosmetic);
		messageHandler.sendIn(player, SendingModes.SUCCESS, "cosmetic.handler.success-equip");
	}

	@Override
	public void equipCosmetic(CosmeticUser user, Cosmetic<?> cosmetic) {
		user.setCurrentCosmetic(cosmetic);
		Runnable runnable = null;

		switch (cosmetic.getCategory()) {
			case MINIATURES: {
				runnable = () -> petEntityHandler.create((PetCosmetic) cosmetic);
				break;
			}
			case EFFECTS: {
				EffectCosmetic effectCosmetic = (EffectCosmetic) cosmetic;
				runnable = () -> effectHandler.create(effectCosmetic);
				break;
			}
		}

		if (runnable == null) {
			return;
		}

		Bukkit.getScheduler().runTaskLater(plugin, runnable, 2);
	}

	@Override
	public void unequipCosmetic(CosmeticUser user, boolean removeFromUser) {
		Cosmetic<?> currentCosmetic = user.getCurrentCosmetic();
		Runnable runnable = null;

		switch (user.getCurrentCategory()) {
			case MINIATURES: {
				runnable = () -> petEntityHandler.destroy((PetCosmetic) currentCosmetic);
				break;
			}
			case EFFECTS: {
				runnable = () -> effectHandler.destroy((EffectCosmetic) currentCosmetic);
				break;
			}
		}

		if (runnable == null) {
			return;
		}

		if (removeFromUser) {
			user.setCurrentCosmetic(null);
		}

		Bukkit.getScheduler().runTaskLater(plugin, runnable, 1);
	}

	@Override
	public void clearCosmetic(Player player, CosmeticUser user) {
		Cosmetic<?> currentCosmetic = user.getCurrentCosmetic();

		if (currentCosmetic == null) {
			messageHandler.sendIn(user.getPlayer(), SendingModes.ERROR, "cosmetic.handler.no-cosmetic-equipped");
			return;
		}

		unequipCosmetic(user, true);
		messageHandler.sendIn(user.getPlayer(), SendingModes.SUCCESS, "cosmetic.handler.success-cleared");
	}

	@Override
	public boolean hasAlreadyEquipedCosmetic(CosmeticUser user, CosmeticType cosmeticType) {
		String currentTypeKey = user.getCurrentTypeKey();

		if (currentTypeKey == null) {
			return false;
		}

		return currentTypeKey.equals(cosmeticType.getIdentifier());
	}

	@Override
	public boolean canBeEquipped(Player player) {
		Set<String> deniedRegions = new HashSet<>(config.getStringList(
			"cosmetics.denied-regions"
		));

		ApplicableRegionSet regions = WGBukkit.getRegionManager(player.getWorld())
			.getApplicableRegions(player.getLocation());

		for (ProtectedRegion region : regions.getRegions()) {
			if (deniedRegions.contains(region.getId())) {
				return false;
			}
		}

		return true;
	}

	@Override
	public Cosmetic<?> create(CosmeticUser owner, CosmeticCategory category, CosmeticType cosmeticType) {
		switch (category) {
			case MINIATURES: {
				return new PetCosmetic(owner.getPlayerReference(), (PetCosmeticType) cosmeticType);
			}
			case MORPHS: {

			}
			case EFFECTS: {
				return new EffectCosmetic(owner.getPlayerReference(), (EffectCosmeticType) cosmeticType);
			}
			default:
				return null;
		}
	}

}
