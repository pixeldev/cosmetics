package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

import static me.pixeldev.ecosmetics.api.util.VectorUtils.cos;
import static me.pixeldev.ecosmetics.api.util.VectorUtils.sin;

public class AtomEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final int STEPS = 32;
	private static final double ANGLE = Math.PI / (STEPS / 2D);

	private double step;

	public AtomEffectAnimation(WeakReference<Player> ownerReference,
														 CosmeticSpectators spectators,
														 ParticleEffect effect) {
		super(ownerReference, spectators, effect, 0);
	}

	@Override
	protected void runAnimation(Player player) {
		double ring1 = ANGLE * step;
		double ring2 = ANGLE * ((step + STEPS / 2D) % STEPS);

		Location playerLocation = player.getLocation();
		Location firstLocation = playerLocation.clone().add(cos(ring1), sin(ring1), sin(ring1));
		Location secondLocation = playerLocation.clone().add(cos(ring1 + Math.PI), sin(ring1), sin(ring1 + Math.PI));
		Location thirdLocation = playerLocation.clone().add(cos(ring2), sin(ring2), sin(ring2));
		Location fourthLocation = playerLocation.clone().add(cos(ring2 + Math.PI), sin(ring2), sin(ring2 + Math.PI));

		spectators.consumeAsPlayers(spectator -> {
			effect.display(firstLocation, 0, 0, 0, 0, 1, null, spectator);
			effect.display(secondLocation, 0, 0, 0, 0, 1, null, spectator);
			effect.display(thirdLocation, 0, 0, 0, 0, 1, null, spectator);
			effect.display(fourthLocation, 0, 0, 0, 0, 1, null, spectator);
		});

		step = (step + 1) % STEPS;
	}

}
