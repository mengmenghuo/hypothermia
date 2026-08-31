package com.mmh1146.hypothermia.mixin;

import com.mmh1146.hypothermia.Hypothermia;

import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {
	/**
	 * 原版 aiStep 中，实体不在细雪里时每 tick 解冻 2 刻度（setTicksFrozen(max(0, ticks - 2))）。
	 * 这里拦截该调用：玩家暴露在下雪处时改为每 tick 累积 1 冻结刻度（上限同细雪），
	 * 其余情况维持原版解冻逻辑。之后原版会自动处理冰霜覆盖层、减速与冻结伤害。
	 */
	@Redirect(
		method = "aiStep",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/LivingEntity;setTicksFrozen(I)V"
		)
	)
	private void hypothermia$freezeInSnowingWeather(LivingEntity entity, int thawedTicks) {
		if (entity instanceof Player && entity.canFreeze() && Hypothermia.isSnowingAt(entity)) {
			entity.setTicksFrozen(Math.min(entity.getTicksRequiredToFreeze(), entity.getTicksFrozen() + 1));
		} else {
			entity.setTicksFrozen(thawedTicks);
		}
	}
}
