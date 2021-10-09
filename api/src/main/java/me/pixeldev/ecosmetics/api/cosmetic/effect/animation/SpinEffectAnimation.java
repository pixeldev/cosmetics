package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

import static me.pixeldev.ecosmetics.api.util.VectorUtils.cos;
import static me.pixeldev.ecosmetics.api.util.VectorUtils.sin;

public class SpinEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final int STEPS = 30;
	private static final double ANGLE = (Math.PI * 2 / STEPS);
	private static final double RADIUS = 0.5;
	private static final double OFFSET = 2.25;

	private double step;

	public SpinEffectAnimation(WeakReference<Player> ownerReference,
														 CosmeticSpectators spectators,
														 ParticleEffect effect) {
		super(ownerReference, spectators, effect, 1);
	}

	@Override
	protected void runAnimation(Player player) {
		double slice = ANGLE * step;

		double dx = RADIUS * cos(slice);
		double dz = RADIUS * sin(slice);

		Location location = player.getLocation().add(dx, OFFSET, dz);

		spectators.consumeAsPlayers(spectator -> effect.display(
			location, 0, 0, 0,
			0, 1, null, spectator
		));

		step = (step + 1) % STEPS;
	}

}
