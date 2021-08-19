package me.pixeldev.ecosmetics.plugin.user;

import com.google.gson.Gson;

import me.pixeldev.alya.storage.gson.JsonModelService;
import me.pixeldev.alya.storage.gson.meta.JsonModelMeta;
import me.pixeldev.alya.storage.universal.service.CompleteModelService;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import org.bukkit.plugin.Plugin;

import javax.inject.Inject;
import javax.inject.Provider;

public class CosmeticUserModelServiceProvider
	implements Provider<CompleteModelService<CosmeticUser>> {

	@Inject private Gson mapper;
	@Inject private Plugin plugin;

	@Override
	public CompleteModelService<CosmeticUser> get() {
		return new JsonModelService<>(
			mapper, plugin, new JsonModelMeta<>(CosmeticUser.class)
		);
	}

}