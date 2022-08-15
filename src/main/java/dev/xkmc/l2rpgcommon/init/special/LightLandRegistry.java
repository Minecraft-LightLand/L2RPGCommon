package dev.xkmc.l2rpgcommon.init.special;

import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2rpgcommon.content.profession.Profession;
import dev.xkmc.l2rpgcommon.content.profession.prof.*;
import dev.xkmc.l2rpgcommon.content.skill.internal.Skill;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.RangedAttribute;
import net.minecraftforge.registries.ForgeRegistries;

import static dev.xkmc.l2rpgcommon.init.LightLand.REGISTRATE;

public class LightLandRegistry {

	public static final L2Registrate.RegistryInstance<Profession> PROFESSION = REGISTRATE.newRegistry("profession", Profession.class);
	public static final L2Registrate.RegistryInstance<Skill<?, ?>> SKILL = REGISTRATE.newRegistry("skill", Skill.class);

	public static final RegistryEntry<ArcaneProfession> PROF_ARCANE = genProf("arcane", ArcaneProfession::new);
	public static final RegistryEntry<MagicianProfession> PROF_MAGIC = genProf("magician", MagicianProfession::new);
	public static final RegistryEntry<SpellCasterProfession> PROF_SPELL = genProf("spell_caster", SpellCasterProfession::new);
	public static final RegistryEntry<KnightProfession> PROF_KNIGHT = genProf("knight", KnightProfession::new);
	public static final RegistryEntry<ShielderProfession> PROF_SHIELDER = genProf("shielder", ShielderProfession::new);
	public static final RegistryEntry<BerserkerProfession> PROF_BURSERKER = genProf("berserker", BerserkerProfession::new);
	public static final RegistryEntry<ArcherProfession> PROF_ARCHER = genProf("archer", ArcherProfession::new);
	public static final RegistryEntry<HunterProfession> PROF_HUNTER = genProf("hunter", HunterProfession::new);
	public static final RegistryEntry<AlchemistProfession> PROF_ALCHEM = genProf("alchemist", AlchemistProfession::new);
	public static final RegistryEntry<ChemistProfession> PROF_CHEM = genProf("chemist", ChemistProfession::new);
	public static final RegistryEntry<TidecallerProfession> PROF_TIDE = genProf("tidecaller", TidecallerProfession::new);
	public static final RegistryEntry<AssassinProfession> PROF_ASSASSIN = genProf("assassin", AssassinProfession::new);

	public static RegistryEntry<Attribute> SPELL_BOOST = REGISTRATE.simple("spell_boost", ForgeRegistries.ATTRIBUTES.getRegistryKey(), () -> new RangedAttribute("attribute.name.spell_boost", 1, 0, 1000).setSyncable(true));
	public static RegistryEntry<Attribute> MAX_MANA = REGISTRATE.simple("max_mana", ForgeRegistries.ATTRIBUTES.getRegistryKey(), () -> new RangedAttribute("attribute.name.max_mana", 0, 0, 1000000).setSyncable(true));
	public static RegistryEntry<Attribute> MAX_SPELL_LOAD = REGISTRATE.simple("max_spell_load", ForgeRegistries.ATTRIBUTES.getRegistryKey(), () -> new RangedAttribute("attribute.name.max_spell_load", 100, 100, 1000000).setSyncable(true));
	public static RegistryEntry<Attribute> MANA_RESTORE = REGISTRATE.simple("mana_restore", ForgeRegistries.ATTRIBUTES.getRegistryKey(), () -> new RangedAttribute("attribute.name.mana_restore", 0.01, 0, 1).setSyncable(true));

	private static <V extends Profession> RegistryEntry<V> genProf(String name, NonNullSupplier<V> v) {
		return REGISTRATE.generic(PROFESSION, name, v).defaultLang().register();
	}

	public static void register() {

	}

}
