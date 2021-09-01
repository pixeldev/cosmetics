package me.pixeldev.ecosmetics.plugin.user;

import me.pixeldev.alya.jdk.concurrent.observer.Observable;
import me.pixeldev.alya.storage.universal.service.FindService;
import me.pixeldev.alya.storage.universal.service.UploadService;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class SimpleCosmeticUserService implements CosmeticUserService {

	@Inject private FindService<CosmeticUser> findService;
	@Inject private UploadService<CosmeticUser> uploadService;

	@Override
	public List<CosmeticUser> getAllCachedUsers() {
		try {
			return findService.findAllFromCacheSync();
		} catch (Exception ignored) {
			return Collections.emptyList();
		}
	}

	@Override
	public CosmeticUser getUserById(UUID uuid) {
		try {
			return findService.findSync(uuid.toString(), false)
				.orElse(null);
		} catch (Exception ignored) {
			return null;
		}
	}

	@Override
	public CosmeticUser getUserOrCreateSync(Player player) {
		UUID playerId = player.getUniqueId();
		CosmeticUser cosmeticUser = getUserById(playerId);

		if (cosmeticUser == null) {
			cosmeticUser = CosmeticUser.of(player);

			try {
				uploadService.uploadSync(cosmeticUser, false);
			} catch (Exception e) {
				e.printStackTrace();
				return null;
			}

			return cosmeticUser;
		}

		return cosmeticUser;
	}

	@Override
	public Observable<CosmeticUser> getUserOrCreate(Player player) {
		return new Observable<>(() -> getUserOrCreateSync(player));
	}

	@Override
	public Observable<CosmeticUser> saveUser(Player player) {
		try {
			Optional<CosmeticUser> userOptional = findService.findSync(
				player.getUniqueId().toString(), false
			);

			if (!userOptional.isPresent()) {
				return new Observable<>(() -> null);
			}

			CosmeticUser user = userOptional.get();

			return new Observable<>(() -> {
				uploadService.upload(user, true);
				return user;
			});
		} catch (Exception ignored) {
			return null;
		}
	}

}
