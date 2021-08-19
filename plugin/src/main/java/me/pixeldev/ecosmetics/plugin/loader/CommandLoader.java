package me.pixeldev.ecosmetics.plugin.loader;

import me.fixeddev.commandflow.CommandManager;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder;
import me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl;
import me.fixeddev.commandflow.annotated.builder.AnnotatedCommandBuilderImpl;
import me.fixeddev.commandflow.annotated.part.PartInjector;
import me.fixeddev.commandflow.annotated.part.SimplePartInjector;
import me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule;
import me.fixeddev.commandflow.annotated.part.defaults.factory.EnumPartFactory;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;
import me.fixeddev.commandflow.translator.DefaultTranslator;

import me.pixeldev.alya.api.loader.Loader;
import me.pixeldev.alya.bukkit.command.CommonTranslatorProvider;
import me.pixeldev.alya.bukkit.command.CommonUsageBuilder;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.plugin.command.CosmeticCommands;
import me.pixeldev.ecosmetics.plugin.command.part.CosmeticTypePartFactory;

import me.yushust.inject.Injector;

import javax.inject.Inject;

public final class CommandLoader implements Loader {

	@Inject private Injector injector;
	@Inject private CosmeticTypePartFactory cosmeticTypePartFactory;
	@Inject private CommonUsageBuilder usageBuilder;
	@Inject private CommonTranslatorProvider translatorProvider;
	@Inject private CosmeticCommands cosmeticCommands;

	@Override
	public void load() {
		CommandManager commandManager = new BukkitCommandManager("electron-cosmetics");
		commandManager.setTranslator(new DefaultTranslator(translatorProvider));
		commandManager.setUsageBuilder(usageBuilder);

		PartInjector partInjector = new SimplePartInjector();
		partInjector.install(new BukkitModule());
		partInjector.install(new DefaultsModule());
		partInjector.bindFactory(CosmeticCategory.class, new EnumPartFactory(CosmeticCategory.class));
		partInjector.bindFactory(CosmeticType.class, cosmeticTypePartFactory);

		AnnotatedCommandTreeBuilder treeBuilder = new AnnotatedCommandTreeBuilderImpl(
			new AnnotatedCommandBuilderImpl(partInjector),
			(clazz, parent) -> injector.getInstance(clazz)
		);

		commandManager.registerCommands(treeBuilder.fromClass(cosmeticCommands));
	}

}