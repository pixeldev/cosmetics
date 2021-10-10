package me.pixeldev.ecosmetics.plugin.module;

import me.pixeldev.alya.bukkit.menu.GUICreator;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticHandler;
import me.pixeldev.ecosmetics.api.cosmetic.permission.CosmeticPermissionFormatter;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticTypeRegistry;
import me.pixeldev.ecosmetics.api.cosmetic.type.creator.CosmeticTypeCreator;
import me.pixeldev.ecosmetics.api.item.ItemParser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;
import me.pixeldev.ecosmetics.plugin.cosmetic.SimpleCosmeticHandler;
import me.pixeldev.ecosmetics.plugin.cosmetic.permission.SimpleCosmeticPermissionFormatter;
import me.pixeldev.ecosmetics.plugin.cosmetic.type.creator.SimpleCosmeticTypeCreator;
import me.pixeldev.ecosmetics.plugin.cosmetic.type.SimpleCosmeticTypeRegistry;
import me.pixeldev.ecosmetics.plugin.item.SimpleItemParser;
import me.pixeldev.ecosmetics.plugin.menu.CosmeticMainMenu;
import me.pixeldev.ecosmetics.plugin.user.SimpleCosmeticUserService;

import me.yushust.inject.AbstractModule;

public final class CosmeticsModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(ItemParser.class).to(SimpleItemParser.class).singleton();
		bind(CosmeticTypeCreator.class).to(SimpleCosmeticTypeCreator.class).singleton();
		bind(CosmeticPermissionFormatter.class).to(SimpleCosmeticPermissionFormatter.class).singleton();
		bind(CosmeticTypeRegistry.class).to(SimpleCosmeticTypeRegistry.class).singleton();
		bind(CosmeticUserService.class).to(SimpleCosmeticUserService.class).singleton();
		bind(CosmeticHandler.class).to(SimpleCosmeticHandler.class).singleton();

		bind(GUICreator.class).named("main").to(CosmeticMainMenu.class).singleton();
	}

}
