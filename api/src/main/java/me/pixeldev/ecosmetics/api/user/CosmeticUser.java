package me.pixeldev.ecosmetics.api.user;

import me.pixeldev.alya.storage.gson.meta.JsonFolder;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.internal.meta.Cached;
import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;

import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;
import java.util.UUID;

@Cached
@JsonFolder("users")
public final class CosmeticUser implements Model {

	private final UUID uuid;
	private final String username;

	private CosmeticCategory currentCategory;
	private String currentTypeKey;

	private transient WeakReference<Player> playerReference;
	private transient Cosmetic<?> currentCosmetic;

	private CosmeticUser(Player player) {
		this.uuid = player.getUniqueId();
		this.username = player.getName();
		this.playerReference = new WeakReference<>(player);
	}

	@Override
	public String getId() {
		return uuid.toString();
	}

	public Player getPlayer() {
		return playerReference.get();
	}

	public WeakReference<Player> getPlayerReference() {
		return playerReference;
	}

	public void setPlayerReference(Player player) {
		this.playerReference = new WeakReference<>(player);
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
		if (currentCosmetic == null) {
			this.currentCosmetic = null;
			this.currentCategory = null;
			this.currentTypeKey = null;
		} else {
			this.currentCosmetic = currentCosmetic;
			this.currentCategory = currentCosmetic.getCategory();
			this.currentTypeKey = currentCosmetic.getType().getIdentifier();
		}
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

	public static CosmeticUser create(Player player) {
		return new CosmeticUser(player);
	}

}
