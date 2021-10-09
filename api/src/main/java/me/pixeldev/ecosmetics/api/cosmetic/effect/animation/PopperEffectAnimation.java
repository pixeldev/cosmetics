package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

import static me.pixeldev.ecosmetics.api.util.VectorUtils.cos;
import static me.pixeldev.ecosmetics.api.util.VectorUtils.sin;

public class PopperEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final double RADIUS = 1;
	private static final double GROW = 0.08;
	private static final double RADIALS = Math.PI / 16;
	private static final int HELICES = 2;
	private static final int STEPS = 32;
	private static final int AMOUNT = 10;
	private static final float SPREAD = 0.5F;
	private static final float SPEED = 0.03F;
	private static final double OFFSET = 2;

	private double step;

	public PopperEffectAnimation(WeakReference<Player> ownerReference,
															 CosmeticSpectators spectators,
															 ParticleEffect effect) {
		super(ownerReference, spectators, effect, 0);
	}

	@Override
	protected void runAnimation(Player player) {
		Location playerLocation = player.getLocation();
		double radius = RADIUS * (1 - step / STEPS);
		for (int i = 0; i < HELICES; i++) {
			double angle = step * RADIALS + (2 * Math.PI * i / HELICES);
			Location location = playerLocation.clone().add(
				cos(angle) * radius,
				step * GROW,
				sin(angle) * radius
			);

			spectators.consumeAsPlayers(spectator -> effect.display(
				location, 0, 0, 0,
				0, 1, null, spectator
			));
		}

		if (step == STEPS - 1) {
			Location location = playerLocation.clone().add(0, OFFSET, 0);
			for (int i = 0; i < AMOUNT; i++) {
				spectators.consumeAsPlayers(spectator -> effect.display(
					location, SPREAD, SPREAD, SPREAD,
					SPEED, 1, null, spectator
				));
			}
		}

		step = (step + 1) % STEPS;
	}

}
