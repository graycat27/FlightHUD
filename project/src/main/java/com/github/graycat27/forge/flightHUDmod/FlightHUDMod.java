package com.github.graycat27.forge.flightHUDmod;

import com.github.graycat27.forge.flightHUDmod.handler.KeyInputHandler;
import com.github.graycat27.forge.flightHUDmod.handler.RenderHandler;
import com.github.graycat27.forge.flightHUDmod.setting.ModSettings;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.event.lifecycle.InterModEnqueueEvent;
import net.minecraftforge.fml.event.lifecycle.InterModProcessEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.stream.Collectors;

@Mod(FlightHUDMod.MODID)
public class FlightHUDMod
{
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

    public FlightHUDMod() {
        // Register the setup method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::setup);
        // Register the enqueueIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::enqueueIMC);
        // Register the processIMC method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::processIMC);
        // Register the doClientStuff method for modloading
        FMLJavaModLoadingContext.get().getModEventBus().addListener(this::doClientStuff);

        // Register ourselves for server and other game events we are interested in
        MinecraftForge.EVENT_BUS.register(this);
        MinecraftForge.EVENT_BUS.register(RenderHandler.class);
        MinecraftForge.EVENT_BUS.register(KeyInputHandler.class);

        KeyInputHandler.registerBindings();

    }

    private void setup(final FMLCommonSetupEvent event)
    {
       LOGGER.info("Start Setup FlightHUD");
       guiController = new FlightHUDGUIController();
    }

    private void doClientStuff(final FMLClientSetupEvent event) {
        // do something that can only be done on the client
        LOGGER.info("Got game settings {}", event.getMinecraftSupplier().get().gameSettings);
    }

    private void enqueueIMC(final InterModEnqueueEvent event)
    {
        // some example code to dispatch IMC to another mod
        // InterModComms.sendTo("examplemod", "helloworld", () -> { LOGGER.info("Hello world from the MDK"); return "Hello world";});
    }

    private void processIMC(final InterModProcessEvent event)
    {
        // some example code to receive and process InterModComms from other mods
        LOGGER.info("Got IMC {}", event.getIMCStream().
                map(m->m.getMessageSupplier().get()).
                collect(Collectors.toList()));
    }
}
