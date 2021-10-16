package me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.stack;

import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.frame.EquipmentFrame;

public interface EquipmentFrameStack {

	EquipmentFrame next();

	EquipmentFrame current();

	int getCursor();

	int getSize();

	boolean hasNext();

}
