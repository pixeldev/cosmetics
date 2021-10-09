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

public class WhirlEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final int RAYS = 2;
	private static final int STEPS = 40;
	private static final double OFFSET = 0.1;

	private final float speed;
	private double step;

	public WhirlEffectAnimation(WeakReference<Player> ownerReference,
															CosmeticSpectators spectators,
															ParticleEffect effect) {
		super(ownerReference, spectators, effect, 0);
		speed = getSpeedByEffect(effect);
	}

	@Override
	protected void runAnimation(Player player) {
		Location playerLocation = player.getLocation();

		for (int i = 0; i < RAYS; i++) {
			double angle = step + (Math.PI * 2 * ((double) i / RAYS));
			double dx = cos(angle);
			double dz = sin(angle);
			double offsetAngle = Math.atan2(dz, dx);
			double xAngle = cos(offsetAngle);
			double zAngle = sin(offsetAngle);
			Location location = playerLocation.clone().add(0, OFFSET, 0);

			ParticleBuilder builder = new ParticleBuilder(effect, location)
				.setSpeed(speed)
				.setOffset((float) xAngle, 0, (float) zAngle);

			spectators.consumeAsPlayers(builder::display);
		}

		step = (step + Math.PI * 2 / STEPS) % STEPS;
	}

}
