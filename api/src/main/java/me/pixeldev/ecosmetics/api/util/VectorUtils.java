package me.pixeldev.ecosmetics.api.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

public final class VectorUtils {

	private static final int SIN_BITS, SIN_MASK, SIN_COUNT;
	private static final double RAD_FULL, RAD_TO_INDEX;
	private static final double DEG_FULL, DEG_TO_INDEX;
	private static final double[] SIN, COS;

	static {
		SIN_BITS = 12;
		SIN_MASK = ~(-1 << SIN_BITS);
		SIN_COUNT = SIN_MASK + 1;

		RAD_FULL = Math.PI * 2.0;
		DEG_FULL = 360.0;
		RAD_TO_INDEX = SIN_COUNT / RAD_FULL;
		DEG_TO_INDEX = SIN_COUNT / DEG_FULL;

		SIN = new double[SIN_COUNT];
		COS = new double[SIN_COUNT];

		for (int i = 0; i < SIN_COUNT; i++) {
			SIN[i] = Math.sin((i + 0.5f) / SIN_COUNT * RAD_FULL);
			COS[i] = Math.cos((i + 0.5f) / SIN_COUNT * RAD_FULL);
		}

		for (int i = 0; i < 360; i += 90) {
			SIN[(int) (i * DEG_TO_INDEX) & SIN_MASK] = Math.sin(i * Math.PI / 180.0);
			COS[(int) (i * DEG_TO_INDEX) & SIN_MASK] = Math.cos(i * Math.PI / 180.0);
		}
	}

	private VectorUtils() {
	}

	public static double sin(double rad) {
		return SIN[(int) (rad * RAD_TO_INDEX) & SIN_MASK];
	}

	public static double cos(double rad) {
		return COS[(int) (rad * RAD_TO_INDEX) & SIN_MASK];
	}

	public static Vector getBackVector(Location location) {
		float angle = location.getYaw() + 90;
		float newZ = (float) (location.getZ() + (1 * Math.sin(Math.toRadians(angle))));
		float newX = (float) (location.getX() + (1 * Math.cos(Math.toRadians(angle))));

		return new Vector(newX - location.getX(), 0, newZ - location.getZ());
	}

	public static Vector rotateAroundAxisX(Vector v, double angle) {
		double y, z, cos, sin;
		cos = cos(angle);
		sin = sin(angle);
		y = v.getY() * cos - v.getZ() * sin;
		z = v.getY() * sin + v.getZ() * cos;
		return v.setY(y).setZ(z);
	}

	public static Vector rotateAroundAxisY(Vector v, double angle) {
		double x, z, cos, sin;
		cos = cos(angle);
		sin = sin(angle);
		x = v.getX() * cos + v.getZ() * sin;
		z = v.getX() * -sin + v.getZ() * cos;
		return v.setX(x).setZ(z);
	}

	public static void rotateAroundAxisZ(Vector v, double angle) {
		double x, y, cos, sin;
		cos = cos(angle);
		sin = sin(angle);
		x = v.getX() * cos - v.getY() * sin;
		y = v.getX() * sin + v.getY() * cos;
		v.setX(x).setY(y);
	}

}
