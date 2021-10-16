package me.pixeldev.ecosmetics.api.cosmetic.pet.equipment;

import java.util.List;

public class SimpleEquipmentFrameStack implements EquipmentFrameStack {

	private final List<EquipmentFrame> frames;

	protected int cursor;
	protected EquipmentFrame currentFrame;
	protected int currentDelay;

	public SimpleEquipmentFrameStack(List<EquipmentFrame> frames) {
		this.frames = frames;
	}

	@Override
	public EquipmentFrame next() {
		return internalNext();
	}

	@Override
	public EquipmentFrame current() {
		return currentFrame;
	}

	@Override
	public int getCursor() {
		return cursor;
	}

	@Override
	public int getSize() {
		return frames.size();
	}

	@Override
	public boolean hasNext() {
		if (currentDelay > 0 && currentFrame != null) {
			currentDelay--;
			return false;
		}

		return cursor >= 0 && cursor <= getSize();
	}

	protected EquipmentFrame internalNext() {
		if (cursor >= getSize()) {
			return null;
		}

		currentFrame = frames.get(cursor++);
		currentDelay = currentFrame.getDelay();

		return currentFrame;
	}

}
