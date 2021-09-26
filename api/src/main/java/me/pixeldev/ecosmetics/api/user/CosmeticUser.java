package me.pixeldev.ecosmetics.api.user;

import me.pixeldev.alya.storage.gson.meta.JsonFolder;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.internal.meta.Cached;
import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@Cached
@JsonFolder("users")
public final class CosmeticUser implements Model {

	private final UUID uuid;
	private final String username;

	private CosmeticCategory currentCategory;
	private String currentTypeKey;

	private transient Cosmetic<?> currentCosmetic;

	private CosmeticUser(UUID uuid, String username) {
		this.uuid = uuid;
		this.username = username;
	}

	@Override
	public String getId() {
		return uuid.toString();
	}

	public Player getPlayer() {
		return Bukkit.getPlayer(uuid);
	}

	public UUID getUuid() {
		return uuid;
	}

	public String getUsername() {
		return username;
	}

	public Cosmetic<?> getCurrentCosmetic() {
		return currentCosmetic;
	}

	public void setCurrentCosmetic(Cosmetic<?> currentCosmetic) {
		this.currentCosmetic = currentCosmetic;
		this.currentCategory = currentCosmetic.getCategory();
		this.currentTypeKey = currentCosmetic.getType().getIdentifier();
	}

	public CosmeticCategory getCurrentCategory() {
		return currentCategory;
	}

	public String getCurrentTypeKey() {
		return currentTypeKey;
	}

	public void setCurrentCategory(CosmeticCategory currentCategory) {
		this.currentCategory = currentCategory;
	}

	public void setCurrentTypeKey(String currentTypeKey) {
		this.currentTypeKey = currentTypeKey;
	}

	@Override
	public String toString() {
		return "CosmeticUser{" +
			"uuid=" + uuid +
			", username='" + username + '\'' +
			", currentCategory=" + currentCategory +
			", currentTypeKey='" + currentTypeKey + '\'' +
			'}';
	}

	public static CosmeticUser of(Player player) {
		return new CosmeticUser(player.getUniqueId(), player.getName());
	}

}
