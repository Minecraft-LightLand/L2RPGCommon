package dev.xkmc.l2rpgcommon.init.registrate;

import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.repack.registrate.builders.ItemBuilder;
import dev.xkmc.l2library.repack.registrate.providers.DataGenContext;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateItemModelProvider;
import dev.xkmc.l2library.repack.registrate.util.entry.EntityEntry;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.l2rpgcommon.content.arcane.item.ArcaneAxe;
import dev.xkmc.l2rpgcommon.content.arcane.item.ArcaneSword;
import dev.xkmc.l2rpgcommon.content.berserker.item.MedicineArmor;
import dev.xkmc.l2rpgcommon.content.berserker.item.MedicineLeather;
import dev.xkmc.l2rpgcommon.content.common.gui.ability.ProfessionScreen;
import dev.xkmc.l2rpgcommon.content.common.item.api.Mat;
import dev.xkmc.l2rpgcommon.content.common.item.misc.ContainerBook;
import dev.xkmc.l2rpgcommon.content.common.item.misc.RecordPearl;
import dev.xkmc.l2rpgcommon.content.common.item.misc.ScreenBook;
import dev.xkmc.l2rpgcommon.content.magic.gui.tree.MagicTreeScreen;
import dev.xkmc.l2rpgcommon.content.magic.item.MagicScroll;
import dev.xkmc.l2rpgcommon.content.magic.item.MagicWand;
import dev.xkmc.l2rpgcommon.content.magic.item.ManaStorage;
import dev.xkmc.l2rpgcommon.content.magic.item.PotionCore;
import dev.xkmc.l2rpgcommon.content.questline.item.DispellWaterBottle;
import dev.xkmc.l2rpgcommon.content.questline.item.SlimeTentacleItem;
import dev.xkmc.l2rpgcommon.init.LightLand;
import dev.xkmc.l2rpgcommon.init.data.GenItem;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.food.FoodProperties;
import net.minecraft.world.food.Foods;
import net.minecraft.world.item.*;
import net.minecraftforge.client.model.generators.ItemModelBuilder;
import net.minecraftforge.common.ForgeSpawnEggItem;

import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.function.Supplier;

import static dev.xkmc.l2rpgcommon.init.LightLand.REGISTRATE;

@SuppressWarnings({"rawtypes", "unchecked", "unsafe"})
@MethodsReturnNonnullByDefault
public class LightlandItems {

	public static class Tab extends CreativeModeTab {

		private final Supplier<ItemEntry> icon;

		public Tab(String id, Supplier<ItemEntry> icon) {
			super(LightLand.MODID + "." + id);
			this.icon = icon;
		}

		@Override
		public ItemStack makeIcon() {
			return icon.get().asStack();
		}
	}

	public static final Tab TAB_MAIN = new Tab("material", () -> LightlandItems.DISPELL_DUST);
	public static final Tab TAB_PROF = new Tab("profession", () -> LightlandItems.MAGIC_WAND);
	public static final Tab TAB_QUEST = new Tab("generated", () -> LightlandItems.GEN_ITEM[0][0]);

	static {
		REGISTRATE.creativeModeTab(() -> TAB_MAIN);
	}

	// -------- common --------
	public static final ItemEntry<Item> STRONG_LEATHER;
	public static final ItemEntry<RecordPearl> RECORD_PEARL;
	public static final ItemEntry<ScreenBook> MAGIC_BOOK, ABILITY_BOOK;
	public static final ItemEntry<ContainerBook> ARCANE_INJECT_BOOK, DISENC_BOOK, SPCRAFT_BOOK;
	public static final ItemEntry<Item> LEAD_INGOT, LEAD_NUGGET, LAYLINE_ORB, CURSED_DROPLET, KNIGHT_SCRAP,
			DISPELL_DUST, OLDROOT, LAYLINE_HEART, ACID_SLIME, DIRTY_SLIME, UNSTABLE_SLIME, BOSS_SLIME,
			GOOD_SOUL, BAD_SOUL, HOLY_POWDER;
	public static final ItemEntry<Item>[] MAT_INGOTS, MAT_NUGGETS;

	public static final ItemEntry<DispellWaterBottle> CLEANSE_WATER_BOTTLE, HOLY_WATER_BOTTLE;
	public static final ItemEntry<SlimeTentacleItem> SLIME_TENTACLE;

