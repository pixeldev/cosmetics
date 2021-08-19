package me.pixeldev.ecosmetics.plugin.adapt;

import me.yushust.inject.Module;

import team.unnamed.gui.core.version.ServerVersionProvider;

public class AdapterModuleProvider {

	public static Module get() {
		try {
			Class<?> clazz = Class.forName(
				"me.pixeldev.ecosmetics.v"
					+ ServerVersionProvider.SERVER_VERSION +
					".AdapterModule"
			);

			return (Module) clazz.newInstance();
		} catch (Exception ignored) {
			throw new IllegalStateException(
				"No adaption module for current version: "
					+ ServerVersionProvider.SERVER_VERSION
			);
		}
	}

}