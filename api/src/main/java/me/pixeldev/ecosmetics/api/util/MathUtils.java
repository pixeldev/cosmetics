package me.pixeldev.ecosmetics.api.util;

import me.pixeldev.alya.bukkit.BukkitBasePlugin;

import java.util.Random;

public final class MathUtils {

	private static final Random RANDOM = BukkitBasePlugin.RANDOM;

	private MathUtils() {

	}

	public static int randomInt(int bound) {
		return RANDOM.nextInt(bound);
	}

	public static double randomDouble(double min, double max) {
		return RANDOM.nextDouble() < 0.5 ?
			((1 - RANDOM.nextDouble()) * (max - min) + min) :
			(RANDOM.nextDouble() * (max - min) + min);
	}

	public static double randomDouble() {
		return RANDOM.nextDouble();
	}

}
