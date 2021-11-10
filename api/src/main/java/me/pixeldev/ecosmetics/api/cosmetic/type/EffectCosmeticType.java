package me.pixeldev.ecosmetics.api.cosmetic.type;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.effect.animation.EffectAnimationType;

import org.bukkit.Color;
import org.bukkit.Material;

import xyz.xenondevs.particle.ParticleEffect;

import java.util.List;

public class EffectCosmeticType extends AbstractCosmeticType {

	private final List<Data> animationTypes;

	public EffectCosmeticType(String permission,
														String configurationIdentifier,
														Material menuIcon, CosmeticCategory category,
														List<Data> animationTypes) {
		super(permission, configurationIdentifier, menuIcon, category);
		this.animationTypes = animationTypes;
	}

	public List<Data> getAnimationTypes() {
		return animationTypes;
	}

	public EffectCosmeticType cloneWithOtherData(List<Data> data) {
		return new EffectCosmeticType(
			getPermission(), getIdentifier(), getMenuIcon(), getCategory(), data
		);
	}

	public interface Data {

		EffectAnimationType getAnimationType();

		default CustomData toCustomData(ParticleEffect effect) {
			return new CustomData(getAnimationType(), effect);
		}

	}

	public static class DefaultData implements Data {

		private final EffectAnimationType animationType;

		public DefaultData(EffectAnimationType animationType) {
			this.animationType = animationType;
		}

		@Override
		public EffectAnimationType getAnimationType() {
			return animationType;
		}

	}

	public static class CustomData extends DefaultData {

		private final ParticleEffect effect;

		public CustomData(EffectAnimationType animationType, ParticleEffect effect) {
			super(animationType);
			this.effect = effect;
		}

		public ParticleEffect getEffect() {
			return effect;
		}

	}

	public static class WingsData extends DefaultData {

		private final Color firstColor;
		private final Color secondColor;
		private final Color thirdColor;

		public WingsData(EffectAnimationType animationType,
										 Color firstColor, Color secondColor, Color thirdColor) {
			super(animationType);
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
