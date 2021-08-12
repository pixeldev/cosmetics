package me.pixeldev.ecosmetics.plugin.module;

import me.pixeldev.alya.api.loader.Loader;
import me.pixeldev.ecosmetics.plugin.loader.CosmeticTypeLoader;

import me.yushust.inject.AbstractModule;

public final class StructureModule extends AbstractModule {

	@Override
	protected void configure() {
		multibind(Loader.class).asSet()
			.to(CosmeticTypeLoader.class);
	}

}