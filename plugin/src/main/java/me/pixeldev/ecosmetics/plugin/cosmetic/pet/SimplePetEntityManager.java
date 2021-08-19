package me.pixeldev.ecosmetics.plugin.cosmetic.pet;

import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityManager;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class SimplePetEntityManager implements PetEntityManager {

	private final Map<Integer, UUID> headByEntityIds = new HashMap<>();

	@Override
	public Optional<UUID> getOwnerByEntity(int entityId) {
		return Optional.ofNullable(headByEntityIds.get(entityId));
	}

	@Override
	public void addPetEntity(UUID ownerId, int entityId) {
		headByEntityIds.put(entityId, ownerId);
	}

	@Override
	public void removePetEntity(int entityId) {
		headByEntityIds.remove(entityId);
	}

}