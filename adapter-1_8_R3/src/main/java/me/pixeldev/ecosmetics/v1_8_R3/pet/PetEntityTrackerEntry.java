package me.pixeldev.ecosmetics.v1_8_R3.pet;

import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.v1_8_R3.track.AbstractEntityTrackerEntry;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.PacketPlayOutEntityDestroy;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class PetEntityTrackerEntry
	extends AbstractEntityTrackerEntry {

	private final PetCosmetic petCosmetic;

	public PetEntityTrackerEntry(PetCosmetic petCosmetic) {
		this.petCosmetic = petCosmetic;
	}

	@Override
	protected Location getLocation() {
		return petCosmetic.getActualLocation();
	}

	@Override
	protected void show(EntityPlayer player) {
		Player viewer = player.getBukkitEntity();
		PetUtils.spawn(petCosmetic, viewer);
	}

	@Override
	protected void hide(EntityPlayer player) {
		PetUtils.destroy(
			petCosmetic.getSpectators(),
			new PacketPlayOutEntityDestroy(petCosmetic.getEntityId()),
			player.getBukkitEntity()
		);
	}

}
