package me.pixeldev.ecosmetics.v1_8_R3.track;

import net.minecraft.server.v1_8_R3.Entity;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.EntityTrackerEntry;
import org.bukkit.Location;

public abstract class AbstractEntityTrackerEntry
	extends EntityTrackerEntry {

	private static final Entity MARKER = new EmptyEntity();
	private static final boolean PAPER;

	static {
		boolean isPaper;

		try {
			Class.forName("com.destroystokyo.paper.Title");
			isPaper = true;
		} catch (ClassNotFoundException e) {
			isPaper = false;
		}

		PAPER = isPaper;
	}

	public AbstractEntityTrackerEntry() {
		super(MARKER, -1, -1, false);
	}

	protected void addToTrackedPlayers(EntityPlayer player) {
		if (PAPER) {
			trackedPlayerMap.put(player, true);
		} else {
			trackedPlayers.add(player);
		}
	}

	@Override
	public void updatePlayer(EntityPlayer player) {
		Location location = getLocation();

		if (!player.u().getPlayerChunkMap().a(
			player, location.getBlockX() >> 4, location.getBlockZ() >> 4
		)) {
			if (this.trackedPlayers.remove(player)) {
				hide(player);
			}
			return;
		}

		if (!this.trackedPlayers.contains(player)) {
			addToTrackedPlayers(player);
			show(player);
		}
	}

	@Override
	public void a() {
		trackedPlayers.removeIf(player -> {
			hide(player);
			return true;
		});
	}

	@Override
	public void a(EntityPlayer player) {
		if (this.trackedPlayers.remove(player)) {
			hide(player);
		}
	}

	@Override
	public boolean equals(Object o) {
		return this == o;
	}

	@Override
	public int hashCode() {
		return System.identityHashCode(this);
	}

	protected abstract Location getLocation();

	protected abstract void show(EntityPlayer player);

	protected abstract void hide(EntityPlayer player);

}
