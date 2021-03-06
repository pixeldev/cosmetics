package me.pixeldev.ecosmetics.v1_8_R3.track;

import net.minecraft.server.v1_8_R3.EntityTracker;
import net.minecraft.server.v1_8_R3.EntityTrackerEntry;
import net.minecraft.server.v1_8_R3.WorldServer;

import org.bukkit.World;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;

import java.lang.reflect.Field;
import java.util.Set;

public class EntityTrackerAccessor {

	private static final Field ENTRIES_FIELD;

	static {
		try {
			ENTRIES_FIELD = EntityTracker.class.getDeclaredField("c");
			ENTRIES_FIELD.setAccessible(true);
		} catch (NoSuchFieldException e) {
			throw new ExceptionInInitializerError(e);
		}
	}

	private EntityTrackerAccessor() {

	}

	public static void addEntry(World world, EntityTrackerEntry entry) {
		WorldServer worldServer = ((CraftWorld) world).getHandle();
		EntityTracker tracker = worldServer.getTracker();

		try {
			@SuppressWarnings("unchecked") Set<EntityTrackerEntry> entries =
				(Set<EntityTrackerEntry>) ENTRIES_FIELD.get(tracker);

			entries.add(entry);
		} catch (IllegalAccessException ignored) {
		}

		entry.scanPlayers(worldServer.players);
	}

	public static void removeEntry(World world, EntityTrackerEntry entry) {
		WorldServer worldServer = ((CraftWorld) world).getHandle();
		EntityTracker tracker = worldServer.getTracker();

		try {
			@SuppressWarnings("unchecked") Set<EntityTrackerEntry> entries =
				(Set<EntityTrackerEntry>) ENTRIES_FIELD.get(tracker);

			if (entries.remove(entry)) {
				entry.a();
			}
		} catch (IllegalAccessException ignored) {
		}
	}

}
