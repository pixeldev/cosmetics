package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

import static me.pixeldev.ecosmetics.api.util.VectorUtils.cos;
import static me.pixeldev.ecosmetics.api.util.VectorUtils.sin;

public class OrbitEffectAnimation extends AbstractEffectCosmeticAnimation {

	private static final int ORBITS = 3;
	private static final int STEPS = 120;
	private static final double RADIUS = 1;

	private double step;

	public OrbitEffectAnimation(WeakReference<Player> ownerReference, CosmeticSpectators spectators,
															ParticleEffect effect) {
		super(ownerReference, spectators, effect, 1);
	}

	@Override
	protected void runAnimation(Player player) {
		Location playerLocation = player.getLocation();

		for (int i = 0; i < ORBITS; i++) {
			double angle = (this.step / (double) STEPS) * (Math.PI * 2) + (((Math.PI * 2) / ORBITS) * i);
			double dx = -cos(angle) * RADIUS;
			double dz = -sin(angle) * RADIUS;
			spectators.consumeAsPlayers(spectator -> effect.display(
				playerLocation.clone().add(dx, 0.1, dz), 0, 0, 0,
				0F, 1, null, spectator
			));
		}

		step = (step + 1) % STEPS;
	}

}
