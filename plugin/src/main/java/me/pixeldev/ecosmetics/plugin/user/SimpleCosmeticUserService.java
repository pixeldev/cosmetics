package me.pixeldev.ecosmetics.plugin.user;

import me.pixeldev.alya.jdk.concurrent.AsyncExecutor;
import me.pixeldev.alya.jdk.concurrent.AsyncResponse;
import me.pixeldev.alya.jdk.concurrent.SimpleAsyncResponse;
import me.pixeldev.alya.jdk.concurrent.SimpleResponse;
import me.pixeldev.alya.storage.universal.service.FindService;
import me.pixeldev.alya.storage.universal.service.UploadService;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import org.bukkit.entity.Player;

import javax.inject.Inject;

import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

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
			return findService.findSync(uuid.toString(), false);
		} catch (Exception ignored) {
			return null;
		}
	}

	@Override
	public CosmeticUser getUserOrCreateSync(Player player) {
		UUID playerId = player.getUniqueId();
		CosmeticUser cosmeticUser;

		try {
			cosmeticUser = findService.findSync(
				playerId.toString(), true
			);
		} catch (Exception e) {
			cosmeticUser = null;
		}

		if (cosmeticUser == null) {
			cosmeticUser = CosmeticUser.create(player);

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
	public AsyncResponse<CosmeticUser> getUserOrCreate(Player player) {
		return new SimpleAsyncResponse<>(
			CompletableFuture.supplyAsync(() -> {
				CosmeticUser cosmeticUser = getUserOrCreateSync(player);

				if (cosmeticUser == null) {
					return SimpleResponse.error(new IllegalArgumentException("User is null."));
				} else {
					return SimpleResponse.success(cosmeticUser);
				}
			}, AsyncExecutor.EXECUTOR)
		);
	}

	@Override
	public AsyncResponse<CosmeticUser> saveUser(Player player) {
		try {
			CosmeticUser user = findService.findSync(
				player.getUniqueId().toString(), false
			);

			return new SimpleAsyncResponse<>(
				CompletableFuture.supplyAsync(() -> {
					if (user != null) {
						try {
							uploadService.uploadSync(user, true);
						} catch (Exception e) {
							e.printStackTrace();
							return SimpleResponse.error(e);
						}
					}
					return SimpleResponse.success(user);
				}, AsyncExecutor.EXECUTOR)
			);
		} catch (Exception ignored) {
			return null;
		}
	}

}
