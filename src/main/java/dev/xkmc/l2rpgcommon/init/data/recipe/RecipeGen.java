package dev.xkmc.l2rpgcommon.init.data.recipe;

import dev.xkmc.l2library.base.recipe.ResultTagShapedBuilder;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateRecipeProvider;
import dev.xkmc.l2library.repack.registrate.util.DataIngredient;
import dev.xkmc.l2library.repack.registrate.util.entry.BlockEntry;
import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2rpgcommon.init.LightLand;
import dev.xkmc.l2rpgcommon.init.data.templates.GenItem;
import dev.xkmc.l2rpgcommon.init.registrate.LLBlocks;
import dev.xkmc.l2rpgcommon.init.registrate.LLItems;
import net.minecraft.advancements.critereon.InventoryChangeTrigger;
import net.minecraft.data.recipes.ShapedRecipeBuilder;
import net.minecraft.data.recipes.ShapelessRecipeBuilder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.alchemy.PotionUtils;
import net.minecraft.world.level.ItemLike;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.List;
import java.util.function.BiFunction;

public class RecipeGen {

	private static String currentFolder = "";

	public static void genRecipe(RegistrateRecipeProvider pvd) {

		// gen tool and storage
		{
			currentFolder = "generated_tools/";
			for (int i = 0; i < GenItem.Mats.values().length; i++) {
				genTools(pvd, i, Items.STICK);
			}

			currentFolder = "storage/";
			for (int i = 0; i < GenItem.Mats.values().length; i++) {
				storage(pvd, LLItems.MAT_NUGGETS[i], LLItems.MAT_INGOTS[i], LLBlocks.GEN_BLOCK[i]);
			}

			storage(pvd, LLItems.ENC_GOLD_NUGGET, LLItems.ENC_GOLD_INGOT, LLBlocks.ENCHANT_GOLD_BLOCK);
			storage(pvd, LLItems.LEAD_NUGGET, LLItems.LEAD_INGOT, LLBlocks.LEAD_BLOCK);
			storage(pvd, LLItems.MAGICIUM_NUGGET, LLItems.MAGICIUM_INGOT, LLBlocks.MAGICIUM_BLOCK);
		}

		currentFolder = "magic_food/";
		// enchanted food
		{
			circle(pvd, Items.APPLE, LLItems.ENC_GOLD_INGOT.get(), LLItems.APPLE.get(), 1);
			circle(pvd, Items.CARROT, LLItems.ENC_GOLD_NUGGET.get(), LLItems.CARROT.get(), 1);
			circle(pvd, LLItems.ENC_GOLD_NUGGET.get(), Items.COOKIE, LLItems.COOKIE.get(), 8);
			unlock(pvd, new ShapedRecipeBuilder(LLItems.COOKIE.get(), 8)::unlockedBy,
					LLItems.ENC_GOLD_NUGGET.get()).pattern(" G ").pattern("ABA")
					.define('G', LLItems.ENC_GOLD_NUGGET.get())
					.define('A', Items.WHEAT).define('B', Items.COCOA_BEANS)
					.save(pvd, new ResourceLocation(LightLand.MODID, currentFolder + "enchanted_cookie_craft"));
			unlock(pvd, new ShapelessRecipeBuilder(LLItems.MELON.get(), 1)::unlockedBy,
					LLItems.ENC_GOLD_NUGGET.get()).requires(LLItems.ENC_GOLD_NUGGET.get())
					.requires(Items.MELON_SLICE).save(pvd, getID(LLItems.MELON.get()));
		}

		currentFolder = "quest_line/";
		// quest line
		{
			full(pvd, GenItem.Mats.STEEL.getIngot(), LLItems.KNIGHT_SCRAP.get(), Items.GOLD_NUGGET, GenItem.Mats.HEAVYSTEEL.getIngot(), 1);
			full(pvd, LLItems.ENC_GOLD_NUGGET.get(), LLBlocks.LAYLINE_HEAD.get().asItem(), GenItem.Mats.STEEL.getNugget(), GenItem.Mats.LAYLINE.getIngot(), 1);
			full(pvd, LLItems.ENC_GOLD_NUGGET.get(), LLBlocks.LAYROOT_HEAD.get().asItem(), Items.IRON_NUGGET, GenItem.Mats.LAYROOT.getIngot(), 1);
			cross(pvd, GenItem.Mats.LAYLINE.getIngot(), LLItems.OLDROOT.get(), GenItem.Mats.OLDROOT.getIngot(), 1);
			unlock(pvd, new ShapedRecipeBuilder(LLBlocks.SLIME_CARPET.get(), 8)::unlockedBy,
					LLItems.UNSTABLE_SLIME.get()).pattern("ABA")
					.define('A', Items.SLIME_BALL).define('B', LLItems.UNSTABLE_SLIME.get())
					.save(pvd, getID(LLBlocks.SLIME_CARPET.get().asItem()));
			unlock(pvd, new ShapedRecipeBuilder(LLBlocks.SLIME_VINE.get(), 1)::unlockedBy,
					LLItems.UNSTABLE_SLIME.get()).pattern("B B").pattern(" A ").pattern("B B")
					.define('A', Items.VINE).define('B', LLItems.UNSTABLE_SLIME.get())
					.save(pvd, getID(LLBlocks.SLIME_VINE.get().asItem()));
		}

		currentFolder = "magic_misc/";
		// magic misc
		{
			unlock(pvd, new ShapedRecipeBuilder(LLBlocks.B_RITUAL_CORE.get(), 1)::unlockedBy,
					LLItems.ENC_GOLD_INGOT.get()).pattern("BBB").pattern("DED").pattern("CCC")
					.define('E', ItemTags.PLANKS).define('D', Items.REDSTONE)
					.define('C', LLItems.ENC_GOLD_NUGGET.get())
					.define('B', LLItems.ENC_GOLD_INGOT.get())
					.save(pvd, getID(LLBlocks.B_RITUAL_CORE.get().asItem()));
			unlock(pvd, new ShapedRecipeBuilder(LLBlocks.B_RITUAL_SIDE.get(), 1)::unlockedBy,
					LLItems.ENC_GOLD_NUGGET.get()).pattern("BBB").pattern("DED").pattern("CCC")
					.define('E', ItemTags.PLANKS).define('D', Items.IRON_NUGGET)
					.define('C', LLItems.ENC_GOLD_NUGGET.get())
					.define('B', Items.IRON_INGOT)
					.save(pvd, getID(LLBlocks.B_RITUAL_SIDE.get().asItem()));
			unlock(pvd, new ShapedRecipeBuilder(LLItems.SPELL_CARD.get(), 64)::unlockedBy,
					LLItems.ENC_GOLD_NUGGET.get()).pattern("BAB").pattern("BAB").pattern("BAB")
					.define('B', Items.PAPER).define('A', LLItems.ENC_GOLD_NUGGET.get())
					.save(pvd, getID(LLItems.SPELL_CARD.get()));
			full(pvd, Items.LEATHER, Items.PAPER, LLItems.ENC_GOLD_NUGGET.get(), LLItems.SPELL_PARCHMENT.get(), 16);
			unlock(pvd, new ShapedRecipeBuilder(LLItems.SPELL_SCROLL.get(), 1)::unlockedBy,
					LLItems.ENC_GOLD_NUGGET.get()).pattern("CDC").pattern("ABA").pattern("CDC")
					.define('C', Items.PAPER).define('D', Items.STICK)
					.define('A', Items.LEATHER)
					.define('B', LLItems.ENC_GOLD_NUGGET.get())
					.save(pvd, getID(LLItems.SPELL_SCROLL.get()));
		}

		currentFolder = "medicine_effects/";
		// medicine leather
		{
			medicine(pvd, Items.BLUE_ORCHID, MobEffects.ABSORPTION, 0, 200);
			medicine(pvd, Items.AZURE_BLUET, MobEffects.DIG_SPEED, 2, 200);
			medicine(pvd, Items.LILY_OF_THE_VALLEY, MobEffects.HEAL, 0, 1);
			medicine(pvd, Items.OXEYE_DAISY, MobEffects.REGENERATION, 1, 200);
			medicine(pvd, Items.ALLIUM, MobEffects.DAMAGE_RESISTANCE, 1, 100);
			medicine(pvd, Items.DANDELION, MobEffects.SATURATION, 0, 10);
			medicine(pvd, Items.CORNFLOWER, MobEffects.MOVEMENT_SPEED, 1, 100);
			medicine(pvd, Items.POPPY, MobEffects.DAMAGE_BOOST, 1, 100);
		}

		currentFolder = "medicine_armors/";
		// medicine armor
		{
			unlock(pvd, new MedArmorBuilder(LLItems.KING_MED_LEATHER.get(), 8)::unlockedBy, LLItems.KING_LEATHER.get())
					.pattern("XXX").pattern("XOX").pattern("XXX")
					.define('X', LLItems.MEDICINE_LEATHER.get())
					.define('O', LLItems.KING_LEATHER.get())
					.save(pvd, getID(LLItems.KING_MED_LEATHER.get()));
			medArmor(pvd, LLItems.MEDICINE_LEATHER.get(), LLItems.MEDICINE_ARMOR);
			medArmor(pvd, LLItems.KING_MED_LEATHER.get(), LLItems.KING_MED_ARMOR);
		}


	}

