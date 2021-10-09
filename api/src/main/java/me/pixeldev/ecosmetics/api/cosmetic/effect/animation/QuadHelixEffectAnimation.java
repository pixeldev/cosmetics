package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

import static me.pixeldev.ecosmetics.api.util.VectorUtils.cos;
import static me.pixeldev.ecosmetics.api.util.VectorUtils.sin;

public class QuadHelixEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final int ORBS = 4;
	private static final int MAX_STEP_X = 80;
	private static final int MAX_STEP_Y = 60;

	private int stepX;
	private int stepY;
	private boolean reverse;

	public QuadHelixEffectAnimation(WeakReference<Player> ownerReference,
																	CosmeticSpectators spectators,
																	ParticleEffect effect) {
		super(ownerReference, spectators, effect, 0);
	}

	@Override
	protected void runAnimation(Player player) {
		if ((stepX++) > MAX_STEP_X) {
			stepX = 0;
		}

		if (reverse) {
			if ((stepY++) > MAX_STEP_Y) {
				reverse = false;
			}
		} else {
			if ((stepY--) < -MAX_STEP_Y) {
				reverse = true;
			}
		}

		Location playerLocation = player.getLocation();

		for (int i = 0; i < ORBS; i++) {
			double multiplier = ((MAX_STEP_Y - Math.abs(this.stepY)) / (double) MAX_STEP_Y);
			double angle = (this.stepX / (double) MAX_STEP_X) * (Math.PI * 2) + (((Math.PI * 2) / ORBS) * i);
			double dx = -cos(angle) * multiplier;
			double dy = (this.stepY / (double) MAX_STEP_Y) + 1;
			double dz = -sin(angle) * multiplier;
			Location location = playerLocation.clone().add(dx, dy, dz);

			spectators.consumeAsPlayers(spectator -> effect.display(
				location, 0, 0, 0,
				0, 1, null, spectator
			));
		}
	}

}
