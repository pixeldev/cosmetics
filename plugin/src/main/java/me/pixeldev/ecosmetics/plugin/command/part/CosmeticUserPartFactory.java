package me.pixeldev.ecosmetics.plugin.command.part;

import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.annotated.part.PartFactory;
import me.fixeddev.commandflow.bukkit.BukkitCommandManager;
import me.fixeddev.commandflow.exception.ArgumentParseException;
import me.fixeddev.commandflow.exception.CommandException;
import me.fixeddev.commandflow.part.ArgumentPart;
import me.fixeddev.commandflow.part.CommandPart;
import me.fixeddev.commandflow.stack.ArgumentStack;

import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import net.kyori.text.TranslatableComponent;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class CosmeticUserPartFactory implements PartFactory {

	private final CosmeticUserService userService;
	private final boolean asSender;

	public CosmeticUserPartFactory(CosmeticUserService userService, boolean asSender) {
		this.userService = userService;
		this.asSender = asSender;
	}

	private CosmeticUser getUser(CommandSender target) throws CommandException {
		if (!(target instanceof Player)) {
			throw new ArgumentParseException(TranslatableComponent.of("sender.only-player"));
		}

		try {
			CosmeticUser user = userService.getUserByPlayer((Player) target);

			if (user == null) {
				throw new ArgumentParseException(TranslatableComponent.of("command.error"));
			}

			return user;
		} catch (Exception e) {
			throw new ArgumentParseException(TranslatableComponent.of("command.error"));
		}
	}

	@Override
	public CommandPart createPart(String name, List<? extends Annotation> list) {
		if (asSender) {
			return new CommandPart() {
				@Override
				public String getName() {
					return name;
				}

				@Override
				public void parse(CommandContext commandContext,
													ArgumentStack argumentStack) throws ArgumentParseException {
					commandContext.setValue(this, getUser(commandContext.getObject(
						CommandSender.class,
						BukkitCommandManager.SENDER_NAMESPACE
					)));
				}
			};
		} else {
			return new ArgumentPart() {
				@Override
				public List<CosmeticUser> parseValue(CommandContext commandContext,
																							 ArgumentStack argumentStack)
					throws ArgumentParseException {

					return Collections.singletonList(getUser(
						Bukkit.getPlayerExact(argumentStack.next())
					));
				}

				@Override
				public List<String> getSuggestions(CommandContext commandContext,
																					 ArgumentStack stack) {
					String last = stack.hasNext() ? stack.next() : "";

					return Bukkit.matchPlayer(last)
						.stream()
						.map(HumanEntity::getName)
						.collect(Collectors.toList());
				}

				@Override
				public Type getType() {
					return CosmeticUser.class;
				}

				@Override
				public String getName() {
					return name;
				}
			};
		}
	}
}
