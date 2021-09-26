package me.pixeldev.ecosmetics.v1_8_R3.pet;

import me.pixeldev.alya.versions.v1_8_R3.packet.Packets;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;

import net.minecraft.server.v1_8_R3.*;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import javax.inject.Inject;
import java.io.IOException;

public class SimplePetEntityHandler implements PetEntityHandler {

	@Inject private PetEntityTrackerManager trackerManager;

	@Override
	public void create(PetCosmetic petCosmetic) {
		EntityTrackerEntry entry = new PetEntityTrackerEntry(petCosmetic);
		petCosmetic.setEntityId(PetUtils.generateId(petCosmetic));
		trackerManager.bindEntry(petCosmetic, entry);
	}

	@Override
	public void spawn(Player viewer, PetCosmetic petCosmetic) {
		PetUtils.spawn(petCosmetic, viewer);
	}

	@Override
	public void displayAnimation(PetCosmetic petCosmetic) {
		Location actualLocation = petCosmetic.getActualLocation();

		sendPacketTeleport(
			petCosmetic.getSpectators(), petCosmetic.getEntityId(),
			actualLocation.getX(), actualLocation.getY(), actualLocation.getZ(),
			actualLocation.getYaw(), actualLocation.getPitch()
		);
	}

	@Override
	public void destroy(Player viewer, PetCosmetic petCosmetic) {
		PetUtils.destroy(
			petCosmetic.getSpectators(),
			new PacketPlayOutEntityDestroy(petCosmetic.getEntityId()),
			viewer
		);
	}

	@Override
	public void destroy(PetCosmetic petCosmetic) {
		trackerManager.unbindEntry(petCosmetic);
	}

	private void sendPacketTeleport(PetCosmetic.Spectators spectators, int entityId,
																	double x, double y, double z,
																	float yaw, float pitch) {
		PacketPlayOutEntityTeleport teleportPacket = new PacketPlayOutEntityTeleport();

		try {
			teleportPacket.a(new PacketDataSerializer(null) {
				private int ordinate;
				private int headOrdinate;

				@Override
				public int e() {
					return entityId;
				}

				@Override
				public int readInt() {
					++ordinate;

					if (ordinate == 1) {
						return MathHelper.floor(x * 32);
					} else if (ordinate == 2) {
						return MathHelper.floor(y * 32);
					} else if (ordinate == 3) {
						return MathHelper.floor(z * 32);
					} else {
						return 0;
					}
				}

				@Override
				public byte readByte() {
					++headOrdinate;

					if (headOrdinate == 1) {
						return (byte) (yaw * 256F / 360F);
					} else if (headOrdinate == 2) {
						return (byte) (pitch * 256F / 360F);
					} else {
						return 0;
					}
				}

				@Override
				public boolean readBoolean() {
					return false;
				}
			});
		} catch (IOException ignored) {
		}

		spectators.consumeAsPlayers(player ->
			Packets.send(player, teleportPacket)
		);
	}

}
