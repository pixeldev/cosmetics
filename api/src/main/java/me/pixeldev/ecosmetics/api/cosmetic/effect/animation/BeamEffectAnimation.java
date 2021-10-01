package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

import static me.pixeldev.ecosmetics.api.util.VectorUtils.cos;
import static me.pixeldev.ecosmetics.api.util.VectorUtils.sin;

public class BeamEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final int POINTS = 16;
	private static final int RADIUS = 1;
	private static final double SLICE = 2 * Math.PI / POINTS;

	private int step;
	private boolean reversed = false;

	public BeamEffectAnimation(WeakReference<Player> ownerReference,
														 CosmeticSpectators spectators, ParticleEffect effect) {
		super(ownerReference, spectators, effect, 1);
	}

	@Override
	protected void runAnimation(Player player) {
		Location location = player.getLocation();

		for (int i = 0; i < POINTS; i++) {
			double angle = SLICE * i;
			Location target = location.clone().add(
				RADIUS * cos(angle),
				(this.step / 10D) - 0.5,
				RADIUS * sin(angle)
			);

			spectators.consumeAsPlayers(spectator -> effect.display(target, spectator));
		}

		step += reversed ? -1 : 1;
		if (step >= 30) {
			reversed = true;
		} else if (step <= 0) {
			reversed = false;
		}
	}

}
