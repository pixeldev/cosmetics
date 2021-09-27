package me.pixeldev.ecosmetics.api.cosmetic.animation;

public abstract class DelayableCosmeticAnimation
	implements CosmeticAnimation {

	private final int goalTicks;

	private int currentTicks;

	public DelayableCosmeticAnimation(int goalTicks) {
		this.goalTicks = goalTicks;
	}

	protected boolean incrementTicks() {
		if (currentTicks >= goalTicks) {
			currentTicks = 0;
			return true;
		}

		currentTicks++;
		return false;
	}

}
