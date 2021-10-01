package me.pixeldev.ecosmetics.api.cosmetic;

import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.*;
import java.util.Map.Entry;
import java.util.function.Consumer;

public final class CosmeticSpectators {

	private final Map<UUID, WeakReference<Player>> spectators = new HashMap<>();

	public boolean addSpectator(Player who) {
		return spectators.put(who.getUniqueId(), new WeakReference<>(who)) == null;
	}

	public boolean removeSpectator(Player who) {
		return spectators.remove(who.getUniqueId()) != null;
	}

	public boolean isSpectating(Player who) {
		return spectators.containsKey(who.getUniqueId());
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
		Iterator<Map.Entry<UUID, WeakReference<Player>>> iterator
			= spectators.entrySet().iterator();;

		while (iterator.hasNext()) {
			Entry<UUID, WeakReference<Player>> entry = iterator.next();
			UUID id = entry.getKey();
			Player player = entry.getValue().get();

			if (player == null) {
				//player has left server, we need to remove from spectators.
				spectators.remove(id);
				continue;
			}

			consumer.accept(player);
		}
	}

}
