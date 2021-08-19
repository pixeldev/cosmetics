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
	public Optional<CosmeticUser> getUserByIdSync(UUID uuid) {
		try {
			return findService.findSync(uuid.toString(), true);
		} catch (Exception ignored) {
			return Optional.empty();
		}
	}

	@Override
	public Observable<Optional<CosmeticUser>> getUserById(UUID uuid) {
		return findService.find(uuid.toString(), true);
	}

	@Override
	public CosmeticUser getUserOrCreateSync(Player player) {
		return getUserByIdSync(player.getUniqueId()).orElseGet(() -> {
			CosmeticUser newUser = CosmeticUser.of(player);

			try {
				uploadService.uploadSync(newUser, false);
				return newUser;
			} catch (Exception ignored) {
				return null;
			}
		});
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