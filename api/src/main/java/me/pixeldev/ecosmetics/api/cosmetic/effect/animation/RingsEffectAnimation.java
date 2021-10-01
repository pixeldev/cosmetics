package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;
import me.pixeldev.ecosmetics.api.util.VectorUtils;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

public class RingsEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final double INTERVAL = (2 * Math.PI) / 100;

	private float step;

	public RingsEffectAnimation(WeakReference<Player> ownerReference,
															CosmeticSpectators spectators,
															ParticleEffect effect) {
		super(ownerReference, spectators, effect, 1);
	}

	@Override
	protected void runAnimation(Player player) {
		for (int i = 0; i < 2; i++) {
			double toAdd = 0;

			if (i == 1) {
				toAdd = 3.5;
			}

			double angle = step * INTERVAL + toAdd;
			Vector vector = new Vector(
				Math.cos(angle),
				0,
				Math.sin(angle)
			);

			if (i == 0) {
				VectorUtils.rotateAroundAxisZ(vector, 180);
			} else {
				VectorUtils.rotateAroundAxisZ(vector, 90);
			}

			Location location = player.getLocation().add(vector).add(0, 1, 0);
			spectators.consumeAsPlayers(spectator -> effect.display(
				location, 0, 0, 0,
				0, 1,
				null, spectator
			));
		}

		step += 3;
	}

}
