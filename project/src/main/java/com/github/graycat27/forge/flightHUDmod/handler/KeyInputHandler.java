package com.github.graycat27.forge.flightHUDmod.handler;

import net.minecraft.client.KeyMapping;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.language.I18n;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.graycat27.forge.flightHUDmod.FlightHUDMod.modSettings;
import static org.lwjgl.glfw.GLFW.*;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class KeyInputHandler {
    private static final String KEY_MAP_CATEGORY = "Flight HUD Mod";

    private static final KeyMapping TRIGGER_SHOW_ON =
            new KeyMapping(I18n.get("flightHud.keyBind.description.show"), GLFW_KEY_H, KEY_MAP_CATEGORY);
    private static final KeyMapping CHANGE_INTERVAL =
            new KeyMapping(I18n.get("flightHud.keyBind.description.interval"), GLFW_KEY_J, KEY_MAP_CATEGORY);

    public static void registerBindings(){
        new RegisterKeyMappingsEvent(Minecraft.getInstance().options).register(KeyInputHandler.TRIGGER_SHOW_ON);
        new RegisterKeyMappingsEvent(Minecraft.getInstance().options).register(KeyInputHandler.CHANGE_INTERVAL);
    }

    /**
     * key down handler
     */
    @SubscribeEvent
    public static void keyInputHandler(RegisterKeyMappingsEvent event){

        Minecraft mc = Minecraft.getInstance();
        if(mc.screen != null && mc.screen.shouldCloseOnEsc()){
            //チャット欄での入力に反応しないようにするため。
            return;
        }

        //[H] trigger show HUD
        if(TRIGGER_SHOW_ON.isDown()){
            modSettings.setShowHud(!modSettings.isShowHud());
        }

        //[J] change pitch gauge interval
        if(CHANGE_INTERVAL.isDown()){
            if(!Screen.hasShiftDown()){
                modSettings.addInterval();
            }else{
                modSettings.subInterval();
            }
        }

    }
}
