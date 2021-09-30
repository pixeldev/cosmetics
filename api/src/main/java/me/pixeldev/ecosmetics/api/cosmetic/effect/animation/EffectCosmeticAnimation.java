package me.pixeldev.ecosmetics.api.cosmetic.effect.animation;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;
import me.pixeldev.ecosmetics.api.cosmetic.animation.CosmeticAnimation;
import me.pixeldev.ecosmetics.api.cosmetic.type.EffectCosmeticType;

import org.bukkit.entity.Player;

import xyz.xenondevs.particle.ParticleEffect;

import java.lang.ref.WeakReference;
import java.util.HashSet;
import java.util.Set;

public interface EffectCosmeticAnimation
	extends CosmeticAnimation {

	static Set<EffectCosmeticAnimation> of(WeakReference<Player> ownerReference,
																				 CosmeticSpectators spectators,
																				 EffectCosmeticType cosmeticType) {
		Set<EffectCosmeticAnimation> animations = new HashSet<>();

		for (EffectCosmeticType.Data animationData : cosmeticType.getAnimationTypes()) {
			ParticleEffect particleEffect = animationData.getEffect();
			EffectAnimationType animationType = animationData.getAnimationType();
			EffectCosmeticAnimation animation;
			
			switch (animationType) {
				case AROUND: {
					animation = new AroundEffectAnimation(ownerReference, spectators, particleEffect);
					break;
				}
				case ENDER_AURA: {
					animation = new EnderAuraEffectAnimation(ownerReference, spectators);
					break;
				}
				case LOVE: {
					animation = new LoveEffectAnimation(ownerReference, spectators);
					break;
				}
				case MUSIC: {
					animation = new MusicEffectAnimation(ownerReference, spectators);
					break;
				}
				case RINGS: {
					animation = new RingsEffectAnimation(ownerReference, spectators, particleEffect);
					break;
				}
				case WAVES: {
					animation = new WavesEffectAnimation(ownerReference, spectators, particleEffect);
					break;
				}
				case WINGS: {
					EffectCosmeticType.WingsData wingsData = (EffectCosmeticType.WingsData) animationData;
					animation = new WingsEffectAnimation(
						ownerReference, spectators,
						wingsData.getFirstColor(), wingsData.getSecondColor(), wingsData.getThirdColor()
					);
					break;
				}
				default:
					animation = null;
					break;
			}

			if (animation == null) {
				continue;
			}

			animations.add(animation);
		}

		return animations;
	}

}