	//TODO public static final FluidEntry<VirtualFluid> CLEANSE_WATER, HOLY_WATER;

	static {
		// Backpacks
		{
			RECORD_PEARL = REGISTRATE.item("record_pearl", p -> new RecordPearl(p.stacksTo(1))).defaultModel().defaultLang().register();
		}
		// Books
		{
			MAGIC_BOOK = REGISTRATE.item("magic_book", p -> new ScreenBook(p, () -> MagicTreeScreen::new)).defaultModel().defaultLang().register();
			ABILITY_BOOK = REGISTRATE.item("ability_book", p -> new ScreenBook(p, () -> ProfessionScreen::new)).defaultModel().defaultLang().register();
			ARCANE_INJECT_BOOK = REGISTRATE.item("arcane_inject_book", p -> new ContainerBook(p, () -> LightlandMenu.MT_ARCANE)).defaultModel().defaultLang().register();
			DISENC_BOOK = REGISTRATE.item("disenchant_book", p -> new ContainerBook(p, () -> LightlandMenu.MT_DISENC)).defaultModel().defaultLang().register();
			SPCRAFT_BOOK = REGISTRATE.item("spell_craft_book", p -> new ContainerBook(p, () -> LightlandMenu.MT_SPCRAFT)).defaultModel().defaultLang().register();
		}
		// materials
		{
			MAT_INGOTS = GenItem.genMats("_ingot");
			MAT_NUGGETS = GenItem.genMats("_nugget");

			LEAD_INGOT = simpleItem("lead_ingot");
			LEAD_NUGGET = simpleItem("lead_nugget");
			STRONG_LEATHER = simpleItem("strong_leather");
			LAYLINE_ORB = simpleItem("layline_orb");
			CURSED_DROPLET = simpleItem("cursed_droplet");
			KNIGHT_SCRAP = simpleItem("knight_scrap");
			DISPELL_DUST = simpleItem("dispell_dust");
			OLDROOT = simpleItem("oldroot");
			LAYLINE_HEART = simpleItem("layline_heart");
			ACID_SLIME = simpleItem("acid_slime");
			DIRTY_SLIME = simpleItem("dirty_slime");
			UNSTABLE_SLIME = simpleItem("unstable_slime");
			BOSS_SLIME = simpleItem("boss_slime");
			GOOD_SOUL = simpleItem("good_soul");
			BAD_SOUL = simpleItem("bad_soul");
			HOLY_POWDER = simpleItem("holy_powder");

			CLEANSE_WATER_BOTTLE = REGISTRATE.item("cleanse_water_bottle", p -> new DispellWaterBottle(
							p.craftRemainder(Items.GLASS_BOTTLE).food(new FoodProperties.Builder().nutrition(1).saturationMod(2).alwaysEat().build()).stacksTo(16)))
					.defaultModel().defaultLang().register();
			HOLY_WATER_BOTTLE = REGISTRATE.item("holy_water_bottle", p -> new DispellWaterBottle(
							p.craftRemainder(Items.GLASS_BOTTLE).food(new FoodProperties.Builder().nutrition(1).saturationMod(2).alwaysEat().build()).stacksTo(16)))
					.defaultModel().defaultLang().register();

			//TODO CLEANSE_WATER = REGISTRATE.virtualFluid("cleanse_water").defaultLang().register();
			//TODO HOLY_WATER = REGISTRATE.virtualFluid("holy_water").defaultLang().register();
			SLIME_TENTACLE = REGISTRATE.item("slime_tentacle", SlimeTentacleItem::new)
					.defaultModel().defaultLang().register();
		}
	}

	// -------- magic --------
	public static final int MANA = 256;

	public static final ItemEntry<ArcaneSword> ARCANE_SWORD_GILDED;
	public static final ItemEntry<ArcaneAxe> ARCANE_AXE_GILDED;
	public static final ItemEntry<MagicWand> MAGIC_WAND;
	public static final ItemEntry<PotionCore> POTION_CORE;
	public static final ItemEntry<MagicScroll> SPELL_CARD, SPELL_PARCHMENT, SPELL_SCROLL;
	public static final ItemEntry<ManaStorage> ENC_GOLD_NUGGET, ENC_GOLD_INGOT, COOKIE, MELON, CARROT, APPLE;
	public static final ItemEntry<Item> MAGICIUM_INGOT, MAGICIUM_NUGGET, ETHERNIUM_NUGGET, ETHERNIUM_INGOT;

