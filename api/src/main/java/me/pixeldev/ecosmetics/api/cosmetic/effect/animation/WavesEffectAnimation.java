package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

public class WavesEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final double RADIUS = 1.1;
	private static final int CURVES_PER_WAVE = 4;
	private static final double MAX_HEIGHT_DIFF = 0.5;
	private static final double HEIGHT_DIFF_STEP = 0.05;

	private boolean direction;
	private double factor = MAX_HEIGHT_DIFF;

	public WavesEffectAnimation(WeakReference<Player> ownerReference,
															CosmeticSpectators spectators, ParticleEffect effect) {
		super(ownerReference, spectators, effect, 2);
	}

	@Override
	public void runAnimation(Player player) {
		if (direction) {
			if (factor > MAX_HEIGHT_DIFF) {
				factor += HEIGHT_DIFF_STEP;
			} else {
				direction = false;
			}
		} else {
			if (factor > -MAX_HEIGHT_DIFF) {
				factor -= HEIGHT_DIFF_STEP;
			} else {
				direction = true;
			}
		}

		Vector vector = new Vector(0, 0, 0);

		for (double angle = 0;
				 angle <= 2 * Math.PI;
				 angle += 2 * Math.PI / 45) {
			vector.setX(Math.cos(angle) * RADIUS);
			vector.setZ(Math.sin(angle) * RADIUS);
			vector.setY(0.5 + Math.sin(angle * CURVES_PER_WAVE) * factor);

			Location firstLocation = player.getLocation().add(vector);
			Location secondLocation = player.getLocation().add(vector).add(0, 1, 0);

			spectators.consumeAsPlayers(spectator -> {
				effect.display(firstLocation);
				effect.display(secondLocation);
			});
		}
	}

}
