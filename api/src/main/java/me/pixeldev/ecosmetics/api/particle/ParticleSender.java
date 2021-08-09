package me.pixeldev.ecosmetics.api.particle;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public interface ParticleSender {

	int[] EMPTY_DATA = new int[0];

	default double color(double colorInput) {
		return colorInput / 255;
	}

	void sendParticle(Object receiver, ParticleType particleType,
										double x, double y, double z,
										int count,
										double offsetX, double offsetY, double offsetZ,
										double extra, Object data);

	default void spawnParticle(World world, ParticleType particle, Location location, int count) {
		spawnParticle(world, particle, location.getX(), location.getY(), location.getZ(), count);
	}

	default void spawnParticle(World world, ParticleType particle,
														 double x, double y, double z,
														 int count) {
		spawnParticle(world, particle, x, y, z, count, null);
	}

	default <T> void spawnParticle(World world, ParticleType particle, Location location, int count, T data) {
		spawnParticle(world, particle, location.getX(), location.getY(), location.getZ(), count, data);
	}

	default <T> void spawnParticle(World world, ParticleType particle, double x, double y, double z, int count,
																 T data) {
		spawnParticle(world, particle, x, y, z, count, 0.0D, 0.0D, 0.0D, data);
	}

	default void spawnParticle(World world, ParticleType particle, Location location, int count, double offsetX,
														 double offsetY, double offsetZ) {
		spawnParticle(world, particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ);
	}

	default void spawnParticle(World world, ParticleType particle, double x, double y, double z, int count,
														 double offsetX, double offsetY, double offsetZ) {
		spawnParticle(world, particle, x, y, z, count, offsetX, offsetY, offsetZ, null);
	}

	default <T> void spawnParticle(World world, ParticleType particle, Location location, int count,
																 double offsetX, double offsetY, double offsetZ, T data) {
		spawnParticle(world, particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY,
			offsetZ, data);
	}

	default <T> void spawnParticle(World world, ParticleType particle, double x, double y, double z, int count,
																 double offsetX, double offsetY, double offsetZ, T data) {
		spawnParticle(world, particle, x, y, z, count, offsetX, offsetY, offsetZ, 1.0D, data);
	}

	default void spawnParticle(World world, ParticleType particle, Location location, int count, double offsetX,
														 double offsetY, double offsetZ, double extra) {
		spawnParticle(world, particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra);
	}

	default void spawnParticle(World world, ParticleType particle, double x, double y, double z, int count,
														 double offsetX, double offsetY, double offsetZ, double extra) {
		spawnParticle(world, particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, null);
	}

	default <T> void spawnParticle(World world, ParticleType particle, Location location, int count,
																 double offsetX, double offsetY, double offsetZ, double extra, T data) {
		spawnParticle(world, particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra, data);
	}

	default <T> void spawnParticle(World world, ParticleType particle, double x, double y, double z, int count,
																 double offsetX, double offsetY, double offsetZ, double extra, T data) {
		sendParticle(world, particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, data);
	}

	/*
	 * Player methods
	 */
	default void spawnParticle(Player player, ParticleType particle, Location location, int count) {
		spawnParticle(player, particle, location.getX(), location.getY(), location.getZ(), count);
	}

	default void spawnParticle(Player player, ParticleType particle, double x, double y, double z, int count) {
		spawnParticle(player, particle, x, y, z, count, null);
	}

	default <T> void spawnParticle(Player player, ParticleType particle, Location location, int count, T data) {
		spawnParticle(player, particle, location.getX(), location.getY(), location.getZ(), count, data);
	}

	default <T> void spawnParticle(Player player, ParticleType particle, double x, double y, double z, int count,
																 T data) {
		spawnParticle(player, particle, x, y, z, count, 0.0D, 0.0D, 0.0D, data);
	}

	default void spawnParticle(Player player, ParticleType particle, Location location, int count, double offsetX,
														 double offsetY, double offsetZ) {
		spawnParticle(player, particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ);
	}

	default void spawnParticle(Player player, ParticleType particle, double x, double y, double z, int count,
														 double offsetX, double offsetY, double offsetZ) {
		spawnParticle(player, particle, x, y, z, count, offsetX, offsetY, offsetZ, null);
	}

	default <T> void spawnParticle(Player player, ParticleType particle, Location location, int count,
																 double offsetX, double offsetY, double offsetZ, T data) {
		spawnParticle(player, particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, data);
	}

	default <T> void spawnParticle(Player player, ParticleType particle, double x, double y, double z, int count,
																 double offsetX, double offsetY, double offsetZ, T data) {
		spawnParticle(player, particle, x, y, z, count, offsetX, offsetY, offsetZ, 1.0D, data);
	}

	default void spawnParticle(Player player, ParticleType particle, Location location, int count, double offsetX,
														 double offsetY, double offsetZ, double extra) {
		spawnParticle(player, particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra);
	}

	default void spawnParticle(Player player, ParticleType particle, double x, double y, double z, int count,
														 double offsetX, double offsetY, double offsetZ, double extra) {
		spawnParticle(player, particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, null);
	}

	default <T> void spawnParticle(Player player, ParticleType particle, Location location, int count,
																 double offsetX, double offsetY, double offsetZ, double extra, T data) {
		spawnParticle(player, particle, location.getX(), location.getY(), location.getZ(), count, offsetX, offsetY, offsetZ, extra, data);
	}

	default <T> void spawnParticle(Player player, ParticleType particle, double x, double y, double z, int count,
																 double offsetX, double offsetY, double offsetZ, double extra, T data) {
		sendParticle(player, particle, x, y, z, count, offsetX, offsetY, offsetZ, extra, data);
	}

}