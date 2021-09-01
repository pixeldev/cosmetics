package me.pixeldev.ecosmetics.api.cosmetic;

import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import org.bukkit.Location;

import java.util.UUID;

public interface Cosmetic<T extends CosmeticType> {

	UUID getOwner();

	CosmeticCategory getCategory();

	T getType();

}
