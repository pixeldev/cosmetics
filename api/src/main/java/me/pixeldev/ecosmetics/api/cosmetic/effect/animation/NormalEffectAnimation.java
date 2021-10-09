package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

public class NormalEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	public NormalEffectAnimation(WeakReference<Player> ownerReference,
															 CosmeticSpectators spectators,
															 ParticleEffect effect) {
		super(ownerReference, spectators, effect, 1);
	}

	@Override
	protected void runAnimation(Player player) {
		spectators.consumeAsPlayers(spectator -> effect.display(
			player.getLocation(), 0.5F, 0.5F, 0.5F,
			0F, 1, null, spectator
		));
	}

}
