package me.pixeldev.ecosmetics.plugin.service;

import me.pixeldev.alya.api.service.Service;
import me.pixeldev.alya.storage.universal.service.UploadService;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import javax.inject.Inject;

public class CosmeticUserService implements Service {

	@Inject private UploadService<CosmeticUser> uploadService;

	@Override
	public void start() {

	}

	@Override
	public void stop() {
		try {
			uploadService.uploadAllSync(user -> {});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
