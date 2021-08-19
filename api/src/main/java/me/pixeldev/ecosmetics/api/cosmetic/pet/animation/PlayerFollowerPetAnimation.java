package me.pixeldev.ecosmetics.api.cosmetic.pet.animation;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.UUID;

public final class PlayerFollowerPetAnimation
	implements CosmeticPetAnimation {

	private final UUID ownerId;
	private Location baseLocation;

	public PlayerFollowerPetAnimation(UUID ownerId, Location baseLocation) {
		this.ownerId = ownerId;
		this.baseLocation = baseLocation;
	}

	@Override
	public void changeBaseLocation(Location location) {
		baseLocation = location;
	}

	@Override
	public void run() {
		Player player = Bukkit.getPlayer(ownerId);
		Location ownerLocation = player.getLocation();

		double y = ownerLocation.getY() + 1.5;
		float angle = (float) Math.toDegrees(Math.atan2(
			ownerLocation.getZ() - baseLocation.getZ(),
			ownerLocation.getX() - baseLocation.getX())
		) - 90F;

		/* we need to assign the view angle to get a correct camera */
		baseLocation.setYaw(angle);
		baseLocation.setPitch(angle);

		double distanceSquared = ownerLocation.distanceSquared(baseLocation);

		if (distanceSquared >= 7) {
			baseLocation.add(ownerLocation.toVector()
				.subtract(baseLocation.toVector())
				.setY(0)
				.normalize()
				.multiply(0.6)
			);
			baseLocation.setY(y);
			baseLocation.setYaw(angle);
			baseLocation.setPitch(angle);
		}

		if (distanceSquared >= 32) {
			baseLocation.setX(ownerLocation.getX());
			baseLocation.setY(y);
			baseLocation.setZ(ownerLocation.getZ());
			baseLocation.setYaw(angle);
			baseLocation.setPitch(angle);
		}
	}

}