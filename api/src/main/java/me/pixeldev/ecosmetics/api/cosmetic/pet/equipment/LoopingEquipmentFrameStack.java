package me.pixeldev.ecosmetics.api.cosmetic.pet.equipment;

import java.util.List;

public class LoopingEquipmentFrameStack
	extends SimpleEquipmentFrameStack {

	public LoopingEquipmentFrameStack(List<EquipmentFrame> frames) {
		super(frames);
	}

	@Override
	public EquipmentFrame next() {
		EquipmentFrame next = super.internalNext();

		if (next == null) {
			cursor = 0;
			return super.internalNext();
		} else {
			return next;
		}
	}

}
