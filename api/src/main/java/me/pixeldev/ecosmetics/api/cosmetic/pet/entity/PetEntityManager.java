package me.pixeldev.ecosmetics.api.cosmetic.pet.entity;

import java.util.Optional;
import java.util.UUID;

public interface PetEntityManager {

	Optional<UUID> getOwnerByEntity(int entityId);

	void addPetEntity(UUID ownerId, int entityId);

	void removePetEntity(int entityId);

}