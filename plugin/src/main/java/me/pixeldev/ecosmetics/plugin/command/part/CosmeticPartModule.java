package me.pixeldev.ecosmetics.plugin.command.part;

import me.fixeddev.commandflow.annotated.part.defaults.factory.EnumPartFactory;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;

import me.pixeldev.alya.api.auto.command.AutoCommand;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;

import javax.inject.Inject;

@AutoCommand(property = AutoCommand.Property.MODULE)
public class CosmeticPartModule extends BukkitModule {

	@Inject private CosmeticTypePartFactory cosmeticTypePartFactory;

	@Override
	public void configure() {
		bindFactory(CosmeticType.class, cosmeticTypePartFactory);
		bindFactory(CosmeticCategory.class, new EnumPartFactory(CosmeticCategory.class));
	}

}
