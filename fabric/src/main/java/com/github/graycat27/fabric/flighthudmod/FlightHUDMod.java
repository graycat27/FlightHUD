package com.github.graycat27.fabric.flighthudmod;

import com.github.graycat27.fabric.flighthudmod.handler.fml.FmlKeyInputHandler;
import com.github.graycat27.fabric.flighthudmod.setting.ModSettings;
import net.fabricmc.api.ClientModInitializer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class FlightHUDMod implements ClientModInitializer {

    // Directly reference a log4j logger.
    private static final Logger LOGGER = LogManager.getLogger();
    public static Logger getLogger(){
        return LOGGER;
    }

    /** MODID for flightHUDMod */
    public static final String MODID = "flighthudmod";

    private static FlightHUDGUIController guiController;
    public static FlightHUDGUIController getGuiController(){
        return guiController;
    }

    //各種設定
    public static final ModSettings modSettings = new ModSettings();

    @Override
    public void onInitializeClient() {
        FmlKeyInputHandler.registerBindings();
    }
}
