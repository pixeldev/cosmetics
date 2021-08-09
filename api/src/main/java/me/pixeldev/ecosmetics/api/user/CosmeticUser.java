package me.pixeldev.ecosmetics.api.user;

import me.pixeldev.alya.storage.gson.meta.JsonFolder;
import me.pixeldev.alya.storage.universal.internal.meta.Cached;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Cached
@JsonFolder("users")
public class CosmeticUser {

	private UUID uuid;

	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}

}