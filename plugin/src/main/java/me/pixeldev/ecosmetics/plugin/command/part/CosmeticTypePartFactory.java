package me.pixeldev.ecosmetics.plugin.command.part;

import me.fixeddev.commandflow.CommandContext;
import me.fixeddev.commandflow.annotated.part.PartFactory;
import me.fixeddev.commandflow.exception.ArgumentParseException;
import me.fixeddev.commandflow.part.ArgumentPart;
import me.fixeddev.commandflow.part.CommandPart;
import me.fixeddev.commandflow.stack.ArgumentStack;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticTypeRegistry;

import net.kyori.text.TranslatableComponent;

import javax.inject.Inject;
import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.util.*;

public class CosmeticTypePartFactory implements PartFactory {

	@Inject private CosmeticTypeRegistry cosmeticTypeRegistry;

	@Override
	public CommandPart createPart(String name, List<? extends Annotation> list) {
		return new ArgumentPart() {
			@Override
			public List<CosmeticType> parseValue(CommandContext commandContext,
																							 ArgumentStack argumentStack)
				throws ArgumentParseException {
				String current = argumentStack.current();
				String next = argumentStack.hasNext() ? argumentStack.next() : "";
				CosmeticCategory category = CosmeticCategory.getByName(current);

				if (category == null) {
					throw new ArgumentParseException(TranslatableComponent
						.of("part.cosmetic-type.invalid-category")
					);
				}

				CosmeticType cosmeticType = cosmeticTypeRegistry.getCosmeticType(
					category, next.toLowerCase()
				);

				if (cosmeticType == null) {
					throw new ArgumentParseException(TranslatableComponent
						.of("part.cosmetic-type.invalid-type")
					);
				}

				return Collections.singletonList(cosmeticType);
			}

			@Override
			public List<String> getSuggestions(CommandContext commandContext, ArgumentStack stack) {
				String current = stack.current();
				String next = stack.hasNext() ? stack.next() : "";
				CosmeticCategory category = CosmeticCategory.getByName(current);

				if (category == null) {
					return Collections.emptyList();
				}

				Collection<CosmeticType> cosmeticTypes = cosmeticTypeRegistry
					.getAllTypesFor(category)
					.values();

				List<String> suggestions = new ArrayList<>();

				for (CosmeticType cosmeticType : cosmeticTypes) {
					String cosmeticKey = cosmeticType.getIdentifier();

					if (cosmeticKey.startsWith(next)) {
						suggestions.add(cosmeticKey);
					}
				}

				return suggestions;
			}

			@Override
			public Type getType() {
				return CosmeticType.class;
			}

			@Override
			public String getName() {
				return name;
			}
		};
	}
}