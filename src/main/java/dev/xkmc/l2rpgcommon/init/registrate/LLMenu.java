package dev.xkmc.l2rpgcommon.init.registrate;

import dev.xkmc.l2library.repack.registrate.util.entry.MenuEntry;
import dev.xkmc.l2rpgcommon.content.magic.gui.craft.*;
import dev.xkmc.l2rpgcommon.init.LightLand;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.inventory.MenuType;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.Objects;

/**
 * handles container menu
 */
@MethodsReturnNonnullByDefault
public class LLMenu {

	public static final MenuEntry<ArcaneInjectContainer> MT_ARCANE = LightLand.REGISTRATE.menu("arcane_inject",
			ArcaneInjectContainer::new, () -> ArcaneInjectScreen::new).lang(LLMenu::getLangKey).register();
	public static final MenuEntry<DisEnchanterContainer> MT_DISENC = LightLand.REGISTRATE.menu("disenchanter",
			DisEnchanterContainer::new, () -> DisEnchanterScreen::new).lang(LLMenu::getLangKey).register();
	public static final MenuEntry<SpellCraftContainer> MT_SPCRAFT = LightLand.REGISTRATE.menu("spell_craft",
			SpellCraftContainer::new, () -> SpellCraftScreen::new).lang(LLMenu::getLangKey).register();

	public static void register() {

	}

	public static String getLangKey(MenuType<?> menu) {
		ResourceLocation rl = Objects.requireNonNull(ForgeRegistries.CONTAINERS.getKey(menu));
		return "container." + rl.getNamespace() + "." + rl.getPath();
	}

}
