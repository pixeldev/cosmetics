package me.pixeldev.ecosmetics.api.cosmetic.pet;

import me.pixeldev.ecosmetics.api.cosmetic.AbstractCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.PlayerFollowerPetAnimation;
import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.movement.CosmeticPetMovementAnimation;
import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.movement.DefaultMovementAnimation;
import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle.CosmeticPetParticleAnimation;
import me.pixeldev.ecosmetics.api.cosmetic.type.PetCosmeticType;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import java.util.UUID;
import java.util.function.Consumer;

public class PetCosmetic extends AbstractCosmetic<PetCosmeticType>
	implements Runnable {

	private final Spectators spectators;

	// used to assign the corresponding entity id.
	private int entityId;

	private Location actualLocation;

	private final PlayerFollowerPetAnimation followerPetAnimation;
	private final CosmeticPetParticleAnimation particleAnimation;
	private final CosmeticPetMovementAnimation movementAnimation;

	public PetCosmetic(Player owner, PetCosmeticType type) {
		super(owner.getUniqueId(), type);
		spectators = new Spectators();
		actualLocation = owner.getLocation().add(0, 1.5, 0);
		entityId = -1;

		followerPetAnimation = new PlayerFollowerPetAnimation(getOwner(), actualLocation);
		particleAnimation = CosmeticPetParticleAnimation.of(actualLocation, type);
		movementAnimation = new DefaultMovementAnimation(actualLocation);
	}

	@Override
	public CosmeticCategory getCategory() {
		return CosmeticCategory.MINIATURES;
	}

	public Spectators getSpectators() {
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

	public void changeBaseLocation(Location location) {
		actualLocation = location;
		followerPetAnimation.changeBaseLocation(location);
		particleAnimation.changeBaseLocation(location);
		movementAnimation.changeBaseLocation(location);
	}

	public CosmeticPetParticleAnimation getParticleAnimation() {
		return particleAnimation;
	}

	public CosmeticPetMovementAnimation getMovementAnimation() {
		return movementAnimation;
	}

	public PlayerFollowerPetAnimation getFollowerPetAnimation() {
		return followerPetAnimation;
	}

	@Override
	public void run() {
		followerPetAnimation.run();
		particleAnimation.run();
		movementAnimation.run();
	}

	/**
	 * Represents the data of the players who
	 * are spectating the current {@link PetCosmetic}.
	 */
	public static class Spectators {

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

		public Set<UUID> getSpectators() {
			return spectators;
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

}