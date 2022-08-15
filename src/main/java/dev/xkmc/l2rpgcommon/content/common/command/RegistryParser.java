package dev.xkmc.l2rpgcommon.content.common.command;

import com.google.gson.JsonObject;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import dev.xkmc.l2library.util.code.Wrappers;
import dev.xkmc.l2rpgcommon.content.profession.Profession;
import dev.xkmc.l2rpgcommon.content.skill.internal.Skill;
import dev.xkmc.l2rpgcommon.init.data.LangData;
import dev.xkmc.l2rpgcommon.init.special.LightLandRegistry;
import net.minecraft.commands.CommandBuildContext;
import net.minecraft.commands.SharedSuggestionProvider;
import net.minecraft.commands.synchronization.ArgumentTypeInfo;
import net.minecraft.commands.synchronization.ArgumentTypeInfos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

@SuppressWarnings({"unchecked", "rawtypes"})
public record RegistryParser<T>(Class<T> cls, Supplier<IForgeRegistry<T>> registry)
		implements ArgumentType<T>, ArgumentTypeInfo.Template<RegistryParser<?>> {

	public static final Set<RegistryParser<?>> SET = new HashSet<>();
	public static final RegistryParser<Profession> PROFESSION = new RegistryParser<>(Profession.class, LightLandRegistry.PROFESSION);
	public static final RegistryParser<Skill<?, ?>> SKILL = new RegistryParser(Skill.class, LightLandRegistry.SKILL);

	private static final RegistryInfo INFO = new RegistryInfo();

	public static void register() {
		Class<RegistryParser<?>> cls = Wrappers.cast(RegistryParser.class);
		ArgumentTypeInfos.registerByClass(cls, INFO);
	}

	public RegistryParser(Class<T> cls, Supplier<IForgeRegistry<T>> registry) {
		this.cls = cls;
		this.registry = registry;
		SET.add(this);
	}

	@Override
	public T parse(StringReader reader) throws CommandSyntaxException {
		int cursor = reader.getCursor();
		ResourceLocation rl = ResourceLocation.read(reader);
		T val = registry.get().getValue(rl);
		if (val == null) {
			reader.setCursor(cursor);
			throw new DynamicCommandExceptionType(LangData.IDS.INVALID_ID::get).createWithContext(reader, rl.toString());
		}
		return val;
	}

	@Override
	public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> context, SuggestionsBuilder builder) {
		return SharedSuggestionProvider.suggestResource(registry.get().getKeys(), builder);
	}

	@Override
	public RegistryParser<T> instantiate(CommandBuildContext context) {
		return this;
	}

	@Override
	public RegistryInfo type() {
		return INFO;
	}

	public static class RegistryInfo implements ArgumentTypeInfo<RegistryParser<?>, RegistryParser<?>> {

		@Override
		public void serializeToNetwork(RegistryParser<?> e, FriendlyByteBuf packet) {
			IForgeRegistry reg = e.registry.get();
			packet.writeUtf(reg.getRegistryName().toString());
		}

		@Override
		public RegistryParser deserializeFromNetwork(FriendlyByteBuf packet) {
			String name = packet.readUtf();
			return Objects.requireNonNull(SET.stream().filter(e -> e.registry.get().getRegistryName().toString().equals(name)).findFirst().orElse(null));
		}

		@Override
		public void serializeToJson(RegistryParser<?> e, JsonObject json) {
			IForgeRegistry reg = e.registry.get();
			json.addProperty("id", reg.getRegistryName().toString());
		}

		@Override
		public RegistryParser unpack(RegistryParser parser) {
			return parser;
		}
	}

}
