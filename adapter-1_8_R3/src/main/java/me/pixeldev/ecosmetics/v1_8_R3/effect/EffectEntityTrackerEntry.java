package me.pixeldev.ecosmetics.v1_8_R3.effect;

import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.event.PreAddCosmeticSpectatorEvent;
import me.pixeldev.ecosmetics.v1_8_R3.track.AbstractEntityTrackerEntry;

import net.minecraft.server.v1_8_R3.EntityPlayer;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class EffectEntityTrackerEntry
	extends AbstractEntityTrackerEntry {

	private final EffectCosmetic effectCosmetic;

	public EffectEntityTrackerEntry(EffectCosmetic effectCosmetic) {
		this.effectCosmetic = effectCosmetic;
	}

	@Override
	protected Location getLocation() {
		return effectCosmetic.getPlayer().getLocation();
	}

	@Override
	protected void show(EntityPlayer player) {
		PreAddCosmeticSpectatorEvent event = new PreAddCosmeticSpectatorEvent(
			player.getBukkitEntity(), effectCosmetic
		);

		Bukkit.getPluginManager().callEvent(event);

		if (event.isCancelled()) {
			return;
		}

		effectCosmetic.getSpectators().addSpectator(
			player.getBukkitEntity()
		);
	}

	@Override
	protected void hide(EntityPlayer player) {
		effectCosmetic.getSpectators().removeSpectator(
			player.getBukkitEntity()
		);
	}

}
