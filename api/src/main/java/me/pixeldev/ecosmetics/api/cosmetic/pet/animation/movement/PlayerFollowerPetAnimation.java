package me.pixeldev.ecosmetics.api.cosmetic.pet.animation.movement;

import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.AbstractCosmeticPetAnimation;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;

public final class PlayerFollowerPetAnimation
	extends AbstractCosmeticPetAnimation {

	private final WeakReference<Player> ownerReference;

	public PlayerFollowerPetAnimation(WeakReference<Player> ownerReference,
																		Location baseLocation) {
		super(baseLocation);
		this.ownerReference = ownerReference;
	}

	@Override
	public void run() {
		Player player = ownerReference.get();

		if (player == null) {
			return;
		}

		Location ownerLocation = player.getLocation();

		double z = ownerLocation.getZ() - baseLocation.getZ();
		double x = ownerLocation.getX() - baseLocation.getX();

		if (z == 0 && x == 0) { //to avoid NaN values if the player joined looking up.
			return;
		}

		double y = ownerLocation.getY() + 1.5;
		double tan = Math.atan2(z, x);
		double degrees = Math.toDegrees(tan);
		float angle = (float) degrees - 90F;

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
