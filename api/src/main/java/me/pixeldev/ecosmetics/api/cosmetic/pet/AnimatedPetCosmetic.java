package me.pixeldev.ecosmetics.api.cosmetic.pet;

import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.stack.EquipmentFrameStack;
import me.pixeldev.ecosmetics.api.cosmetic.type.pet.PetCosmeticType;

import org.bukkit.entity.Player;

import java.lang.ref.WeakReference;

public class AnimatedPetCosmetic extends PetCosmetic {

	private final EquipmentFrameStack skinFrameStack;
	private final EquipmentFrameStack handFrameStack;
	private final EquipmentFrameStack chestFrameStack;
	private final EquipmentFrameStack leggingsFrameStack;
	private final EquipmentFrameStack bootsFrameStack;

	public AnimatedPetCosmetic(WeakReference<Player> ownerReference,
														 PetCosmeticType type,
														 EquipmentFrameStack skinFrameStack,
														 EquipmentFrameStack handFrameStack,
														 EquipmentFrameStack chestFrameStack,
														 EquipmentFrameStack leggingsFrameStack,
														 EquipmentFrameStack bootsFrameStack) {
		super(ownerReference, type);
		this.skinFrameStack = skinFrameStack;
		this.handFrameStack = handFrameStack;
		this.chestFrameStack = chestFrameStack;
		this.leggingsFrameStack = leggingsFrameStack;
		this.bootsFrameStack = bootsFrameStack;
	}

	public EquipmentFrameStack getSkinFrameStack() {
		return skinFrameStack;
	}

	public EquipmentFrameStack getHandFrameStack() {
		return handFrameStack;
	}

	public EquipmentFrameStack getChestFrameStack() {
		return chestFrameStack;
	}

	public EquipmentFrameStack getLeggingsFrameStack() {
		return leggingsFrameStack;
	}

	public EquipmentFrameStack getBootsFrameStack() {
		return bootsFrameStack;
	}

}
