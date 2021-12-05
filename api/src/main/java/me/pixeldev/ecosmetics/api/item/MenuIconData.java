package me.pixeldev.ecosmetics.api.item;

import org.bukkit.Material;

public class MenuIconData {

	private final Material material;
	private final short data;

	public MenuIconData(Material material, short data) {
		this.material = material;
		this.data = data;
	}

	public Material getMaterial() {
		return material;
	}

	public short getData() {
		return data;
	}

}
