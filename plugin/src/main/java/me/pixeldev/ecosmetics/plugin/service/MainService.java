package me.pixeldev.ecosmetics.plugin.service;

import me.pixeldev.alya.api.loader.Loader;
import me.pixeldev.alya.api.service.Service;
import me.pixeldev.ecosmetics.plugin.menu.ParticleSelectorMenu;

import javax.inject.Inject;
import java.util.Set;

public final class MainService implements Service {

	@Inject private Set<Loader> loaders;
	@Inject private CosmeticUserService userService;

	@Override
	public void start() {
		loaders.forEach(Loader::load);
		ParticleSelectorMenu.load();
	}

	@Override
	public void stop() {
		userService.stop();
	}

}