	private static void cross(RegistrateRecipeProvider pvd, Item core, Item side, Item out, int count) {
		unlock(pvd, new ShapedRecipeBuilder(out, count)::unlockedBy, core)
				.pattern(" S ").pattern("SCS").pattern(" S ")
				.define('S', side).define('C', core)
				.save(pvd, getID(out));
	}

	private static void full(RegistrateRecipeProvider pvd, Item core, Item side, Item corner, Item out, int count) {
		unlock(pvd, new ShapedRecipeBuilder(out, count)::unlockedBy, core)
				.pattern("CSC").pattern("SAS").pattern("CSC")
				.define('A', core).define('S', side).define('C', corner)
				.save(pvd, getID(out));
	}

	private static ResourceLocation getID(Item item) {
		return new ResourceLocation(LightLand.MODID, currentFolder + ForgeRegistries.ITEMS.getKey(item).getPath());
	}

	private static void circle(RegistrateRecipeProvider pvd, Item core, Item side, Item out, int count) {
		unlock(pvd, new ShapedRecipeBuilder(out, count)::unlockedBy, core)
				.pattern("SSS").pattern("SAS").pattern("SSS")
				.define('A', core).define('S', side)
				.save(pvd, getID(out));
	}

	private static void storage(RegistrateRecipeProvider pvd, ItemEntry<?> nugget, ItemEntry<?> ingot, BlockEntry<?> block) {
		storage(pvd, nugget::get, ingot::get);
		storage(pvd, ingot::get, block::get);
	}

