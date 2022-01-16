package me.pixeldev.ecosmetics.plugin;

import me.pixeldev.alya.bukkit.BukkitBasePlugin;
import me.pixeldev.ecosmetics.plugin.module.MainModule;

import me.pixeldev.ecosmetics.plugin.service.MainService;
import me.yushust.inject.Module;
import org.bukkit.Bukkit;
import team.unnamed.gui.core.GUIListeners;

import javax.inject.Inject;

public final class CosmeticsPlugin extends BukkitBasePlugin {

	@Inject private MainService mainService;

	@Override
	public void onDisable() {
		mainService.stop();
	}

	@Override
	public void onEnable() {
        Bukkit.getPluginManager().registerEvents(new GUIListeners(), this);
		mainService.start();
	}

	@Override
	public Module getMainModule() {
		return new MainModule(this);
	}

}