package me.pixeldev.ecosmetics.api.cosmetic;

import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;

import org.bukkit.Location;

import java.util.UUID;

public abstract class AbstractCosmetic<T extends CosmeticType>
	implements Cosmetic<T> {

	protected final UUID owner;
	protected final T type;

	protected AbstractCosmetic(UUID owner, T type) {
		this.owner = owner;
		this.type = type;
	}

	@Override
	public UUID getOwner() {
		return owner;
	}

	@Override
	public T getType() {
		return type;
	}

}