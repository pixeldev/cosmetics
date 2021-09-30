package me.pixeldev.ecosmetics.v1_8_R3;

import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectHandler;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;
import me.pixeldev.ecosmetics.v1_8_R3.effect.SimpleEffectHandler;
import me.pixeldev.ecosmetics.v1_8_R3.pet.SimplePetEntityHandler;
import me.pixeldev.ecosmetics.v1_8_R3.track.EntityTrackerManager;

import me.yushust.inject.AbstractModule;
import me.yushust.inject.key.TypeReference;

public class AdapterModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PetEntityHandler.class).to(SimplePetEntityHandler.class).singleton();
		bind(EffectHandler.class).to(SimpleEffectHandler.class).singleton();
		bind(new TypeReference<EntityTrackerManager<PetCosmetic>>() {})
			.toInstance(new EntityTrackerManager<>());
		bind(new TypeReference<EntityTrackerManager<EffectCosmetic>>() {})
			.toInstance(new EntityTrackerManager<>());
	}

}
