package me.pixeldev.ecosmetics.api.user;

import me.pixeldev.alya.storage.gson.meta.JsonFolder;
import me.pixeldev.alya.storage.universal.Model;
import me.pixeldev.alya.storage.universal.internal.meta.Cached;
import me.pixeldev.ecosmetics.api.cosmetic.Cosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;

import me.pixeldev.ecosmetics.api.cosmetic.type.PetCosmeticType;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Cached
@JsonFolder("users")
public final class CosmeticUser implements Model {

	private final UUID uuid;
	private final String username;

	private CosmeticCategory currentCategory;
	private String currentTypeKey;

	private final transient Set<UUID> renderedMiniatures;
	private transient Cosmetic<?> currentCosmetic;

	private CosmeticUser(UUID uuid, String username) {
		this.uuid = uuid;
		this.username = username;
		renderedMiniatures = new HashSet<>();
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

	public Set<UUID> getRenderedMiniatures() {
		return renderedMiniatures;
	}

	public boolean addRenderedMiniature(Cosmetic<PetCosmeticType> cosmetic) {
		return renderedMiniatures.add(cosmetic.getOwner());
	}

	public boolean removeRenderedMiniature(Cosmetic<PetCosmeticType> cosmetic) {
		return renderedMiniatures.remove(cosmetic.getOwner());
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