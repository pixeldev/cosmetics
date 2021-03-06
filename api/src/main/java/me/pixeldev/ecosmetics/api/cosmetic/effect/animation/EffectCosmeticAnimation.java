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
			EffectAnimationType animationType = animationData.getAnimationType();
			EffectCosmeticAnimation animation = null;

			if (!animationType.hasCustomEffect()) {
				switch (animationType) {
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
					case WINGS: {
						EffectCosmeticType.WingsData wingsData = (EffectCosmeticType.WingsData) animationData;
						animation = new WingsEffectAnimation(
							ownerReference, spectators,
							wingsData.getFirstColor(), wingsData.getSecondColor(), wingsData.getThirdColor()
						);
						break;
					}
				}
			} else {
				ParticleEffect particleEffect = ((EffectCosmeticType.CustomData) animationData).getEffect();

				switch (animationType) {
					case AROUND: {
						animation = new AroundEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case RINGS: {
						animation = new RingsEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case WAVES: {
						animation = new WavesEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case COMPANION: {
						animation = new CompanionEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case BEAM: {
						animation = new BeamEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case CHAINS: {
						animation = new ChainsEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case FEET: {
						animation = new FeetEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case HALO: {
						animation = new HaloEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case NORMAL: {
						animation = new NormalEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case ORBIT: {
						animation = new OrbitEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case POINT: {
						animation = new PointEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case OVERHEAD: {
						animation = new OverheadEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case POPPER: {
						animation = new PopperEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case PULSE: {
						animation = new PulseEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case QUAD_HELIX: {
						animation = new QuadHelixEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case ATOM: {
						animation = new AtomEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case SPHERE: {
						animation = new SphereEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case SPIN: {
						animation = new SpinEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
					case WHIRL: {
						animation = new WhirlEffectAnimation(
							ownerReference, spectators, particleEffect
						);
						break;
					}
				}
			}

			if (animation == null) {
				continue;
			}

			animations.add(animation);
		}

		return animations;
	}

}
