package me.pixeldev.ecosmetics.api.user;

import me.pixeldev.alya.jdk.concurrent.observer.Observable;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CosmeticUserService {

	List<CosmeticUser> getAllCachedUsers();

	Optional<CosmeticUser> getUserByIdSync(UUID uuid);

	default Optional<CosmeticUser> getUserByPlayerSync(Player player) {
		return getUserByIdSync(player.getUniqueId());
	}

	Observable<Optional<CosmeticUser>> getUserById(UUID uuid);

	default Observable<Optional<CosmeticUser>> getUserByPlayer(Player player) {
		return getUserById(player.getUniqueId());
	}

	CosmeticUser getUserOrCreateSync(Player player);

	Observable<CosmeticUser> getUserOrCreate(Player player);

	Observable<CosmeticUser> saveUser(Player player);

}