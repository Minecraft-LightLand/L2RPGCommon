package dev.xkmc.l2rpgcommon.content.skill;

import dev.xkmc.l2library.serial.SerialClass;
import dev.xkmc.l2library.util.annotation.DataGenOnly;
import dev.xkmc.l2library.util.raytrace.RayTraceUtil;
import dev.xkmc.l2rpgcommon.content.skill.internal.Skill;
import dev.xkmc.l2rpgcommon.content.skill.internal.SkillConfig;
import dev.xkmc.l2rpgcommon.content.skill.internal.SkillData;
import dev.xkmc.l2rpgcommon.init.LightLand;
import it.unimi.dsi.fastutil.ints.Int2DoubleFunction;
import it.unimi.dsi.fastutil.ints.Int2IntFunction;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.phys.Vec3;

public class MovementSkill extends Skill<MovementSkill.Config, MovementSkill.Data> {

	@SerialClass
	public static class Data extends SkillData {

		@SerialClass.SerialField
		public Vec3 velocity = Vec3.ZERO;

		@SerialClass.SerialField
		public int time = 0;

		@SerialClass.SerialField
		public boolean init = false;

		public void activate(Config config, ServerPlayer player) {
			int lv = Math.min(level, config.max_level);
			time = config.time[lv];
			velocity = RayTraceUtil.getRayTerm(Vec3.ZERO, player.getXRot(), player.getYRot(), config.velocity[lv]);
			init = true;
		}

	}

	@SerialClass
	public static class Config extends SkillConfig<Data> {

		@SerialClass.SerialField
		public int[] time;

		@SerialClass.SerialField
		public double[] velocity;

		@Override
		public boolean isValid() {
			if (!super.isValid()) return false;
			if (time.length != max_level) {
				LightLand.LOGGER.error("time length must be the same as max_level");
				return false;
			}
			if (velocity.length != max_level) {
				LightLand.LOGGER.error("velocity length must be the same as max_level");
				return false;
			}
			for (int i = 0; i < max_level; i++) {
				int t = time[i];
				double v = velocity[i];
				if (t <= 0 || t >= cooldown[i]) {
					LightLand.LOGGER.error("time cannot exceed cooldown");
					return false;
				}
				if (v < 0.25 || v > 3) {
					LightLand.LOGGER.error("velocity must be between 0.25 and 3");
					return false;
				}
			}
			return true;
		}

	}

	@Override
	public Data genData(Player player) {
		return new Data();
	}

	@Override
	public void activate(Level level, ServerPlayer player, Data data) {
		data.activate(getConfig(), player);
		super.activate(level, player, data);
	}

	@Override
	public void tick(Player player, Data data) {
		super.tick(player, data);
		if (data.time > 0) {
			if (player.level.isClientSide()) {
				player.setDeltaMovement(data.velocity);
				player.makeStuckInBlock(Blocks.AIR.defaultBlockState(), Vec3.ZERO);
			} else {
				if (data.init) {
					data.init = false;
					player.hasImpulse = true;
				}
				player.setDeltaMovement(data.velocity);
				player.makeStuckInBlock(Blocks.AIR.defaultBlockState(), Vec3.ZERO);
			}
			data.time--;
			if (data.time <= 0) {
				data.time = 0;
				data.velocity = Vec3.ZERO;
			}
		}
	}

	@DataGenOnly
	public static class MovementSkillBuilder extends SkillConfig.ConfigLevelBuilder<Config, Data> {

		private final Int2IntFunction time;
		private final Int2DoubleFunction velocity;

		public MovementSkillBuilder(int maxLevel, Int2IntFunction cooldown, Int2IntFunction time, Int2DoubleFunction velocity) {
			super(maxLevel, cooldown);
			this.time = time;
			this.velocity = velocity;
		}

		@Override
		protected Config build(Config config) {
			config.time = new int[maxLevel];
			config.velocity = new double[maxLevel];
			return super.build(config);
		}

		@Override
		protected void build(Config config, int lv) {
			super.build(config, lv);
			config.time[lv] = time.applyAsInt(lv);
			config.velocity[lv] = velocity.applyAsDouble(lv);
		}

		@Override
		public Config build() {
			return build(new Config());
		}
	}

}
