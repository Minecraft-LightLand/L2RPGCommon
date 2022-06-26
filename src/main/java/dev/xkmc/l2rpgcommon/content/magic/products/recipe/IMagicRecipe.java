package dev.xkmc.l2rpgcommon.content.magic.products.recipe;

import com.google.common.collect.Maps;
import dev.xkmc.l2library.base.recipe.BaseRecipe;
import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.serial.network.BaseConfig;
import dev.xkmc.l2rpgcommon.content.magic.products.IMagicProduct;
import dev.xkmc.l2rpgcommon.content.magic.products.MagicElement;
import dev.xkmc.l2rpgcommon.content.magic.products.MagicProductType;
import dev.xkmc.l2rpgcommon.content.magic.products.info.DisplayInfo;
import dev.xkmc.l2rpgcommon.network.ConfigType;
import dev.xkmc.l2rpgcommon.network.NetworkManager;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@SerialClass
@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class IMagicRecipe extends BaseConfig {

	@SerialClass.SerialField
	public ResourceLocation[] predecessor;
	@SerialClass.SerialField
	public ElementalMastery[] elemental_mastery;
	@SerialClass.SerialField
	public MagicProductType<?, ?> product_type;
	@SerialClass.SerialField
	public ResourceLocation product_id;
	@SerialClass.SerialField
	public DisplayInfo screen;
	private MagicElement[] elements;
	private boolean[][] maps;

	public IMagicRecipe() {
	}

	public static List<IMagicRecipe> getAll() {
		return NetworkManager.getConfigs(ConfigType.MAGIC_DATA).map(e -> (IMagicRecipe) e.getValue()).toList();
	}

	public static <T> Map<T, IMagicRecipe> getMap(MagicProductType<T, ?> type) {
		Map<T, IMagicRecipe> ans = Maps.newLinkedHashMap();
		getAll().stream().filter(r -> r.product_type == type)
				.forEach(r -> Optional.of(type.getter.apply(r.product_id)).ifPresent((t) -> ans.put(t, r)));
		return ans;
	}

	public final IMagicProduct<?, ?> getProduct() {
		return IMagicProduct.getInstance(product_type, product_id);
	}

	protected final void register(MagicElement[] elements, boolean[][] maps) {
		this.elements = elements;
		this.maps = maps;
	}

	public final MagicElement[] getElements() {
		return elements;
	}

	public final boolean[][] getGraph() {
		return maps;
	}

	@SerialClass
	public static class ElementalMastery {

		@SerialClass.SerialField
		public MagicElement element;

		@SerialClass.SerialField
		public int level;

	}

}
