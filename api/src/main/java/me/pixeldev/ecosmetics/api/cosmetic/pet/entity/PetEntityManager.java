package me.pixeldev.ecosmetics.api.cosmetic.pet.entity;

import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface PetEntityManager {

	@Nullable UUID getOwnerByEntity(int entityId);

	void addPetEntity(UUID ownerId, int entityId);

	void removePetEntity(int entityId);

}
