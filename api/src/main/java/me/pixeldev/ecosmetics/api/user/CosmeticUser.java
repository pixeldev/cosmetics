package me.pixeldev.ecosmetics.api.user;

import me.pixeldev.alya.storage.gson.meta.JsonFolder;
import me.pixeldev.alya.storage.universal.internal.meta.Cached;

import java.util.UUID;

@Cached
@JsonFolder("users")
public class CosmeticUser {

	private UUID uuid;


}