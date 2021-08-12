package me.pixeldev.ecosmetics.plugin;

import me.pixeldev.alya.bukkit.BukkitBasePlugin;
import me.pixeldev.ecosmetics.plugin.module.MainModule;

import me.yushust.inject.Module;

public final class CosmeticsPlugin extends BukkitBasePlugin {

	@Override
	public Module getMainModule() {
		return new MainModule(this);
	}

}