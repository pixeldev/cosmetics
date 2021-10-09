package me.pixeldev.ecosmetics.api.cosmetic.pet;

import me.pixeldev.ecosmetics.api.cosmetic.AbstractCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;
import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.CosmeticPetAnimation;
import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.movement.PlayerFollowerPetAnimation;
import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.movement.DefaultMovementAnimation;
import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle.CosmeticPetParticleAnimation;
import me.pixeldev.ecosmetics.api.cosmetic.type.PetCosmeticType;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;

public class PetCosmetic extends AbstractCosmetic<PetCosmeticType>
	implements Runnable {

	private final CosmeticSpectators spectators;

	// used to assign the corresponding entity id.
	private int entityId;

	private final Location actualLocation;

	private final PlayerFollowerPetAnimation followerPetAnimation;
	private final CosmeticPetParticleAnimation particleAnimation;
	private final CosmeticPetAnimation movementAnimation;

	public PetCosmetic(WeakReference<Player> ownerReference, PetCosmeticType type) {
		super(ownerReference, type);
		spectators = new CosmeticSpectators();
		actualLocation = ownerReference.get().getLocation().add(0, 1.5, 0);
		entityId = -1;

		followerPetAnimation = new PlayerFollowerPetAnimation(ownerReference, actualLocation);
		particleAnimation = CosmeticPetParticleAnimation.of(actualLocation, spectators, type);
		movementAnimation = new DefaultMovementAnimation(actualLocation);
	}

	@Override
	public CosmeticCategory getCategory() {
		return CosmeticCategory.MINIATURES;
	}

	public CosmeticSpectators getSpectators() {
		return spectators;
	}

	public int getEntityId() {
		return entityId;
	}

	public void setEntityId(int entityId) {
		this.entityId = entityId;
	}

	public Location getActualLocation() {
		return actualLocation;
	}

	@Override
	public void run() {
		if (!equipped) {
			return;
		}

		movementAnimation.run();
		followerPetAnimation.run();
		particleAnimation.run();
	}

}
