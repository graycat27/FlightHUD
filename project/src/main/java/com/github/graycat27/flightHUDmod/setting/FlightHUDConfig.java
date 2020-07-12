package com.github.graycat27.flightHUDmod.setting;

import net.minecraft.world.BossInfo;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig;
import net.minecraftforge.server.command.ForgeCommand;

import java.time.format.TextStyle;

@Mod.EventBusSubscriber
public class FlightHUDConfig {

    public static final String CATEGORY_GENERAL = "general";

    private static final ForgeConfigSpec.Builder COMMON_BUILDER = new ForgeConfigSpec.Builder();
    private static final ForgeConfigSpec.Builder CLIENT_BUILDER = new ForgeConfigSpec.Builder();

    public static ForgeConfigSpec COMMON_CONFIG;
    public static ForgeConfigSpec CLIENT_CONFIG;


    public static ForgeConfigSpec.BooleanValue SHOW_COMPASS;

    public static ForgeConfigSpec.EnumValue<GuiColor> GUI_COLOR;


}
