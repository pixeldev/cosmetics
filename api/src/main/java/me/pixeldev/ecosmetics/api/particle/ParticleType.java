package me.pixeldev.ecosmetics.api.particle;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.MaterialData;

/**
 * @author MrMicky
 */
public enum ParticleType {

	// 1.7+
	WATER_BUBBLE("bubble", "bubble"),
	WATER_SPLASH("splash", "splash"),
	WATER_WAKE("wake", "fishing"),
	SUSPENDED("suspended", "underwater"),
	SUSPENDED_DEPTH("depthsuspend", "underwater"),
	CRIT("crit", "crit"),
	CRIT_MAGIC("magicCrit", "enchanted_hit"),
	SPELL("spell", "effect"),
	SPELL_INSTANT("instantSpell", "instant_effect"),
	SPELL_MOB("mobSpell", "entity_effect"),
	SPELL_MOB_AMBIENT("mobSpellAmbient", "ambient_entity_effect"),
	SPELL_WITCH("witchMagic", "witch"),
	DRIP_WATER("dripWater", "dripping_water"),
	DRIP_LAVA("dripLava", "dripping_lava"),
	VILLAGER_ANGRY("angryVillager", "angry_villager"),
	VILLAGER_HAPPY("happyVillager", "happy_villager"),
	TOWN_AURA("townaura", "mycelium"),
	NOTE("note", "note"),
	PORTAL("portal", "portal"),
	ENCHANTMENT_TABLE("enchantmenttable", "enchant"),
	FLAME("flame", "flame"),
	LAVA("lava", "lava"),
	// FOOTSTEP("footstep", null),
	CLOUD("cloud", "cloud"),
	REDSTONE("reddust", "dust"),
	SNOWBALL("snowballpoof", "item_snowball"),
	SNOW_SHOVEL("snowshovel", "item_snowball"),
	SLIME("slime", "item_slime"),
	HEART("heart", "heart"),
	ITEM_CRACK("iconcrack", "item"),
	BLOCK_CRACK("blockcrack", "block"),
	BLOCK_DUST("blockdust", "block"),

	WATER_DROP("droplet", "rain", 8),
	// ITEM_TAKE("take", null, 8),

	// 1.9+
	DRAGON_BREATH("dragonbreath", "dragon_breath", 9),
	END_ROD("endRod", "end_rod", 9),
	DAMAGE_INDICATOR("damageIndicator", "damage_indicator", 9),
	SWEEP_ATTACK("sweepAttack", "sweep_attack", 9),

	// 1.10+
	FALLING_DUST("fallingdust", "falling_dust", 10),

	// 1.11+
	TOTEM("totem", "totem_of_undying", 11),
	SPIT("spit", "spit", 11),

	// 1.13+
	SQUID_INK(13),
	BUBBLE_POP(13),
	CURRENT_DOWN(13),
	BUBBLE_COLUMN_UP(13),
	NAUTILUS(13),
	DOLPHIN(13),

	// 1.14+
	SNEEZE(14),
	COMPOSTER(14),
	FLASH(14),
	FALLING_LAVA(14),
	LANDING_LAVA(14),
	FALLING_WATER(14),

	// 1.15+
	DRIPPING_HONEY(15),
	FALLING_HONEY(15),
	LANDING_HONEY(15),
	FALLING_NECTAR(15);

	private static final String SERVER_VERSION =
		Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

	private static final int SERVER_VERSION_ID;

	static {
		SERVER_VERSION_ID = Integer.parseInt(SERVER_VERSION.charAt(2) + "");
	}

	private final String legacyName;
	private final String name;
	private final int minimumVersion;

	// 1.7 particles
	ParticleType(String legacyName, String name) {
		this(legacyName, name, -1);
	}

	// 1.13+ particles
	ParticleType(int minimumVersion) {
		this.legacyName = null;
		this.name = name().toLowerCase();
		this.minimumVersion = minimumVersion;
	}

	// 1.8-1.12 particles
	ParticleType(String legacyName, String name, int minimumVersion) {
		this.legacyName = legacyName;
		this.name = name;
		this.minimumVersion = minimumVersion;
	}

	public boolean hasLegacyName() {
		return legacyName != null;
	}

	public String getLegacyName() {
		if (!hasLegacyName()) {
			throw new IllegalStateException("Particle " + name() + " don't have legacy name");
		}
		return legacyName;
	}

	public String getName() {
		return name;
	}

	public boolean isSupported() {
		return minimumVersion <= 0 || SERVER_VERSION_ID >= minimumVersion;
	}

	public Class<?> getDataType() {
		switch (this) {
			case ITEM_CRACK:
				return ItemStack.class;
			case BLOCK_CRACK:
			case BLOCK_DUST:
			case FALLING_DUST:
				return MaterialData.class;
			case REDSTONE:
				return Color.class;
			default:
				return Void.class;
		}
	}

	public static ParticleType getParticle(String particleName) {
		try {
			return ParticleType.valueOf(particleName.toUpperCase());
		} catch (IllegalArgumentException e) {
			for (ParticleType particle : values()) {
				if (particle.getName().equalsIgnoreCase(particleName)) {
					return particle;
				}

				if (particle.hasLegacyName() && particle.getLegacyName().equalsIgnoreCase(particleName)) {
					return particle;
				}
			}
		}
		return null;
	}

}