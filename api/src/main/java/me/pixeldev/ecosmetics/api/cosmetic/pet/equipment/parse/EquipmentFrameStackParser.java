package me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.parse;

import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.frame.EquipmentFrame;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.stack.EquipmentFrameStack;

import java.util.List;

public interface EquipmentFrameStackParser {

	EquipmentFrameStack parse(List<EquipmentFrame> frames);

}
