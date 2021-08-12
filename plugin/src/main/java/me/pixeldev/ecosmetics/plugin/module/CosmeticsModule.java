package me.pixeldev.ecosmetics.plugin.module;

import me.pixeldev.ecosmetics.api.cosmetic.permission.CosmeticPermissionFormatter;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticTypeRegistry;
import me.pixeldev.ecosmetics.api.item.ItemParser;
import me.pixeldev.ecosmetics.plugin.cosmetic.permission.SimpleCosmeticPermissionFormatter;
import me.pixeldev.ecosmetics.plugin.cosmetic.type.SimpleCosmeticTypeRegistry;
import me.pixeldev.ecosmetics.plugin.item.SimpleItemParser;

import me.yushust.inject.AbstractModule;

public final class CosmeticsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ItemParser.class).to(SimpleItemParser.class).singleton();
		bind(CosmeticPermissionFormatter.class).to(SimpleCosmeticPermissionFormatter.class).singleton();
		bind(CosmeticTypeRegistry.class).to(SimpleCosmeticTypeRegistry.class).singleton();
	}

}