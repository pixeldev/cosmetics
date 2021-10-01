package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

import static me.pixeldev.ecosmetics.api.util.VectorUtils.cos;
import static me.pixeldev.ecosmetics.api.util.VectorUtils.sin;

public class HaloEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final double RADIUS = 0.65;
	private static final int POINTS = 16;
	private static final double PLAYER_OFFSET = 2.25;
	private static final double SLICE = 2 * Math.PI / POINTS;

	public HaloEffectAnimation(WeakReference<Player> ownerReference, CosmeticSpectators spectators,
														 ParticleEffect effect) {
		super(ownerReference, spectators, effect, 5);
	}

	@Override
	protected void runAnimation(Player player) {
		Location location = player.getLocation();

		for (int i = 0; i < POINTS; i++) {
			double angle = SLICE * i;
			Location target = location.clone().add(
				RADIUS * cos(angle),
				PLAYER_OFFSET,
				RADIUS * sin(angle)
			);

			spectators.consumeAsPlayers(spectator -> effect.display(target, spectator));
		}
	}

}
