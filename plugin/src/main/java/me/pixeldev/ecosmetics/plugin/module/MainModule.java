package me.pixeldev.ecosmetics.plugin.module;

import me.pixeldev.alya.api.yaml.YamlFileConfiguration;
import me.pixeldev.alya.bukkit.config.BukkitYamlFileConfiguration;
import me.pixeldev.ecosmetics.plugin.CosmeticsPlugin;
import me.pixeldev.ecosmetics.plugin.language.MessageConfigurationModule;

import me.yushust.inject.AbstractModule;

import org.bukkit.plugin.Plugin;

public final class MainModule extends AbstractModule {

	private final CosmeticsPlugin plugin;

	public MainModule(CosmeticsPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	protected void configure() {
		bind(CosmeticsPlugin.class).toInstance(plugin);
		bind(Plugin.class).to(CosmeticsPlugin.class);

		bind(YamlFileConfiguration.class).toInstance(new BukkitYamlFileConfiguration(
			plugin, "config"
		));

		install(
			new CosmeticsModule(), new StructureModule(),
			new MessageConfigurationModule()
		);
	}

}