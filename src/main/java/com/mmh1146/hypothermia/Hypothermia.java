package com.mmh1146.hypothermia;

import net.fabricmc.api.ModInitializer;

import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.biome.Biome;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Hypothermia implements ModInitializer {
	public static final String MOD_ID = "hypothermia";

	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		LOGGER.info("Hypothermia initialized");
	}

	/**
	 * 判断实体所在位置是否正在下雪。
	 * 原版 precipitationAt 综合了：天气为降雨、可见天空、生物群系有降水且温度低于 0.15。
	 */
	public static boolean isSnowingAt(Entity entity) {
		return entity.level().precipitationAt(entity.blockPosition()) == Biome.Precipitation.SNOW;
	}

	public static Identifier id(String path) {
		return Identifier.fromNamespaceAndPath(MOD_ID, path);
	}
}
