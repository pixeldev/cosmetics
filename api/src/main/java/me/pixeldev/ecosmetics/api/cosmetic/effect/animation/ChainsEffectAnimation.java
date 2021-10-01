package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

public class ChainsEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final int PARTICLE_AMOUNT = 8;

	public ChainsEffectAnimation(WeakReference<Player> ownerReference,
															 CosmeticSpectators spectators, ParticleEffect effect) {
		super(ownerReference, spectators, effect, 2);
	}

	@Override
	protected void runAnimation(Player player) {
		for (double n = -0.2; n < 0.6; n += 0.8 / PARTICLE_AMOUNT) {
			Location first = player.getLocation().add(1 - n, n - 1.1, 1 - n);
			Location second = player.getLocation().add(1 - n, n - 1.1, -1 + n);
			Location third = player.getLocation().add(-1 + n, n - 1.1, 1 - n);
			Location fourth = player.getLocation().add(-1 + n, n - 1.1, -1 + n);

			spectators.consumeAsPlayers(spectator -> {
				effect.display(first, spectator);
				effect.display(second, spectator);
				effect.display(third, spectator);
				effect.display(fourth, spectator);
			});
		}
	}

}
