package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;

import static me.pixeldev.ecosmetics.api.util.VectorUtils.*;

public class CompanionEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final int PARTICLES = 150;
	private static final int PARTICLES_PER_ITERATION = 3;
	private static final double SIZE = 0.75;
	private static final double X_FACTOR = 1;
	private static final double Y_FACTOR = 1.25;
	private static final double Z_FACTOR = 1;
	private static final double X_OFFSET = 0;
	private static final double Y_OFFSET = -1.5;
	private static final double Z_OFFSET = 0;

	private int step;

	public CompanionEffectAnimation(WeakReference<Player> ownerReference,
																	CosmeticSpectators spectators, ParticleEffect effect) {
		super(ownerReference, spectators, effect, 0);
	}

	@Override
	protected void runAnimation(Player player) {
		Vector vector = new Vector();
		double t = (Math.PI / PARTICLES) * this.step++;
		double r = sin(t) * SIZE;
		double s = 2 * Math.PI * t;

		vector.setX(X_FACTOR * r * cos(s) + X_OFFSET);
		vector.setY(Y_FACTOR * SIZE * cos(t) + Y_OFFSET);
		vector.setZ(Z_FACTOR * r * sin(s) + Z_OFFSET);

		for (int i = 0; i < PARTICLES_PER_ITERATION; i++) {
			Location location = player.getLocation().subtract(vector);
			spectators.consumeAsPlayers(spectator -> effect.display(location, spectator));
		}
	}

}
