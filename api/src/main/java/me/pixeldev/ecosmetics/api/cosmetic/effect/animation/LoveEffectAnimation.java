package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

public class LoveEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	public LoveEffectAnimation(WeakReference<Player> ownerReference,
														 CosmeticSpectators spectators) {
		super(ownerReference, spectators, ParticleEffect.HEART, 1);
	}

	@Override
	protected void runAnimation(Player player) {
		Location location = player.getLocation().add(0, 1, 0);

		spectators.consumeAsPlayers(spectator -> effect.display(
			location, 0.5F, 0.5F, 0.5F,
			0F, 2, null, spectator
		));
	}

}
