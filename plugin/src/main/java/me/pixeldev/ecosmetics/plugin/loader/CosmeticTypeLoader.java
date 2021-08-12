package me.pixeldev.ecosmetics.plugin.loader;

import me.pixeldev.alya.api.loader.Loader;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticTypeRegistry;

import javax.inject.Inject;

public final class CosmeticTypeLoader implements Loader {

	@Inject private CosmeticTypeRegistry typeRegistry;

	@Override
	public void load() {
		typeRegistry.registerFromAllKnownSections();
	}

}