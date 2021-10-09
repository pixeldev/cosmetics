package me.pixeldev.ecosmetics.v1_8_R3.track;

import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;

import net.minecraft.server.v1_8_R3.EntityTrackerEntry;
import org.bukkit.Bukkit;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class EntityTrackerManager<T extends Cosmetic<?>> {

	private final Map<UUID, EntityTrackerEntry> entries;

	public EntityTrackerManager() {
		entries = new HashMap<>();
	}

	public void bindEntry(T cosmetic, EntityTrackerEntry entry) {
		EntityTrackerAccessor.addEntry(
			Bukkit.getWorld(cosmetic.getWorldName()),
			entry
		);
		entries.put(cosmetic.getOwnerId(), entry);
	}

	public void unbindEntry(T cosmetic) {
		EntityTrackerEntry entry = entries.remove(cosmetic.getOwnerId());

		if (entry == null) {
			return;
		}

		EntityTrackerAccessor.removeEntry(
			Bukkit.getWorld(cosmetic.getWorldName()),
			entry
		);
	}

}
