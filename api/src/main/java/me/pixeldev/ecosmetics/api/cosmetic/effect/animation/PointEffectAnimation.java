package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

public class PointEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final double OFFSET = 2.25;

	public PointEffectAnimation(WeakReference<Player> ownerReference,
															CosmeticSpectators spectators,
															ParticleEffect effect) {
		super(ownerReference, spectators, effect, 2);
	}

	@Override
	protected void runAnimation(Player player) {
		Location location = player.getLocation().add(0, OFFSET, 0);
		spectators.consumeAsPlayers(spectator -> effect.display(
			location, 0, 0, 0,
			0, 1, null, spectator
		));
	}

}
