package me.pixeldev.ecosmetics.plugin.cosmetic.permission;

import me.pixeldev.alya.api.yaml.YamlFileConfiguration;
import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.permission.CosmeticPermissionFormatter;

import javax.inject.Inject;
import java.util.Locale;

public class SimpleCosmeticPermissionFormatter implements CosmeticPermissionFormatter {

	@Inject private YamlFileConfiguration configuration;

	@Override
	public String format(CosmeticCategory category, String typeKey) {
		return configuration.getString("cosmetics.permission-format")
			.replace("%category%", category.name().toLowerCase(Locale.ROOT))
			.replace("%identifier%", typeKey);
	}

}