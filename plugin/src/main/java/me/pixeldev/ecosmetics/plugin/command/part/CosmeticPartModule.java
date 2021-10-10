package me.pixeldev.ecosmetics.plugin.command.part;

import me.fixeddev.commandflow.annotated.part.Key;
import me.fixeddev.commandflow.annotated.part.defaults.factory.EnumPartFactory;
import me.fixeddev.commandflow.bukkit.annotation.Sender;
import me.fixeddev.commandflow.bukkit.factory.BukkitModule;

import me.pixeldev.alya.api.auto.command.AutoCommand;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;
import me.pixeldev.ecosmetics.plugin.command.arg.ReloadType;

import javax.inject.Inject;

@AutoCommand(property = AutoCommand.Property.MODULE)
public class CosmeticPartModule extends BukkitModule {

	@Inject private CosmeticTypePartFactory cosmeticTypePartFactory;
	@Inject private CosmeticUserService userService;

	@Override
	public void configure() {
		bindFactory(CosmeticType.class, cosmeticTypePartFactory);
		bindFactory(CosmeticCategory.class, new EnumPartFactory(CosmeticCategory.class));
		bindFactory(new Key(CosmeticUser.class, Sender.class), new CosmeticUserPartFactory(userService, true));
		bindFactory(CosmeticUser.class, new CosmeticUserPartFactory(userService, false));
		bindFactory(ReloadType.class, new EnumPartFactory(ReloadType.class));
	}

}
