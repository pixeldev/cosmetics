package me.pixeldev.alya.bukkit.loader;

public class AutoCommandLoader implements me.pixeldev.alya.api.loader.Loader {

  @javax.inject.Inject private me.pixeldev.ecosmetics.plugin.command.CosmeticCommands cosmeticcommands;

  @javax.inject.Inject private org.bukkit.plugin.Plugin plugin;
  @javax.inject.Inject private me.yushust.inject.Injector injector;

  @javax.inject.Inject private me.pixeldev.alya.bukkit.command.CommonTranslatorProvider translatorProvider;
  @javax.inject.Inject private me.pixeldev.alya.bukkit.command.CommonUsageBuilder usageBuilder;

  @Override
  public void load() {
    me.fixeddev.commandflow.CommandManager commandManager = new me.fixeddev.commandflow.bukkit.BukkitCommandManager(plugin.getName());
    commandManager.setTranslator(new me.fixeddev.commandflow.translator.DefaultTranslator(translatorProvider));
    commandManager.setUsageBuilder(usageBuilder);
    me.fixeddev.commandflow.annotated.part.PartInjector partInjector = new me.fixeddev.commandflow.annotated.part.SimplePartInjector();
    partInjector.install(new me.fixeddev.commandflow.annotated.part.defaults.DefaultsModule());
    partInjector.install(new me.fixeddev.commandflow.bukkit.factory.BukkitModule());
    me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilder builder = new me.fixeddev.commandflow.annotated.AnnotatedCommandTreeBuilderImpl(
      new me.fixeddev.commandflow.annotated.builder.AnnotatedCommandBuilderImpl(partInjector),
      (clazz, parent) -> injector.getInstance(clazz)
    );

    commandManager.registerCommands(builder.fromClass(cosmeticcommands));
  }
}
