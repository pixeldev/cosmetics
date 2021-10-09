package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

import static me.pixeldev.ecosmetics.api.util.MathUtils.randomDouble;
import static me.pixeldev.ecosmetics.api.util.VectorUtils.cos;
import static me.pixeldev.ecosmetics.api.util.VectorUtils.sin;

public class SphereEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final int DENSITY = 15;
	private static final double RADIUS = 1.5;

	public SphereEffectAnimation(WeakReference<Player> ownerReference,
															 CosmeticSpectators spectators,
															 ParticleEffect effect) {
		super(ownerReference, spectators, effect, 4);
	}

	@Override
	protected void runAnimation(Player player) {
		Location playerLocation = player.getLocation();

		for (int i = 0; i < DENSITY; i++) {
			double u = randomDouble();
			double v = randomDouble();
			double theta = 2 * Math.PI * u;
			double phi = Math.acos(2 * v - 1);
			double dx = RADIUS * sin(phi) * cos(theta);
			double dy = RADIUS * sin(phi) * sin(theta) + 1;
			double dz = RADIUS * cos(phi);

			Location location = playerLocation.clone().add(dx, dy, dz);
			spectators.consumeAsPlayers(spectator -> effect.display(
				location, 0, 0, 0,
				0, 1, null, spectator
			));
		}
	}

}
