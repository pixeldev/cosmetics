package me.pixeldev.ecosmetics.api.cosmetic.type.creator;

import me.pixeldev.alya.api.yaml.YamlConfigurationSection;
import me.pixeldev.ecosmetics.api.cosmetic.type.CosmeticType;

public interface CosmeticTypeCreator {

	CosmeticType createFromSection(YamlConfigurationSection section);

}