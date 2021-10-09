package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

public class OverheadEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final double HEAD_OFFSET = 2.25;
	private static final float SPREAD_X = 0.4F;
	private static final float SPREAD_Y = 0.1F;
	private static final float SPREAD_Z = 0.4F;

	public OverheadEffectAnimation(WeakReference<Player> ownerReference,
																 CosmeticSpectators spectators,
																 ParticleEffect effect) {
		super(ownerReference, spectators, effect, 2);
	}

	@Override
	protected void runAnimation(Player player) {
		Location location = player.getLocation().add(0, HEAD_OFFSET, 0);

		spectators.consumeAsPlayers(spectator -> effect.display(
			location, SPREAD_X, SPREAD_Y, SPREAD_Z,
			0, 1, null, spectator
		));
	}

}
