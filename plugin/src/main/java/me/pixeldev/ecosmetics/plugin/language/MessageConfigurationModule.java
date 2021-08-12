package me.pixeldev.ecosmetics.plugin.language;

import me.pixeldev.alya.bukkit.translation.sender.CommonMessageSender;

import me.yushust.inject.AbstractModule;
import me.yushust.inject.Provides;
import me.yushust.message.config.ConfigurationModule;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import javax.inject.Singleton;

public final class MessageConfigurationModule extends AbstractModule {

	@Provides @Singleton
	public ConfigurationModule createConfigurationModule(CommonMessageSender messageSender) {
		return configurationHandle -> {
			configurationHandle.addInterceptor(
				text -> ChatColor.translateAlternateColorCodes('&', text)
			);

			configurationHandle.specify(CommandSender.class)
				.setLinguist(commandSender -> "es")
				.setMessageSender(messageSender);
		};
	}

}