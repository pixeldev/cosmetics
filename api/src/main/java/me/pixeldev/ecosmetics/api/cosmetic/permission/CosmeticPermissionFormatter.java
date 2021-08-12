package me.pixeldev.ecosmetics.api.cosmetic.permission;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;

public interface CosmeticPermissionFormatter {

	/**
	 * @param category Category of the cosmetic.
	 * @param typeKey Type of the cosmetic.
	 * @return A formatted string with permission format set in the configuration.
	 */
	String format(CosmeticCategory category, String typeKey);

}