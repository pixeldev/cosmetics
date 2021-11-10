package me.pixeldev.ecosmetics.plugin.module;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import me.pixeldev.alya.api.loader.Loader;
import me.pixeldev.alya.bukkit.loader.AutoCommandLoader;
import me.pixeldev.alya.bukkit.loader.AutoListenerLoader;
import me.pixeldev.alya.bukkit.loader.ServerTickEventLoader;
import me.pixeldev.alya.storage.gson.adapt.AbstractionTypeAdapter;
import me.pixeldev.alya.storage.gson.adapt.RuntimeTypeAdapterFactory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.PlayerSkinProvider;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProvider;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.UrlSkinProvider;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.ViewerSkinProvider;
import me.pixeldev.ecosmetics.api.cosmetic.type.EffectCosmeticType;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;
import me.pixeldev.ecosmetics.plugin.loader.CosmeticTypeLoader;
import me.yushust.inject.AbstractModule;

public final class StructureModule extends AbstractModule {

	@Override
	protected void configure() {
		multibind(Loader.class).asSet()
			.to(CosmeticTypeLoader.class)
			.to(AutoCommandLoader.class)
			.to(ServerTickEventLoader.class)
			.to(AutoListenerLoader.class);

		bind(Gson.class).toInstance(new GsonBuilder()
			.setPrettyPrinting()
			.registerTypeAdapter(
				EffectCosmeticType.Data.class,
				new AbstractionTypeAdapter<>()
			)
			.registerTypeAdapterFactory(RuntimeTypeAdapterFactory
				.of(EffectCosmeticType.Data.class)
				.registerSubtype(EffectCosmeticType.DefaultData.class)
				.registerSubtype(EffectCosmeticType.WingsData.class)
				.registerSubtype(EffectCosmeticType.CustomData.class)
			)
			.registerTypeAdapterFactory(RuntimeTypeAdapterFactory
				.of(SkinProvider.class)
				.registerSubtype(UrlSkinProvider.class)
				.registerSubtype(ViewerSkinProvider.class)
				.registerSubtype(PlayerSkinProvider.class)
			)
			.create()
		);
	}

}
