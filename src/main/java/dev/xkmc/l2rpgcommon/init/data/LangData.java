package dev.xkmc.l2rpgcommon.init.data;

import dev.xkmc.l2library.idea.magic.HexDirection;
import dev.xkmc.l2library.repack.registrate.providers.RegistrateLangProvider;
import dev.xkmc.l2magic.content.arcane.internal.ArcaneType;
import dev.xkmc.l2rpgcommon.content.common.gui.AbilityScreen;
import dev.xkmc.l2rpgcommon.init.LightLand;
import net.minecraft.client.KeyMapping;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.contents.TranslatableContents;
import org.lwjgl.glfw.GLFW;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiConsumer;

public class LangData {

	public enum IDS {
		INVALID_ID("argument.invalid_id", 0),
		PROF_EXIST("chat.profession_already_exist", 0),
		LOCKED("chat.locked", 0),
		UNLOCKED("chat.unlocked", 0),
		ACTION_SUCCESS("chat.action_success", 0),
		PLAYER_NOT_FOUND("chat.player_not_found", 0),
		WRONG_ITEM("chat.wrong_item", 0),
		ARMOR_WEIGHT("tooltip.weight", 1),
		ARMOR_ENCHANT("tooltip.enchant", 1),

		LVUP_NO_POINT("screen.ability.ability.error.no_point", 0),
		LVUP_NO_PROF("screen.ability.ability.error.no_prof", 0),
		LVUP_MAX_REACHED("screen.ability.ability.error.max_reached", 0),
		LVUP_PROF_MAX("screen.ability.ability.error.prof_max", 0),

		GUI_ABILITY("screen.ability.ability.title", 0),
		GUI_ABILITY_LV("screen.ability.ability.desc.lv", 1),
		GUI_ABILITY_COST("screen.ability.ability.desc.cost", 2),
		GUI_ARCANE("screen.ability.arcane.title", 0),
		GUI_ARCANE_COST("screen.ability.arcane.cost", 2),
		GUI_ELEMENT("screen.ability.elemental.title", 0),
		GUI_ELEMENT_LV("screen.ability.elemental.desc.lv", 1),
		GUI_ELEMENT_COUNT("screen.ability.elemental.desc.count", 1),
		GUI_ELEMENT_COST("screen.ability.elemental.desc.cost", 2),
		GUI_PROF("screen.ability.profession.title", 0),
		GUI_PROF_EXIST("screen.ability.profession.desc.prof_already_exist", 0);

		final String id;
		final int count;

		IDS(String id, int count) {
			this.id = id;
			this.count = count;
		}

		public MutableComponent get(Object... objs) {
			if (objs.length != count)
				throw new IllegalArgumentException("for " + name() + ": expect " + count + " parameters, got " + objs.length);
			return translate(LightLand.MODID + "." + id, objs);
		}

	}

	public enum Keys {
		SKILL_1("key.l2rpgcommon.skill_1", GLFW.GLFW_KEY_Z),
		SKILL_2("key.l2rpgcommon.skill_2", GLFW.GLFW_KEY_X),
		SKILL_3("key.l2rpgcommon.skill_3", GLFW.GLFW_KEY_C);

		public final String id;
		public final int key;
		public final KeyMapping map;

		Keys(String id, int key) {
			this.id = id;
			this.key = key;
			map = new KeyMapping(id, key, "key.categories.l2rpgcommon");
		}

	}

	public interface LangEnum<T extends Enum<T> & LangEnum<T>> {

		int getCount();

		@Nullable
		default Class<? extends Enum<?>> mux() {
			return null;
		}

		@SuppressWarnings({"unchecked"})
		default T getThis() {
			return (T) this;
		}

	}

	public static final Map<Class<? extends Enum<?>>, String> MAP = new HashMap<>();
	public static final Map<Class<? extends LangEnum<?>>, String> LANG_MAP = new HashMap<>();

	static {
		MAP.put(AbilityScreen.AbilityType.class, "screen.ability.ability.");
		MAP.put(ArcaneType.Hit.class, "screen.ability.arcane.activate.");
	}

	public static MutableComponent get(Enum<?> obj, Object... args) {
		if (MAP.containsKey(obj.getClass()))
			return translate(LightLand.MODID + "." + MAP.get(obj.getClass()) + obj.name().toLowerCase(Locale.ROOT), args);
		if (LANG_MAP.containsKey(obj.getClass())) {
			if (obj instanceof LangEnum<?> lang) {
				if (lang.mux() != null) {
					return translate(LightLand.MODID + "." + LANG_MAP.get(obj.getClass()) +
							obj.name().toLowerCase(Locale.ROOT) + ((Enum<?>) args[0]).name().toLowerCase(Locale.ROOT));
				}
			}
			return translate(LightLand.MODID + "." + LANG_MAP.get(obj.getClass()) + obj.name().toLowerCase(Locale.ROOT), args);
		}
		return translate("unknown.enum." + obj.name().toLowerCase(Locale.ROOT));
	}

	public static void addTranslations(BiConsumer<String, String> pvd) {
		for (IDS id : IDS.values()) {
			String[] strs = id.id.split("\\.");
			String str = strs[strs.length - 1];
			pvd.accept(LightLand.MODID + "." + id.id,
					RegistrateLangProvider.toEnglishName(str) + getParams(id.count));
		}
		for (Keys key : Keys.values()) {
			String[] strs = key.id.split("\\.");
			String str = strs[strs.length - 1];
			pvd.accept(key.id, RegistrateLangProvider.toEnglishName(str));
		}
		pvd.accept("itemGroup.l2rpgcommon.material", "Tools and Materials");
		pvd.accept("itemGroup.l2rpgcommon.profession", "Professional Equipments");
		pvd.accept("itemGroup.l2rpgcommon.generated", "Common Equipments");
		pvd.accept("key.categories.l2rpgcommon", "Light Land Keys");
		MAP.forEach((v, k) -> {
			for (Enum<?> e : v.getEnumConstants()) {
				String en = e.name().toLowerCase(Locale.ROOT);
				pvd.accept(LightLand.MODID + "." + k + en,
						RegistrateLangProvider.toEnglishName(en));
			}
		});
		LANG_MAP.forEach((v, k) -> {
			for (LangEnum<?> e : v.getEnumConstants()) {
				String en = e.getThis().name().toLowerCase(Locale.ROOT);
				Class<? extends Enum<?>> next = e.mux();
				if (next != null) {
					for (Enum<?> n : next.getEnumConstants()) {
						String ne = n.name().toLowerCase(Locale.ROOT);
						pvd.accept(LightLand.MODID + "." + k + en + "." + ne,
								RegistrateLangProvider.toEnglishName(en) + " " + RegistrateLangProvider.toEnglishName(ne));
					}
				} else
					pvd.accept(LightLand.MODID + "." + k + en,
							RegistrateLangProvider.toEnglishName(en) + getParams(e.getCount()));
			}
		});

		for (Lore lore : Lore.values()) {
			pvd.accept("lore." + LightLand.MODID + "." + lore.id, lore.lore);
		}
	}

	private static String getParams(int count) {
		if (count <= 0)
			return "";
		StringBuilder pad = new StringBuilder();
		for (int i = 0; i < count; i++) {
			pad.append(pad.length() == 0 ? ": " : "/");
			pad.append("%s");
		}
		return pad.toString();
	}

	public static String asId(String name) {
		return name.toLowerCase(Locale.ROOT);
	}

	public static MutableComponent translate(String key, Object... objs) {
		return MutableComponent.create(new TranslatableContents(key, objs));
	}

}
