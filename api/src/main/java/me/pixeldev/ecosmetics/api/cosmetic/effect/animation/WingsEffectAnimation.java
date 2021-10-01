package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;

import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import xyz.xenondevs.particle.ParticleEffect;
import xyz.xenondevs.particle.data.ParticleData;
import xyz.xenondevs.particle.data.color.DustData;

import java.lang.ref.WeakReference;

import static me.pixeldev.ecosmetics.api.util.ColorUtils.parseColorToData;
import static me.pixeldev.ecosmetics.api.util.VectorUtils.getBackVector;
import static me.pixeldev.ecosmetics.api.util.VectorUtils.rotateAroundAxisY;

public class WingsEffectAnimation
	extends AbstractEffectCosmeticAnimation {

	private static final char[][] LAYOUT = {
		{'f', 'f', 'f', 'f', 'f', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'f', 'f', 'f', 'f', 'f'},
		{'f', 'f', 'f', 'f', 'f', 'f', 'f', 'o', 'o', 'o', 'o', 'o', 'f', 'f', 'f', 'f', 'f', 'f', 'f'},
		{'o', 'f', 'f', 'f', 'f', 'f', 'f', 's', 's', 'o', 's', 's', 'f', 'f', 'f', 'f', 'f', 'f', 'o'},
		{'o', 'o', 'f', 'f', 'f', 'f', 's', 's', 's', 's', 's', 's', 's', 'f', 'f', 'f', 'f', 'o', 'o'},
		{'o', 'o', 'o', 'f', 'f', 's', 's', 's', 's', 't', 's', 's', 's', 's', 'f', 'f', 'o', 'o', 'o'},
		{'o', 'o', 's', 's', 's', 's', 's', 's', 't', 't', 't', 's', 's', 's', 's', 's', 's', 'o', 'o'},
		{'o', 'o', 'o', 'o', 's', 's', 's', 's', 't', 't', 't', 's', 's', 's', 's', 'o', 'o', 'o', 'o'},
		{'o', 'o', 'o', 'o', 's', 's', 's', 't', 't', 'o', 't', 't', 's', 's', 's', 'o', 'o', 'o', 'o'},
		{'o', 'o', 'o', 's', 's', 't', 't', 't', 'o', 'o', 'o', 't', 't', 't', 's', 's', 'o', 'o', 'o'},
		{'o', 'o', 't', 't', 't', 't', 't', 'o', 'o', 'o', 'o', 'o', 't', 't', 't', 't', 't', 'o', 'o'},
		{'o', 'o', 't', 't', 't', 't', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 't', 't', 't', 't', 'o', 'o'},
		{'o', 'o', 'o', 't', 't', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 'o', 't', 't', 'o', 'o', 'o'}
	};

	private static final double SPACE = 0.2;
	private static final double X_LENGTH = SPACE * LAYOUT[0].length / 2;
	private static final double Y_LENGTH = SPACE * LAYOUT.length;

	private final ParticleData firstColor;
	private final ParticleData secondColor;
	private final ParticleData thirdColor;

	public WingsEffectAnimation(WeakReference<Player> ownerReference,
															CosmeticSpectators spectators,
															Color firstColor, Color secondColor, Color thirdColor) {
		super(ownerReference, spectators, ParticleEffect.REDSTONE, 10);
		this.firstColor = parseColorToData(firstColor);
		this.secondColor = parseColorToData(secondColor);
		this.thirdColor = parseColorToData(thirdColor);
	}

	@Override
	protected void runAnimation(Player player) {
		Location playerLocation = player.getLocation();
		double baseX = playerLocation.getX() - X_LENGTH;
		double x = baseX;
		double y = playerLocation.getY() + Y_LENGTH;
		double angle = -Math.toRadians(playerLocation.getYaw());

		Vector locationVector = playerLocation.toVector();

		for (char[] line : LAYOUT) {
			for (char c : line) {
				if (c == 'o') {
					x += SPACE;
					continue;
				}

				Location preTarget = playerLocation.clone();
				preTarget.setX(x);
				preTarget.setY(y);

				Vector rotation = preTarget.toVector().subtract(locationVector);
				Vector backDirection = getBackVector(playerLocation);
				rotateAroundAxisY(rotation, angle);
				backDirection.multiply(-0.25);

				Location target = playerLocation.clone();

				target.add(rotation);
				target.add(backDirection);

				ParticleData color = new DustData(255, 255, 255, 1.25F);
				switch (c) {
					case 'f': {
						color = firstColor;
						break;
					}
					case 's': {
						color = secondColor;
						break;
					}
					case 't': {
						color = thirdColor;
						break;
					}
				}

				ParticleData finalColor = color;

				spectators.consumeAsPlayers(spectator -> {
					effect.display(
						target, 0, 0, 0,
						0, 1, finalColor, spectator
					);
					System.out.println("Showing to " + spectator.getName());
				});

				x += SPACE;
			}
			y -= SPACE;
			x = baseX;
		}
	}

}
