package me.pixeldev.ecosmetics.plugin.loader;

import me.pixeldev.alya.api.loader.Loader;
import me.pixeldev.ecosmetics.plugin.cosmetic.pet.PetShowerRunnable;

import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import javax.inject.Inject;

public final class PetShowerLoader implements Loader {

	@Inject private Plugin plugin;
	@Inject private PetShowerRunnable petShowerRunnable;

	@Override
	public void load() {
		Bukkit.getScheduler().runTaskTimer(
			plugin, petShowerRunnable, 0, 100
		);
	}

}