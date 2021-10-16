package me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.parse;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.ecosmetics.api.cosmetic.pet.equipment.frame.EquipmentFrame;

import java.util.List;

public interface EquipmentFrameParser {

	List<EquipmentFrame> parseFromSection(String typeKey, String sectionKey, int slot,
																				YamlConfigurationSection section,
																				StringBuilder details);

}
