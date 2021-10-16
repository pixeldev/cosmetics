package me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.parse;

import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.frame.EquipmentFrame;

import java.util.List;

public interface RainbowEquipmentParser {

	List<EquipmentFrame> parse(String rainbowKey, String typeKey,
														 String sectionKey, int slot,
														 StringBuilder details);

}
