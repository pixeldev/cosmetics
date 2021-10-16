package me.pixeldev.ecosmetics.api.item;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Set;

/**
 * Parser to create an {@link ItemStack} with all available properties.
 */
public interface ItemParser {

	ItemStack parseUsingColors(Material baseMaterial, String color);

	/**
	 * Creates an {@link ItemStack} from the given {@link YamlConfigurationSection}.
	 * @param section Section to get all the properties of the {@link ItemStack}.
	 * @return A not null instance of the parsed {@link ItemStack}
	 *
	 * NOTE: It will return an instance with type {@link Material#STONE} if an
	 * error occurs or cannot parse from that section.
	 */
	ItemStack parseFromSection(YamlConfigurationSection section);

	/**
	 * Creates a {@link Set} with all possibles {@link ItemStack} that can
	 * be parsed using {@link ItemParser#parseFromSection(YamlConfigurationSection)}
	 * @param section Section to parse.
	 * @return A not null instance of the {@link Set} containing all the parsed items
	 * or empty if nothing was parsed.
	 */
	Set<ItemStack> parseAllFromSection(YamlConfigurationSection section);

}
