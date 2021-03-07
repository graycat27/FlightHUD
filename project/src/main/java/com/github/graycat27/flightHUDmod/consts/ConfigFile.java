package com.github.graycat27.flightHUDmod.consts;

import com.github.graycat27.flightHUDmod.FlightHUDMod;

import java.io.File;

public final class ConfigFile {
    //file
    public static final String configDirName = File.separator + "config" + File.separator + "flightHudMod";
    public static final String configFileName = "config.properties";

    //keys
    private static final String SETTINGS = "flightHud.setting.";
    public static final String SHOW = SETTINGS + "show";
    public static final String PITCH_INTERVAL = SETTINGS + "interval";


}