	public static void storage(RegistrateRecipeProvider pvd, NonNullSupplier<ItemLike> from, NonNullSupplier<ItemLike> to) {
		unlock(pvd, new ShapedRecipeBuilder(to.get(), 1)::unlockedBy, from.get().asItem())
				.pattern("XXX").pattern("XXX").pattern("XXX").define('X', from.get())
				.save(pvd, getID(to.get().asItem()) + "_storage");
		unlock(pvd, new ShapelessRecipeBuilder(from.get(), 9)::unlockedBy, to.get().asItem())
				.requires(to.get()).save(pvd, getID(to.get().asItem()) + "_unpack");
	}

	private static void genTools(RegistrateRecipeProvider pvd, int i, Item stick) {
		Item item = LLItems.MAT_INGOTS[i].get();
		ItemEntry<?>[] arr = LLItems.GEN_ITEM[i];
		unlock(pvd, new ShapedRecipeBuilder(arr[0].get(), 1)::unlockedBy, arr[0].get())
				.pattern("A A").pattern("A A").define('A', item).save(pvd, getID(arr[0].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[1].get(), 1)::unlockedBy, arr[1].get())
				.pattern("AAA").pattern("A A").pattern("A A").define('A', item).save(pvd, getID(arr[1].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[2].get(), 1)::unlockedBy, arr[2].get())
				.pattern("A A").pattern("AAA").pattern("AAA").define('A', item).save(pvd, getID(arr[2].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[3].get(), 1)::unlockedBy, arr[3].get())
				.pattern("AAA").pattern("A A").define('A', item).save(pvd, getID(arr[3].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[4].get(), 1)::unlockedBy, arr[4].get())
				.pattern("A").pattern("A").pattern("B").define('A', item).define('B', stick).save(pvd, getID(arr[4].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[5].get(), 1)::unlockedBy, arr[5].get())
				.pattern("AA").pattern("AB").pattern(" B").define('A', item).define('B', stick).save(pvd, getID(arr[5].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[6].get(), 1)::unlockedBy, arr[6].get())
				.pattern("A").pattern("B").pattern("B").define('A', item).define('B', stick).save(pvd, getID(arr[6].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[7].get(), 1)::unlockedBy, arr[7].get())
				.pattern("AAA").pattern(" B ").pattern(" B ").define('A', item).define('B', stick).save(pvd, getID(arr[7].get()));
		unlock(pvd, new ShapedRecipeBuilder(arr[8].get(), 1)::unlockedBy, arr[8].get())
				.pattern("AA").pattern(" B").pattern(" B").define('A', item).define('B', stick).save(pvd, getID(arr[8].get()));
	}

