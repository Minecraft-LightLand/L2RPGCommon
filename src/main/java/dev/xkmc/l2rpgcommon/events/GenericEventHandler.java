package dev.xkmc.l2rpgcommon.events;

import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import dev.xkmc.l2rpgcommon.content.common.command.BaseCommand;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.function.Consumer;

@SuppressWarnings("unused")
public class GenericEventHandler {

	@SubscribeEvent
	public static void onCommandRegister(RegisterCommandsEvent event) {
		LiteralArgumentBuilder<CommandSourceStack> lightland = Commands.literal("lightland");
		for (Consumer<LiteralArgumentBuilder<CommandSourceStack>> command : BaseCommand.LIST) {
			command.accept(lightland);
		}
		event.getDispatcher().register(lightland);
	}

}
