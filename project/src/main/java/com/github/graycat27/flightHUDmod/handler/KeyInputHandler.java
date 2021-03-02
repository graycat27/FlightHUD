package com.github.graycat27.flightHUDmod.handler;

import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.graycat27.flightHUDmod.FlightHUDMod.modSettings;
import static org.lwjgl.glfw.GLFW.*;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class KeyInputHandler {

    /**
     * key down handler
     */
    @SubscribeEvent
    public static void keyInputHandler(InputEvent.KeyInputEvent event){

        Minecraft mc = Minecraft.getInstance();
        if(mc.currentScreen != null && mc.currentScreen.shouldCloseOnEsc()){
            //チャット欄での入力に反応しないようにするため。
            return;
        }

        //[Alt]+[H] trigger show HUD
        if(event.getAction() == GLFW_PRESS && event.getKey() == GLFW_KEY_H && event.getModifiers() == GLFW_MOD_ALT){
            modSettings.setShowHud(!modSettings.isShowHud());
        }

    }
}
