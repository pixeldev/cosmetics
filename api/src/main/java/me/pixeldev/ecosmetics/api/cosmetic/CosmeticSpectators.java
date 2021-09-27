package me.pixeldev.ecosmetics.api.cosmetic;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public final class CosmeticSpectators {

	private final Set<UUID> spectators = new HashSet<>();

	public boolean addSpectator(Player who) {
		return spectators.add(who.getUniqueId());
	}

	public boolean removeSpectator(Player who) {
		return spectators.remove(who.getUniqueId());
	}

	public boolean isSpectating(Player who) {
		return spectators.contains(who.getUniqueId());
	}

	/**
	 * Iterate and map the current spectators to
	 * parse them as players, then run the action
	 * set with the consumer.
	 *
	 * @param consumer Action to execute with the parsed player.
	 */
	public void consumeAsPlayers(Consumer<Player> consumer) {
		/* using the iterator to remove while iterating */
		Iterator<UUID> iterator = spectators.iterator();

		while (iterator.hasNext()) {
			Player current = Bukkit.getPlayer(iterator.next());

			if (current == null) {
				//player has left server, we need to remove from spectators.
				iterator.remove();
				continue;
			}

			consumer.accept(current);
		}
	}

}
