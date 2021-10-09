package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleBuilder;
import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

import static me.pixeldev.ecosmetics.api.util.EffectUtils.getSpeedByEffect;
import static me.pixeldev.ecosmetics.api.util.VectorUtils.cos;
import static me.pixeldev.ecosmetics.api.util.VectorUtils.sin;

public class PulseEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final int POINTS = 50;
	private static final double RADIUS = 0.5;
	private static final double OFFSET = 0.1;
	private static final int STEPS = 15;

	private final float speed;
	private double step;

	public PulseEffectAnimation(WeakReference<Player> ownerReference,
															CosmeticSpectators spectators,
															ParticleEffect effect) {
		super(ownerReference, spectators, effect, 0);
		this.speed = getSpeedByEffect(effect);
	}

	@Override
	protected void runAnimation(Player player) {
		if (step == 0) {
			for (int i = 0; i < POINTS; i++) {
				double slice = Math.PI * 2 * ((double) i / POINTS);
				double dx = cos(slice) * RADIUS;
				double dz = sin(slice) * RADIUS;
				double angle = Math.atan2(dz, dx);
				double xAng = cos(angle);
				double zAng = sin(angle);
				Location location = player.getLocation().clone().add(dx, OFFSET, dz);

				ParticleBuilder builder = new ParticleBuilder(effect, location)
					.setSpeed(speed)
					.setOffset((float) xAng, 0, (float) zAng);

				spectators.consumeAsPlayers(builder::display);
			}
		}

		step = (step + 1) % STEPS;
	}

}