	static {
		ARCANE_SWORD_GILDED = REGISTRATE.item("gilded_arcane_sword", p ->
						new ArcaneSword(Tiers.IRON, 5, -2.4f, p.stacksTo(1).setNoRepair(), 50))
				.model((ctx, pvd) -> pvd.handheld(ctx::getEntry)).defaultLang().register();
		ARCANE_AXE_GILDED = REGISTRATE.item("gilded_arcane_axe", p ->
						new ArcaneAxe(Tiers.IRON, 8, -3.1f, p.stacksTo(1).setNoRepair(), 50))
				.model((ctx, pvd) -> pvd.handheld(ctx::getEntry)).defaultLang().register();
		MAGIC_WAND = REGISTRATE.item("magic_wand", MagicWand::new)
				.defaultModel().defaultLang().register();
		POTION_CORE = REGISTRATE.item("potion_core", PotionCore::new)
				.defaultModel().defaultLang().register();
		SPELL_CARD = REGISTRATE.item("spell_card", p ->
						new MagicScroll(MagicScroll.ScrollType.CARD, p))
				.defaultModel().defaultLang().register();
		SPELL_PARCHMENT = REGISTRATE.item("spell_parchment", p ->
						new MagicScroll(MagicScroll.ScrollType.PARCHMENT, p))
				.defaultModel().defaultLang().register();
		SPELL_SCROLL = REGISTRATE.item("spell_scroll", p ->
						new MagicScroll(MagicScroll.ScrollType.SCROLL, p))
				.defaultModel().defaultLang().register();
		COOKIE = REGISTRATE.item("enchant_cookie", p -> new ManaStorage(p.food(Foods.COOKIE), Items.COOKIE, MANA / 8))
				.defaultModel().defaultLang().register();
		MELON = REGISTRATE.item("enchant_melon", p -> new ManaStorage(p.food(Foods.MELON_SLICE), Items.MELON_SLICE, MANA))
				.defaultModel().defaultLang().register();
		CARROT = REGISTRATE.item("enchant_carrot", p -> new ManaStorage(p.food(Foods.GOLDEN_CARROT), Items.GOLDEN_CARROT, MANA * 8))
				.defaultModel().defaultLang().register();
		APPLE = REGISTRATE.item("enchant_apple", p -> new ManaStorage(p.food(Foods.GOLDEN_APPLE), Items.GOLDEN_APPLE, MANA * 72))
				.defaultModel().defaultLang().register();
		ENC_GOLD_NUGGET = REGISTRATE.item("enchant_gold_nugget", p -> new ManaStorage(p, Items.GOLD_NUGGET, MANA))
				.defaultModel().defaultLang().register();
		ENC_GOLD_INGOT = REGISTRATE.item("enchant_gold_ingot", p -> new ManaStorage(p, Items.GOLD_INGOT, MANA * 9))
				.defaultModel().defaultLang().register();

		MAGICIUM_INGOT = simpleItem("magicium_ingot");
		MAGICIUM_NUGGET = simpleItem("magicium_nugget");
		ETHERNIUM_INGOT = simpleItem("ethernium_ingot");
		ETHERNIUM_NUGGET = simpleItem("ethernium_nugget");
	}

	// -------- questline--------

	public static final ItemEntry<Item> KING_LEATHER;

	static {
		KING_LEATHER = REGISTRATE.item("king_leather", Item::new)
				.defaultModel().defaultLang().register();
	}

	static {
		registerEgg("layline_zombie_spawn_egg", 0, 0, () -> LightlandEntities.ET_LAYLINE_ZOMBIE);
		registerEgg("layline_skeleton_spawn_egg", 0, 0, () -> LightlandEntities.ET_LAYLINE_SKELETON);
		registerEgg("cursed_knight_spawn_egg", 0, 0, () -> LightlandEntities.ET_CURSED_KNIGHT);
		registerEgg("cursed_archer_spawn_egg", 0, 0, () -> LightlandEntities.ET_CURSED_ARCHER);
		registerEgg("cursed_shield_spawn_egg", 0, 0, () -> LightlandEntities.ET_CURSED_SHIELD);
		registerEgg("potion_slime_spawn_egg", 0, 0, () -> LightlandEntities.ET_POTION_SLIME);
		registerEgg("stone_slime_spawn_egg", 0, 0, () -> LightlandEntities.ET_STONE_SLIME);
		registerEgg("vine_slime_spawn_egg", 0, 0, () -> LightlandEntities.ET_VINE_SLIME);
		registerEgg("carpet_slime_spawn_egg", 0, 0, () -> LightlandEntities.ET_CARPET_SLIME);
		registerEgg("boss_slime_spawn_egg", 0, 0, () -> LightlandEntities.ET_BOSS_SLIME);
	}

