package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

public class FeetEffectAnimation extends AbstractEffectCosmeticAnimation {

	private static final double FEET_OFFSET = -0.25;
	private static final float PARTICLE_SPREAD_X = 0.4F;
	private static final float PARTICLE_SPREAD_Y = 0F;
	private static final float PARTICLE_SPREAD_Z = 0.4F;
	private static final float PARTICLE_SPEED = 0;
	private static final int PARTICLES_PER_TICK = 1;

	public FeetEffectAnimation(WeakReference<Player> ownerReference, CosmeticSpectators spectators,
														 ParticleEffect effect) {
		super(ownerReference, spectators, effect, 2);
	}

	@Override
	protected void runAnimation(Player player) {
		Location playerLocation = player.getLocation();
		for (int i = 0; i < PARTICLES_PER_TICK; i++) {
			spectators.consumeAsPlayers(spectator -> effect.display(
				playerLocation.clone().add(0, FEET_OFFSET, 0),
				PARTICLE_SPREAD_X, PARTICLE_SPREAD_Y, PARTICLE_SPREAD_Z,
				PARTICLE_SPEED, PARTICLES_PER_TICK, null, spectator
			));
		}
	}

}
