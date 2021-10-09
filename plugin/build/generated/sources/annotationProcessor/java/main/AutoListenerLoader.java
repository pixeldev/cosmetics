package me.pixeldev.alya.bukkit.loader;

public class AutoListenerLoader implements me.pixeldev.alya.api.loader.Loader {

  @javax.inject.Inject private me.pixeldev.ecosmetics.plugin.listener.cosmetic.CosmeticAnimationListener cosmeticanimationlistener;
  @javax.inject.Inject private me.pixeldev.ecosmetics.plugin.listener.vanilla.PlayerChangedWorldListener playerchangedworldlistener;
  @javax.inject.Inject private me.pixeldev.ecosmetics.plugin.listener.vanilla.PlayerJoinListener playerjoinlistener;
  @javax.inject.Inject private me.pixeldev.ecosmetics.plugin.listener.vanilla.PlayerLeaveListener playerleavelistener;

  @javax.inject.Inject private org.bukkit.plugin.Plugin plugin; 

  @Override
  public void load() {
    org.bukkit.plugin.PluginManager pluginManager = org.bukkit.Bukkit.getPluginManager();
    pluginManager.registerEvents(new team.unnamed.gui.core.GUIListeners(), plugin);
    pluginManager.registerEvents(cosmeticanimationlistener, plugin);
    pluginManager.registerEvents(playerchangedworldlistener, plugin);
    pluginManager.registerEvents(playerjoinlistener, plugin);
    pluginManager.registerEvents(playerleavelistener, plugin);
  }
}
