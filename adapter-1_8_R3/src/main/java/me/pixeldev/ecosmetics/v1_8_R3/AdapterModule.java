package me.pixeldev.ecosmetics.v1_8_R3;

import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;
import me.pixeldev.ecosmetics.v1_8_R3.pet.SimplePetEntityHandler;
import me.yushust.inject.AbstractModule;

public class AdapterModule extends AbstractModule {

	@Override
	protected void configure() {
		bind(PetEntityHandler.class).to(SimplePetEntityHandler.class).singleton();
	}

}
