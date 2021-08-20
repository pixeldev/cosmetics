package me.pixeldev.ecosmetics.v1_8_R3;

import me.pixeldev.alya.abstraction.packet.PacketSender;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityHandler;
import me.pixeldev.ecosmetics.api.cosmetic.pet.entity.PetEntityManager;
import me.pixeldev.ecosmetics.api.cosmetic.type.PetCosmeticType;
import me.pixeldev.ecosmetics.api.user.CosmeticUserService;

import net.minecraft.server.v1_8_R3.*;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import javax.inject.Inject;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SimplePetEntityHandler implements PetEntityHandler {

	@Inject private PacketSender packetSender;
	@Inject private PetEntityManager petEntityManager;
	@Inject private CosmeticUserService userService;

	@Override
	public void teleport(PetCosmetic petCosmetic, Location location) {
		sendPacketTeleport(
			petCosmetic.getSpectators(), petCosmetic.getEntityId(),
			location.getX(), location.getY(), location.getZ(),
			location.getYaw(), location.getPitch()
		);
	}

	@Override
	public void spawn(Player viewer, PetCosmetic petCosmetic) {
		PetCosmetic.Spectators spectators = petCosmetic.getSpectators();

		if (spectators.isSpectating(viewer)) {
			return;
		}

		Location baseLocation = petCosmetic.getActualLocation();
		EntityArmorStand armorStand = new EntityArmorStand(
			((CraftWorld) baseLocation.getWorld()).getHandle()
		);

		armorStand.setLocation(
			baseLocation.getX(), baseLocation.getY(), baseLocation.getZ(),
			baseLocation.getYaw(), baseLocation.getPitch()
		);

		PetCosmeticType type = petCosmetic.getType();

		armorStand.setGravity(false);
		armorStand.setInvisible(type.isInvisible());
		armorStand.setArms(type.isArms());
		armorStand.setBasePlate(true);
		armorStand.setSmall(true);

		int entityId = petCosmetic.getEntityId();

		if (entityId == -1) {
			int standId = armorStand.getId();
			entityId = standId;
			petCosmetic.setEntityId(standId);
			petEntityManager.addPetEntity(petCosmetic.getOwner(), entityId);
		} else {
			armorStand.d(entityId);
		}

		List<Object> packets = new ArrayList<>();

		packets.add(new PacketPlayOutSpawnEntityLiving(armorStand));

		packets.add(createEquipmentPacket(
			entityId, 4, type.getSkinProvider().getSkin(viewer)
		));

		Material materialInHand = type.getMaterialInHand();

		if (materialInHand != null) {
			packets.add(createEquipmentPacket(
				entityId, 0, new ItemStack(materialInHand)
			));
		}

		for (Map.Entry<Integer, ItemStack> equipmentEntry : type.getArmorContent().entrySet()) {
			packets.add(createEquipmentPacket(
				entityId, equipmentEntry.getKey(), equipmentEntry.getValue()
			));
		}

		if (spectators.addSpectator(viewer)) {
			packetSender.sendPackets(viewer, packets.toArray(new Object[0]));
			userService.getUserByPlayerSync(viewer)
				.ifPresent(user -> user.addRenderedMiniature(petCosmetic));
		}
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
		destroy(
			petCosmetic.getSpectators(), petCosmetic,
			new PacketPlayOutEntityDestroy(petCosmetic.getEntityId()),
			viewer
		);
	}

	@Override
	public void destroy(PetCosmetic petCosmetic) {
		PetCosmetic.Spectators spectators = petCosmetic.getSpectators();
		PacketPlayOutEntityDestroy entityDestroyPacket = new PacketPlayOutEntityDestroy(
			petCosmetic.getEntityId()
		);

		spectators.consumeAsPlayers(player ->
			destroy(spectators, petCosmetic, entityDestroyPacket, player)
		);
	}

	private void destroy(PetCosmetic.Spectators spectators,
											 PetCosmetic petCosmetic,
											 PacketPlayOutEntityDestroy entityDestroyPacket,
											 Player viewer) {
		if (spectators.removeSpectator(viewer)) {
			packetSender.sendPacket(viewer, entityDestroyPacket);
			userService.getUserByPlayerSync(viewer)
				.ifPresent(user -> user.removeRenderedMiniature(petCosmetic));
		}
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
		} catch (IOException ignored) {}

		spectators.consumeAsPlayers(player ->
			packetSender.sendPacket(player, teleportPacket)
		);
	}

	private PacketPlayOutEntityEquipment createEquipmentPacket(int entityId, int equipmentSlot,
																														 ItemStack itemStack) {
		return new PacketPlayOutEntityEquipment(
			entityId, equipmentSlot, CraftItemStack.asNMSCopy(itemStack)
		);
	}
}