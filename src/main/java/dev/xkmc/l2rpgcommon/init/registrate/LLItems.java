package dev.xkmc.l2rpgcommon.init.registrate;

import dev.xkmc.l2library.repack.registrate.util.entry.ItemEntry;
import dev.xkmc.l2rpgcommon.content.common.gui.ProfessionScreen;
import dev.xkmc.l2rpgcommon.content.common.item.RecordPearl;
import dev.xkmc.l2rpgcommon.content.common.item.misc.ScreenBook;
import dev.xkmc.l2rpgcommon.init.LightLand;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.function.Supplier;

import static dev.xkmc.l2rpgcommon.init.LightLand.REGISTRATE;

@SuppressWarnings({"rawtypes", "unchecked", "unsafe"})
@MethodsReturnNonnullByDefault
public class LLItems {

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

	public static final Tab TAB_MAIN = new Tab("material", () -> LLItems.RECORD_PEARL);

	static {
		REGISTRATE.creativeModeTab(() -> TAB_MAIN);
	}

	// -------- common --------
	public static final ItemEntry<RecordPearl> RECORD_PEARL;
	public static final ItemEntry<ScreenBook> ABILITY_BOOK;

	static {
		RECORD_PEARL = REGISTRATE.item("record_pearl", p -> new RecordPearl(p.stacksTo(1))).defaultModel().defaultLang().register();
		ABILITY_BOOK = REGISTRATE.item("ability_book", p -> new ScreenBook(p, () -> ProfessionScreen::new)).defaultModel().defaultLang().register();
	}

	public static void register() {
	}

	public static ItemEntry<Item> simpleItem(String id) {
		return REGISTRATE.item(id, Item::new).defaultModel().defaultLang().register();
	}

}
