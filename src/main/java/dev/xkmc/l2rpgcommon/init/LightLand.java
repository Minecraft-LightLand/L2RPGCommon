package dev.xkmc.l2rpgcommon.init;

import dev.xkmc.l2library.base.L2Registrate;
import dev.xkmc.l2library.repack.registrate.providers.ProviderType;
import dev.xkmc.l2rpgcommon.compat.GeneralCompatHandler;
import dev.xkmc.l2rpgcommon.content.arcane.internal.ArcaneType;
import dev.xkmc.l2rpgcommon.content.common.capability.player.LLPlayerData;
import dev.xkmc.l2rpgcommon.content.common.command.*;
import dev.xkmc.l2rpgcommon.events.DamageEventHandler;
import dev.xkmc.l2rpgcommon.events.GenericEventHandler;
import dev.xkmc.l2rpgcommon.events.ItemUseEventHandler;
import dev.xkmc.l2rpgcommon.events.MiscEventHandler;
import dev.xkmc.l2rpgcommon.init.data.AllTags;
import dev.xkmc.l2rpgcommon.init.data.LangData;
import dev.xkmc.l2rpgcommon.init.data.recipe.RecipeGen;
import dev.xkmc.l2rpgcommon.init.data.configs.ConfigGenDispatcher;
import dev.xkmc.l2rpgcommon.init.registrate.*;
import dev.xkmc.l2rpgcommon.init.special.*;
import dev.xkmc.l2rpgcommon.init.worldgenreg.StructureRegistrate;
import dev.xkmc.l2rpgcommon.init.worldgenreg.WorldGenRegistrate;
import dev.xkmc.l2rpgcommon.network.NetworkManager;
import dev.xkmc.l2rpgcommon.util.EffectAddUtil;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ParticleFactoryRegisterEvent;
import net.minecraftforge.common.ForgeMod;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.capabilities.RegisterCapabilitiesEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.DistExecutor;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod("l2rpgcommon")
public class LightLand {

	public static final String MODID = "l2rpgcommon";
	public static final Logger LOGGER = LogManager.getLogger();
	public static final L2Registrate REGISTRATE = new L2Registrate(MODID);

	private static void registerRegistrates(IEventBus bus) {
		ForgeMod.enableMilkFluid();
		LLBlocks.register();
		LLEntities.register();
		LLItems.register();
		LLMenu.register();
		LLRecipes.register(bus);
		LLEffects.register();
		LLParticle.register();
		WorldGenRegistrate.register();
		StructureRegistrate.register();
		LightLandRegistry.register();
		MagicRegistry.register();
		ArcaneType.register();
		ArcaneRegistry.register();
		SpellRegistry.register();
		SkillRegistry.register();
		AllTags.register();
		NetworkManager.register();
		GeneralCompatHandler.handle(GeneralCompatHandler.Stage.INIT);
		REGISTRATE.addDataGenerator(ProviderType.RECIPE, RecipeGen::genRecipe);

		LLPlayerData.register();
	}

	private static void registerForgeEvents() {
		MinecraftForge.EVENT_BUS.register(ItemUseEventHandler.class);
		MinecraftForge.EVENT_BUS.register(GenericEventHandler.class);
		MinecraftForge.EVENT_BUS.register(DamageEventHandler.class);
		MinecraftForge.EVENT_BUS.register(MiscEventHandler.class);

	}

	private static void registerModBusEvents(IEventBus bus) {
		bus.addListener(LightLand::setup);
		bus.addListener(LLClient::clientSetup);
		bus.addListener(EventPriority.LOWEST, LightLand::gatherData);
		bus.addListener(LightLand::onParticleRegistryEvent);
		bus.addListener(LightLand::registerCaps);
		bus.addListener(LLEntities::registerEntityAttributes);
	}

	private static void registerCommands() {
		RegistryParser.register();
		BaseCommand.LIST.add(MainCommand::new);
		BaseCommand.LIST.add(ArcaneCommand::new);
		BaseCommand.LIST.add(MagicCommand::new);
	}

	public LightLand() {
		FMLJavaModLoadingContext ctx = FMLJavaModLoadingContext.get();
		IEventBus bus = ctx.getModEventBus();
		registerModBusEvents(bus);
		DistExecutor.unsafeRunWhenOn(Dist.CLIENT, () -> () -> LLClient.onCtorClient(bus, MinecraftForge.EVENT_BUS));
		registerRegistrates(bus);
		registerForgeEvents();
		registerCommands();
	}

	private static void setup(final FMLCommonSetupEvent event) {
		event.enqueueWork(() -> {
			EffectAddUtil.init();
			LLEffects.registerBrewingRecipe();
		});
		StructureRegistrate.commonSetup(event);
	}

	public static void gatherData(GatherDataEvent event) {
		LangData.addTranslations(REGISTRATE::addRawLang);
		event.getGenerator().addProvider(true, new ConfigGenDispatcher(event.getGenerator()));
	}

	public static void onParticleRegistryEvent(ParticleFactoryRegisterEvent event) {
		LLParticle.registerClient();
	}

	public static void registerCaps(RegisterCapabilitiesEvent event) {
	}

}
