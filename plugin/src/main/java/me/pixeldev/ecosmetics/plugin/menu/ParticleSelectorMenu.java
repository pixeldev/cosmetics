package me.pixeldev.ecosmetics.plugin.menu;

import me.pixeldev.ecosmetics.api.cosmetic.CosmeticCategory;
import me.pixeldev.ecosmetics.api.cosmetic.type.EffectCosmeticType;
import me.pixeldev.ecosmetics.api.user.CosmeticUser;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import team.unnamed.gui.abstraction.item.ItemClickable;
import team.unnamed.gui.core.gui.type.GUIBuilder;

import xyz.xenondevs.particle.ParticleEffect;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

public class ParticleSelectorMenu extends CosmeticsGUICreator {

	private static final Map<ParticleEffect, Material> MATERIALS_BY_EFFECT = new HashMap<>();

	public static void load() {
		MATERIALS_BY_EFFECT.put(ParticleEffect.FLAME, Material.BLAZE_POWDER);
		MATERIALS_BY_EFFECT.put(ParticleEffect.CLOUD, Material.WEB);
		MATERIALS_BY_EFFECT.put(ParticleEffect.BLOCK_CRACK, Material.ARMOR_STAND);
		MATERIALS_BY_EFFECT.put(ParticleEffect.CRIT, Material.IRON_SWORD);
		MATERIALS_BY_EFFECT.put(ParticleEffect.CRIT_MAGIC, Material.DIAMOND_SWORD);
		MATERIALS_BY_EFFECT.put(ParticleEffect.DRIP_LAVA, Material.LAVA_BUCKET);
		MATERIALS_BY_EFFECT.put(ParticleEffect.DRIP_WATER, Material.WATER_BUCKET);
		MATERIALS_BY_EFFECT.put(ParticleEffect.FIREWORKS_SPARK, Material.FIREWORK);
		MATERIALS_BY_EFFECT.put(ParticleEffect.FOOTSTEP, Material.IRON_BOOTS);
		MATERIALS_BY_EFFECT.put(ParticleEffect.HEART, Material.FLOWER_POT_ITEM);
		MATERIALS_BY_EFFECT.put(ParticleEffect.ITEM_CRACK, Material.GRASS);
		MATERIALS_BY_EFFECT.put(ParticleEffect.NOTE, Material.NOTE_BLOCK);
		MATERIALS_BY_EFFECT.put(ParticleEffect.REDSTONE, Material.REDSTONE);
		MATERIALS_BY_EFFECT.put(ParticleEffect.SLIME, Material.SLIME_BALL);
		MATERIALS_BY_EFFECT.put(ParticleEffect.SNOWBALL, Material.SNOW_BALL);
		MATERIALS_BY_EFFECT.put(ParticleEffect.SPELL, Material.EXP_BOTTLE);
		MATERIALS_BY_EFFECT.put(ParticleEffect.SPELL_INSTANT, Material.POTION);
		MATERIALS_BY_EFFECT.put(ParticleEffect.SPELL_MOB, Material.MONSTER_EGG);
		MATERIALS_BY_EFFECT.put(ParticleEffect.SPELL_MOB_AMBIENT, Material.EGG);
		MATERIALS_BY_EFFECT.put(ParticleEffect.SPELL_WITCH, Material.CAULDRON_ITEM);
		MATERIALS_BY_EFFECT.put(ParticleEffect.TOWN_AURA, Material.MYCEL);
		MATERIALS_BY_EFFECT.put(ParticleEffect.VILLAGER_HAPPY, Material.EMERALD);
	}

	public ParticleSelectorMenu() {
		super("particle-selector");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Inventory create(Player issuer, Object... extraData) {
		CosmeticUser cosmeticUser = (CosmeticUser) extraData[0];
		EffectCosmeticType cosmeticType = (EffectCosmeticType) extraData[1];
		List<EffectCosmeticType.Data> customData
			= (List<EffectCosmeticType.Data>) extraData[2];

		List<EffectCosmeticType.Data> animationTypes = cosmeticType.getAnimationTypes();
		int maxSize = animationTypes.size();
		int animationIndex = (int) extraData[3];
		EffectCosmeticType.Data data = animationTypes.get(animationIndex);

		return GUIBuilder.builderPaginated(ParticleEffect.class, getTitle(
				issuer, data.getAnimationType()
			))
			.setBounds(10, 35)
			.setEntities(MATERIALS_BY_EFFECT.keySet())
			.setItemParser(particleEffect -> {
				Material icon = MATERIALS_BY_EFFECT.get(particleEffect);
				Predicate<InventoryClickEvent> action;

				if (animationIndex + 1 < maxSize) {
					action = event -> {
						customData.add(data.toCustomData(particleEffect));
						issuer.openInventory(create(
							issuer, cosmeticUser, cosmeticType,
							customData, animationIndex + 1
						));

						return true;
					};
				} else {
					action = event -> {
						issuer.closeInventory();
						customData.add(data.toCustomData(particleEffect));
						cosmeticHandler.assignAndEquipCosmetic(
							issuer, cosmeticUser, CosmeticCategory.EFFECTS,
							cosmeticType.cloneWithOtherData(customData)
						);

						return true;
					};
				}

				return ItemClickable.builder()
					.setAction(action)
					.setItemStack(createItem(
						issuer, icon, "particles", particleEffect
					).build())
					.build();
			})
			.setItemsPerRow(7)
			.setNextPageItem(page -> createNextPageItem(issuer, page))
			.setPreviousPageItem(page -> createPreviousPageItem(issuer, page))
			.addItem(createBackToMainMenuItem(issuer, extraData))
			.build();
	}

}
