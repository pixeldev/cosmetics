package me.pixeldev.ecosmetics.api.cosmetic.event;

import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class PreAddCosmeticSpectatorEvent
	extends PlayerEvent
	implements Cancellable {

	private static final HandlerList HANDLER_LIST = new HandlerList();

	private final Cosmetic<?> cosmetic;
	private boolean cancelled;

	public PreAddCosmeticSpectatorEvent(Player who, Cosmetic<?> cosmetic) {
		super(who);
		this.cosmetic = cosmetic;
	}

	@Override
	public HandlerList getHandlers() {
		return HANDLER_LIST;
	}

	public Cosmetic<?> getCosmetic() {
		return cosmetic;
	}

	@Override
	public boolean isCancelled() {
		return cancelled;
	}

	@Override
	public void setCancelled(boolean cancel) {
		cancelled = cancel;
	}

	public static HandlerList getHandlerList() {
		return HANDLER_LIST;
	}
}
