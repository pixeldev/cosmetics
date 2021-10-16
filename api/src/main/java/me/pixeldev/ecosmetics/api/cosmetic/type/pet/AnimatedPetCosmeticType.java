package me.pixeldev.ecosmetics.api.cosmetic.type.pet;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.pet.animation.particle.PetParticleAnimationType;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.EquipmentFrame;

import org.bukkit.Material;

import xyz.xenondevs.particle.ParticleEffect;

import java.util.List;

public class AnimatedPetCosmeticType extends PetCosmeticType {

	private final List<EquipmentFrame> skinFrames;
	private final List<EquipmentFrame> handFrames;
	private final List<EquipmentFrame> chestFrames;
	private final List<EquipmentFrame> leggingsFrames;
	private final List<EquipmentFrame> bootsFrames;

	public AnimatedPetCosmeticType(String permission, String configurationIdentifier,
																 Material menuIcon, CosmeticCategory category,
																 boolean invisible, boolean arms,
																 ParticleEffect particleEffect,
																 PetParticleAnimationType animationType,
																 float incrementX, float incrementY, float incrementZ, int goalTicks,
																 List<EquipmentFrame> skinFrames, List<EquipmentFrame> handFrames,
																 List<EquipmentFrame> chestFrames, List<EquipmentFrame> leggingsFrames,
																 List<EquipmentFrame> bootsFrames) {
		super(
			permission, configurationIdentifier, menuIcon,
			category, null,
			null, invisible, arms,
			particleEffect, animationType, incrementX, incrementY,
			incrementZ, goalTicks
		);

		this.skinFrames = skinFrames;
		this.handFrames = handFrames;
		this.chestFrames = chestFrames;
		this.leggingsFrames = leggingsFrames;
		this.bootsFrames = bootsFrames;
	}

	public List<EquipmentFrame> getSkinFrames() {
		return skinFrames;
	}

	public List<EquipmentFrame> getHandFrames() {
		return handFrames;
	}

	public List<EquipmentFrame> getChestFrames() {
		return chestFrames;
	}

	public List<EquipmentFrame> getLeggingsFrames() {
		return leggingsFrames;
	}

	public List<EquipmentFrame> getBootsFrames() {
		return bootsFrames;
	}

}
