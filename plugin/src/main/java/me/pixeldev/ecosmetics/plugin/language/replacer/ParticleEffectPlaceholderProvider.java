package me.pixeldev.ecosmetics.plugin.language.replacer;

import me.yushust.message.format.PlaceholderProvider;
import me.yushust.message.track.ContextRepository;

import org.jetbrains.annotations.Nullable;

import xyz.xenondevs.particle.ParticleEffect;

public class ParticleEffectPlaceholderProvider
	implements PlaceholderProvider<ParticleEffect> {
	@Override
	public @Nullable Object replace(ContextRepository contextRepository,
																	ParticleEffect effect, String s) {
		return effect.name();
	}
}
