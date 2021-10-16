package me.pixeldev.ecosmetics.api.cosmetic.pet.equipment;

public class SingleEquipmentFrameStack
	implements EquipmentFrameStack {

	private final EquipmentFrame frame;

	public SingleEquipmentFrameStack(EquipmentFrame frame) {
		this.frame = frame;
	}

	@Override
	public EquipmentFrame next() {
		return frame;
	}

	@Override
	public EquipmentFrame current() {
		return frame;
	}

	@Override
	public int getCursor() {
		return 0;
	}

	@Override
	public int getSize() {
		return 0;
	}

	@Override
	public boolean hasNext() {
		return false;
	}

}
