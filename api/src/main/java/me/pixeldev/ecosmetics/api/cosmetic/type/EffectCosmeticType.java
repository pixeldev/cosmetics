package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.effect.animation.EffectAnimationType;

import org.bukkit.Color;
import org.bukkit.Material;

import xyz.xenondevs.particle.ParticleEffect;

import java.util.Set;

public class EffectCosmeticType extends AbstractCosmeticType {

	private final Set<Data> animationTypes;

	public EffectCosmeticType(String name, String permission,
														String configurationIdentifier,
														Material menuIcon, CosmeticCategory category,
														Set<Data> animationTypes) {
		super(name, permission, configurationIdentifier, menuIcon, category);
		this.animationTypes = animationTypes;
	}

	public Set<Data> getAnimationTypes() {
		return animationTypes;
	}

	public static class Data {

		private final ParticleEffect effect;
		private final EffectAnimationType animationType;

		public Data(ParticleEffect effect, EffectAnimationType animationType) {
			this.effect = effect;
			this.animationType = animationType;
		}

		public ParticleEffect getEffect() {
			return effect;
		}

		public EffectAnimationType getAnimationType() {
			return animationType;
		}

	}

	public static class WingsData extends Data {

		private final Color firstColor;
		private final Color secondColor;
		private final Color thirdColor;

		public WingsData(ParticleEffect effect, EffectAnimationType animationType,
										 Color firstColor, Color secondColor, Color thirdColor) {
			super(effect, animationType);
			this.firstColor = firstColor;
			this.secondColor = secondColor;
			this.thirdColor = thirdColor;
		}

		public Color getFirstColor() {
			return firstColor;
		}

		public Color getSecondColor() {
			return secondColor;
		}

		public Color getThirdColor() {
			return thirdColor;
		}

	}

}
