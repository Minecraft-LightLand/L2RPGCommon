package dev.xkmc.l2rpgcommon.init.special;

import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.repack.registrate.util.entry.RegistryEntry;
import dev.xkmc.l2library.repack.registrate.util.nullness.NonNullSupplier;
import dev.xkmc.l2rpgcommon.content.arcane.internal.Arcane;
import dev.xkmc.l2rpgcommon.content.arcane.internal.ArcaneType;
import dev.xkmc.l2rpgcommon.content.magic.products.MagicElement;
import dev.xkmc.l2rpgcommon.content.magic.products.MagicProductType;
import dev.xkmc.l2rpgcommon.content.magic.spell.internal.Spell;
import dev.xkmc.l2rpgcommon.content.profession.Profession;
import dev.xkmc.l2rpgcommon.content.profession.prof.*;
import dev.xkmc.l2rpgcommon.content.skill.internal.Skill;

import static dev.xkmc.l2rpgcommon.init.LightLand.REGISTRATE;

public class LightLandRegistry {

	public static final L2Registrate.RegistryInstance<MagicElement> ELEMENT = REGISTRATE.newRegistry("magic_element", MagicElement.class);
	public static final L2Registrate.RegistryInstance<MagicProductType<?, ?>> PRODUCT_TYPE = REGISTRATE.newRegistry("magic_product_type", MagicProductType.class);
	public static final L2Registrate.RegistryInstance<ArcaneType> ARCANE_TYPE = REGISTRATE.newRegistry("arcane_type", ArcaneType.class);
	public static final L2Registrate.RegistryInstance<Arcane> ARCANE = REGISTRATE.newRegistry("arcane", Arcane.class);
	public static final L2Registrate.RegistryInstance<Spell<?, ?>> SPELL = REGISTRATE.newRegistry("spell", Spell.class);
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

	private static <V extends Profession> RegistryEntry<V> genProf(String name, NonNullSupplier<V> v) {
		return REGISTRATE.generic(PROFESSION, name, v).defaultLang().register();
	}

	public static void register(){

	}

}