	private static void registerEgg(String id, int col_0, int col_1, Supplier<EntityEntry<? extends Mob>> sup) {
		REGISTRATE.item(id,
						p -> new ForgeSpawnEggItem(() -> sup.get().get(), col_0, col_1, p))
				.model((ctx, prov) -> prov.withExistingParent(ctx.getName(), new ResourceLocation("item/template_spawn_egg")))
				.defaultLang().register();
	}

	static {
		REGISTRATE.creativeModeTab(() -> TAB_PROF);
	}

	// -------- berserker --------
	public static final ItemEntry<MedicineLeather> MEDICINE_LEATHER, KING_MED_LEATHER;
	public static final ItemEntry<MedicineArmor>[] MEDICINE_ARMOR, KING_MED_ARMOR;

	static {
		MEDICINE_LEATHER = REGISTRATE.item("medicine_leather", p -> new MedicineLeather(14, p))
				.color(() -> () -> (stack, val) -> val > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack))
				.defaultModel().defaultLang().register();
		KING_MED_LEATHER = REGISTRATE.item("king_med_leather", p -> new MedicineLeather(100, p))
				.color(() -> () -> (stack, val) -> val > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack))
				.defaultModel().defaultLang().register();
		MEDICINE_ARMOR = genArmor("medicine_leather",
				(slot, p) -> new MedicineArmor(Mat.MEDICINE_LEATHER, slot, p), e -> e.model(LightlandItems::createDoubleLayerModel)
						.color(() -> () -> (stack, val) -> val > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack)));
		KING_MED_ARMOR = genArmor("king_leather",
				(slot, p) -> new MedicineArmor(Mat.KING_LEATHER, slot, p), e -> e.model(LightlandItems::createDoubleLayerModel)
						.color(() -> () -> (stack, val) -> val > 0 ? -1 : ((DyeableLeatherItem) stack.getItem()).getColor(stack)));
	}


	// -------- gen --------

	static {
		REGISTRATE.creativeModeTab(() -> TAB_QUEST);
	}

	public static final ItemEntry<Item>[][] GEN_ITEM;

	static {
		GEN_ITEM = GenItem.genItem();
	}

	public static void register() {
	}

	@SuppressWarnings({"rawtypes", "unsafe", "unchecked"})
	public static <T extends ArmorItem> ItemEntry<T>[] genArmor(String id, BiFunction<EquipmentSlot, Item.Properties, T> sup, Function<ItemBuilder<T, L2Registrate>, ItemBuilder<T, L2Registrate>> func) {
		ItemEntry[] ans = new ItemEntry[4];
		ans[0] = func.apply(REGISTRATE.item(id + "_helmet", p -> sup.apply(EquipmentSlot.HEAD, p))).defaultLang().register();
		ans[1] = func.apply(REGISTRATE.item(id + "_chestplate", p -> sup.apply(EquipmentSlot.CHEST, p))).defaultLang().register();
		ans[2] = func.apply(REGISTRATE.item(id + "_leggings", p -> sup.apply(EquipmentSlot.LEGS, p))).defaultLang().register();
		ans[3] = func.apply(REGISTRATE.item(id + "_boots", p -> sup.apply(EquipmentSlot.FEET, p))).defaultLang().register();
		return ans;
	}

	public static <T extends Item> void createDoubleLayerModel(DataGenContext<Item, T> ctx, RegistrateItemModelProvider pvd) {
		ItemModelBuilder builder = pvd.withExistingParent(ctx.getName(), "minecraft:generated");
		builder.texture("layer0", "item/" + ctx.getName());
		builder.texture("layer1", "item/" + ctx.getName() + "_overlay");
	}

	public static ItemEntry<Item> simpleItem(String id) {
		return REGISTRATE.item(id, Item::new).defaultModel().defaultLang().register();
	}

	public static class ArmorItems {



	}

}
