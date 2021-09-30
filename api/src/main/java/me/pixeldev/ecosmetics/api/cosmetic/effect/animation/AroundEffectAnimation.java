package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import org.bukkit.util.Vector;
import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

public class AroundEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private boolean up;
	private float height;
	private int step;

	public AroundEffectAnimation(WeakReference<Player> ownerReference,
															 CosmeticSpectators spectators, ParticleEffect effect) {
		super(ownerReference, spectators, effect, 1);
	}

	@Override
	protected void runAnimation(Player player) {
		if (up) {
			if (height < 2)
				height += 0.05;
			else
				up = false;
		} else {
			if (height > 0)
				height -= 0.05;
			else
				up = true;
		}

		double inc = (2 * Math.PI) / 100;
		double angle = step * inc;

		Vector vector = new Vector();
		vector.setX(Math.cos(angle) * 1.1);
		vector.setZ(Math.sin(angle) * 1.1);

		Location location = player.getLocation().add(vector).add(0, height, 0);

		spectators.consumeAsPlayers(spectator -> effect.display(location, spectator));

		step += 4;
	}

}
