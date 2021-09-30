package me.pixeldev.ecosmetics.v1_8_R3.effect;

import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.effect.EffectHandler;
import me.pixeldev.ecosmetics.v1_8_R3.track.EntityTrackerManager;

import javax.inject.Inject;

public class SimpleEffectHandler implements EffectHandler {

	@Inject private EntityTrackerManager<EffectCosmetic> trackerManager;

	@Override
	public void create(EffectCosmetic effectCosmetic) {
		trackerManager.bindEntry(effectCosmetic, new EffectEntityTrackerEntry(effectCosmetic));
	}

	@Override
	public void destroy(EffectCosmetic effectCosmetic) {
		trackerManager.unbindEntry(effectCosmetic);
	}

}
