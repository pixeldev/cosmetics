package me.pixeldev.ecosmetics.api.cosmetic.type.pet;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle.PetParticleAnimationType;
import me.pixeldev.ecosmetics.api.cosmetic.pet.skin.SkinProvider;
import me.pixeldev.ecosmetics.api.cosmetic.type.AbstractCosmeticType;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import xyz.xenondevs.particle.ParticleEffect;

import java.util.Map;

public class PetCosmeticType extends AbstractCosmeticType {

	private final Map<Integer, ItemStack> armorContent;
	private final SkinProvider skinProvider;
	private final boolean invisible;
	private final boolean arms;

	private final ParticleEffect particleEffect;
	private final PetParticleAnimationType animationType;
	private final float incrementX;
	private final float incrementY;
	private final float incrementZ;
	private final int goalTicks;

	public PetCosmeticType(String permission,
												 String configurationIdentifier,
												 Material menuIcon, CosmeticCategory category,
												 Map<Integer, ItemStack> armorContent, SkinProvider skinProvider,
												 boolean invisible, boolean arms,
												 ParticleEffect particleEffect,
												 PetParticleAnimationType animationType,
												 float incrementX, float incrementY, float incrementZ,
												 int goalTicks) {
		super(permission, configurationIdentifier, menuIcon, category);
		this.armorContent = armorContent;
		this.skinProvider = skinProvider;
		this.invisible = invisible;
		this.arms = arms;
		this.particleEffect = particleEffect;
		this.animationType = animationType;
		this.incrementX = incrementX;
		this.incrementY = incrementY;
		this.incrementZ = incrementZ;
		this.goalTicks = goalTicks;
	}

	public Map<Integer, ItemStack> getArmorContent() {
		return armorContent;
	}

	public SkinProvider getSkinProvider() {
		return skinProvider;
	}

	public boolean isInvisible() {
		return invisible;
	}

	public boolean isArms() {
		return arms;
	}

	public ParticleEffect getParticleEffect() {
		return particleEffect;
	}

	public PetParticleAnimationType getAnimationType() {
		return animationType;
	}

	public float getIncrementX() {
		return incrementX;
	}

	public float getIncrementY() {
		return incrementY;
	}

	public float getIncrementZ() {
		return incrementZ;
	}

	public int getGoalTicks() {
		return goalTicks;
	}

}