	private static <T> T unlock(RegistrateRecipeProvider pvd, BiFunction<String, InventoryChangeTrigger.TriggerInstance, T> func, Item item) {
		return func.apply("has_" + pvd.safeName(item), DataIngredient.items(item).getCritereon(pvd));
	}

	/* special */

	private static void medicine(RegistrateRecipeProvider pvd, Item flower, MobEffect eff, int amp, int duration) {
		Item item = LLItems.MEDICINE_LEATHER.get();
		ItemStack stack = new ItemStack(item);
		MobEffectInstance ins = new MobEffectInstance(eff, duration, amp);
		PotionUtils.setCustomEffects(stack, List.of(ins));
		unlock(pvd, new ResultTagShapedBuilder(stack)::unlockedBy, flower)
				.pattern("FVF").pattern("FLF").pattern("FVF").define('V', Items.VINE)
				.define('F', flower).define('L', Items.LEATHER)
				.save(pvd, new ResourceLocation(LightLand.MODID, currentFolder + ForgeRegistries.MOB_EFFECTS.getKey(eff).getPath()));
	}

	private static void medArmor(RegistrateRecipeProvider pvd, Item input, LLItems.ArmorItems<?> out) {
		unlock(pvd, new MedArmorBuilder(out.armors[0].get(), 1)::unlockedBy, input)
				.pattern("A A").pattern("A A").define('A', input).save(pvd, getID(out.armors[0].get()));
		unlock(pvd, new MedArmorBuilder(out.armors[1].get(), 1)::unlockedBy, input)
				.pattern("AAA").pattern("A A").pattern("A A").define('A', input).save(pvd, getID(out.armors[1].get()));
		unlock(pvd, new MedArmorBuilder(out.armors[2].get(), 1)::unlockedBy, input)
				.pattern("A A").pattern("AAA").pattern("AAA").define('A', input).save(pvd, getID(out.armors[2].get()));
		unlock(pvd, new MedArmorBuilder(out.armors[3].get(), 1)::unlockedBy, input)
				.pattern("AAA").pattern("A A").define('A', input).save(pvd, getID(out.armors[3].get()));
	}

}
