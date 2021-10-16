package me.pixeldev.ecosmetics.v1_8_R3.pet;

import me.pixeldev.alya.versions.v1_8_R3.packet.Packets;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticSpectators;
import me.pixeldev.ecosmetics.api.cosmetic.pet.AnimatedPetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.pet.PetCosmetic;
import me.pixeldev.ecosmetics.api.cosmetic.type.pet.AnimatedPetCosmeticType;
import me.pixeldev.ecosmetics.api.cosmetic.type.pet.PetCosmeticType;

import net.minecraft.server.v1_8_R3.*;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.inventory.CraftItemStack;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class PetUtils {

	private PetUtils() {

	}

	public static int generateId(PetCosmetic petCosmetic) {
		Location baseLocation = petCosmetic.getActualLocation();
		EntityArmorStand armorStand = new EntityArmorStand(
			((CraftWorld) baseLocation.getWorld()).getHandle()
		);

		return armorStand.getId();
	}

	public static void spawn(PetCosmetic petCosmetic, Player viewer) {
		CosmeticSpectators spectators = petCosmetic.getSpectators();

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
		armorStand.d(entityId);

		List<Packet<?>> packets = new ArrayList<>();

		packets.add(new PacketPlayOutSpawnEntityLiving(armorStand));

		if (type instanceof AnimatedPetCosmeticType) {
			packets.addAll(getCurrentEquipmentPackets(
				entityId, viewer, (AnimatedPetCosmetic) petCosmetic)
			);
		} else {
			packets.add(createEquipmentPacket(
				entityId, 4, petCosmetic.getSkin(viewer)
			));

			for (Map.Entry<Integer, ItemStack> equipmentEntry : type.getArmorContent().entrySet()) {
				packets.add(createEquipmentPacket(
					entityId, equipmentEntry.getKey(), equipmentEntry.getValue()
				));
			}
		}

		if (spectators.addSpectator(viewer)) {
			Packets.send(viewer, packets);
		}
	}

	public static void destroy(CosmeticSpectators spectators,
														 PacketPlayOutEntityDestroy entityDestroyPacket,
														 Player viewer) {
		if (spectators.removeSpectator(viewer)) {
			Packets.send(viewer, entityDestroyPacket);
		}
	}

	public static PacketPlayOutEntityEquipment createEquipmentPacket(int entityId, int equipmentSlot,
																																	 ItemStack itemStack) {
		return new PacketPlayOutEntityEquipment(
			entityId, equipmentSlot, CraftItemStack.asNMSCopy(itemStack)
		);
	}

	public static List<Packet<?>> getCurrentEquipmentPackets(int entityId, Player viewer,
																													 AnimatedPetCosmetic animatedPetCosmetic) {
		List<Packet<?>> packets = new ArrayList<>();
		packets.add(createEquipmentPacket(
			entityId, 0, animatedPetCosmetic.getHandFrameStack().current().getItem(viewer)
		));
		packets.add(createEquipmentPacket(
			entityId, 1, animatedPetCosmetic.getChestFrameStack().current().getItem(viewer)
		));
		packets.add(createEquipmentPacket(
			entityId, 2, animatedPetCosmetic.getLeggingsFrameStack().current().getItem(viewer)
		));
		packets.add(createEquipmentPacket(
			entityId, 3, animatedPetCosmetic.getBootsFrameStack().current().getItem(viewer)
		));
		packets.add(createEquipmentPacket(
			entityId, 4, animatedPetCosmetic.getSkinFrameStack().current().getItem(viewer)
		));
		return packets;
	}

}
