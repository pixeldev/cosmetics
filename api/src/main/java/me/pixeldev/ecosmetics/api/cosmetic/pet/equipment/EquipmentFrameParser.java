package me.pixeldev.ecosmetics.api.cosmetic.pet.equipment;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;

import java.util.List;

public interface EquipmentFrameParser {

	List<EquipmentFrame> parseFromSection(String sectionKey,
																				YamlConfigurationSection section,
																				StringBuilder details);

}
