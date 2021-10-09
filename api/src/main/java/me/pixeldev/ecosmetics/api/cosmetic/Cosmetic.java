package me.pixeldev.ecosmetics.api.cosmetic;

import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.UUID;

public interface Cosmetic<T extends CosmeticType> {

	Player getPlayer();

	UUID getOwnerId();

	String getWorldName();

	void setWorlName(Location newLocation);

	CosmeticCategory getCategory();

	T getType();

}
