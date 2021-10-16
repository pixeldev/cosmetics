package me.pixeldev.ecosmetics.plugin.cosmetic.pet;

import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.frame.EquipmentFrame;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.parse.EquipmentFrameStackParser;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.stack.EquipmentFrameStack;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.stack.LoopingEquipmentFrameStack;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.stack.SingleEquipmentFrameStack;

import java.util.List;

public class SimpleEquipmentFrameStackParser
	implements EquipmentFrameStackParser {

	@Override
	public EquipmentFrameStack parse(List<EquipmentFrame> frames) {
		if (frames.size() == 1) {
			return new SingleEquipmentFrameStack(frames.get(0));
		} else {
			return new LoopingEquipmentFrameStack(frames);
		}
	}

}
