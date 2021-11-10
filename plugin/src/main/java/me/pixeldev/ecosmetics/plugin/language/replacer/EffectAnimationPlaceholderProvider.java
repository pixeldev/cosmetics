package me.pixeldev.ecosmetics.plugin.language.replacer;

import me.pixeldev.ecosmetics.api.cosmetic.effect.animation.EffectAnimationType;

import me.yushust.message.format.PlaceholderProvider;
import me.yushust.message.track.ContextRepository;

import org.jetbrains.annotations.Nullable;

public class EffectAnimationPlaceholderProvider
	implements PlaceholderProvider<EffectAnimationType> {

	@Override
	public @Nullable Object replace(ContextRepository contextRepository,
																	EffectAnimationType animationType, String placeholder) {
		return animationType.name();
	}

}
