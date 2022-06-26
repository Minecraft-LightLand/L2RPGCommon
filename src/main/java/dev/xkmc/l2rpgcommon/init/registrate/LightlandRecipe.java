package dev.xkmc.l2rpgcommon.init.registrate;

import dev.xkmc.l2library.base.recipe.AbstractShapedRecipe;
import dev.xkmc.l2library.base.recipe.BaseRecipe;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2rpgcommon.content.berserker.recipe.MedArmorRecipe;
import dev.xkmc.l2rpgcommon.content.magic.block.RitualCore;
import dev.xkmc.l2rpgcommon.content.magic.products.recipe.DefMagicRecipe;
import dev.xkmc.l2rpgcommon.content.magic.products.recipe.IMagicRecipe;
import dev.xkmc.l2rpgcommon.content.magic.ritual.*;
import dev.xkmc.l2rpgcommon.init.LightLand;
import net.minecraft.core.Registry;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import static dev.xkmc.l2rpgcommon.init.LightLand.REGISTRATE;

public class LightlandRecipe {

	public static final DeferredRegister<RecipeType<?>> RECIPE_TYPES = DeferredRegister.create(Registry.RECIPE_TYPE_REGISTRY, LightLand.MODID);
	public static RegistryObject<RecipeType<AbstractRitualRecipe<?>>> RT_RITUAL = REGISTRATE.recipe(RECIPE_TYPES, "ritual");

	public static final RegistryEntry<BaseRecipe.RecType<BasicRitualRecipe, AbstractRitualRecipe<?>, RitualCore.Inv>> RS_DEF =
			reg("ritual_default", () -> new BaseRecipe.RecType<>(BasicRitualRecipe.class, RT_RITUAL));
	public static final RegistryEntry<BaseRecipe.RecType<EnchantRitualRecipe, AbstractRitualRecipe<?>, RitualCore.Inv>> RS_ENCH =
			reg("ritual_enchantment", () -> new BaseRecipe.RecType<>(EnchantRitualRecipe.class, RT_RITUAL));

	public static final RegistryEntry<BaseRecipe.RecType<PotionBoostRecipe, AbstractRitualRecipe<?>, RitualCore.Inv>> RSP_BOOST =
			reg("ritual_potion_boost", () -> new BaseRecipe.RecType<>(PotionBoostRecipe.class, RT_RITUAL));
	public static final RegistryEntry<BaseRecipe.RecType<PotionCoreRecipe, AbstractRitualRecipe<?>, RitualCore.Inv>> RSP_CORE =
			reg("ritual_potion_core", () -> new BaseRecipe.RecType<>(PotionCoreRecipe.class, RT_RITUAL));
	public static final RegistryEntry<BaseRecipe.RecType<PotionSpellRecipe, AbstractRitualRecipe<?>, RitualCore.Inv>> RSP_SPELL =
			reg("ritual_potion_spell", () -> new BaseRecipe.RecType<>(PotionSpellRecipe.class, RT_RITUAL));
	public static final RegistryEntry<BaseRecipe.RecType<PotionModifyRecipe, AbstractRitualRecipe<?>, RitualCore.Inv>> RSP_MODIFY =
			reg("ritual_potion_modify", () -> new BaseRecipe.RecType<>(PotionModifyRecipe.class, RT_RITUAL));

	public static final RegistryEntry<AbstractShapedRecipe.Serializer<MedArmorRecipe>> RSC_MED_ARMOR =
			reg("medicine_armor", () -> new AbstractShapedRecipe.Serializer<>(MedArmorRecipe::new));

	public static void register(IEventBus bus) {
		RECIPE_TYPES.register(bus);
	}

	private static <A extends RecipeSerializer<?>> RegistryEntry<A> reg(String id, NonNullSupplier<A> sup) {
		return REGISTRATE.simple(id, ForgeRegistries.Keys.RECIPE_SERIALIZERS, sup);
	}

}
