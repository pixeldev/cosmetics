package me.pixeldev.ecosmetics.api.cosmetic;

import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;

import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.UUID;

public abstract class AbstractCosmetic<T extends CosmeticType>
	implements Cosmetic<T> {

	protected final WeakReference<Player> ownerReference;
	protected String worldName;
	protected boolean equipped;
	protected final UUID ownerId;
	protected final T type;

	protected AbstractCosmetic(WeakReference<Player> ownerReference, T type) {
		this.ownerReference = ownerReference;

		Player owner = ownerReference.get();
		this.worldName = owner.getWorld().getName();
		this.equipped = true;
		this.ownerId = owner.getUniqueId();
		this.type = type;
	}

	@Override
	public Player getPlayer() {
		return ownerReference.get();
	}

	@Override
	public UUID getOwnerId() {
		return ownerId;
	}

	@Override
	public T getType() {
		return type;
	}

	@Override
	public String getWorldName() {
		return worldName;
	}

	@Override
	public boolean isEquipped() {
		return equipped;
	}

	@Override
	public void setEquipped(boolean equipped) {
		this.equipped = equipped;
	}

}
