package me.pixeldev.ecosmetics.api.user;

import me.pixeldev.alya.jdk.concurrent.AsyncResponse;

import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.UUID;

public interface CosmeticUserService {

	List<CosmeticUser> getAllCachedUsers();

	@Nullable CosmeticUser getUserById(UUID uuid);

	default @Nullable CosmeticUser getUserByPlayer(Player player) {
		return getUserById(player.getUniqueId());
	}

	CosmeticUser getUserOrCreateSync(Player player);

	AsyncResponse<CosmeticUser> getUserOrCreate(Player player);

	AsyncResponse<CosmeticUser> saveUser(Player player);

}
