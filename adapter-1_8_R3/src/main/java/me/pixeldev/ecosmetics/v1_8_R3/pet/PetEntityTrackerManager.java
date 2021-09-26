package me.pixeldev.ecosmetics.v1_8_R3.pet;

import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.v1_8_R3.track.EntityTrackerAccessor;

import net.minecraft.server.v1_8_R3.EntityTrackerEntry;

import javax.inject.Singleton;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Singleton
public final class PetEntityTrackerManager {

	private final Map<UUID, EntityTrackerEntry> entries;

	public PetEntityTrackerManager() {
		entries = new HashMap<>();
	}

	public void bindEntry(PetCosmetic petCosmetic, EntityTrackerEntry entry) {
		EntityTrackerAccessor.addEntry(
			petCosmetic.getActualLocation().getWorld(),
			entry
		);
		entries.put(petCosmetic.getOwnerId(), entry);
	}

	public void unbindEntry(PetCosmetic petCosmetic) {
		EntityTrackerEntry entry = entries.remove(petCosmetic.getOwnerId());

		if (entry == null) {
			return;
		}

		EntityTrackerAccessor.removeEntry(
			petCosmetic.getActualLocation().getWorld(),
			entry
		);
	}

}
