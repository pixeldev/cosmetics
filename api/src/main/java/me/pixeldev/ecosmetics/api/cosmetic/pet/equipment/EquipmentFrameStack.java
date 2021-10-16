package me.pixeldev.ecosmetics.api.cosmetic.pet.equipment;

public interface EquipmentFrameStack {

	EquipmentFrame next();

	EquipmentFrame current();

	int getCursor();

	int getSize();

	boolean hasNext();

}
