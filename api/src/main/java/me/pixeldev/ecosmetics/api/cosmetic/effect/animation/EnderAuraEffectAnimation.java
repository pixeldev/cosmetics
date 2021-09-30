package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

public class EnderAuraEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	public EnderAuraEffectAnimation(WeakReference<Player> ownerReference,
																	CosmeticSpectators spectators) {
		super(ownerReference, spectators, ParticleEffect.PORTAL, 2);
	}

	@Override
	public void runAnimation(Player player) {
		Location location = player.getLocation().add(0, 1.2, 0);

		spectators.consumeAsPlayers(spectator -> {
			effect.display(
				location,
				0.35F, 0.05F, 0.35F,
				0.1F, 5, null, spectator
			);
			effect.display(
				location,
				0.35F, 0.05F, 0.35F,
				0.1F, 5, null, spectator
			);
		});
	}

}
