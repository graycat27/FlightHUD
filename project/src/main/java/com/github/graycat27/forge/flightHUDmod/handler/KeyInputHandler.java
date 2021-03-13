package com.github.graycat27.forge.flightHUDmod.handler;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

import static com.github.graycat27.forge.flightHUDmod.FlightHUDMod.modSettings;
import static org.lwjgl.glfw.GLFW.*;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class KeyInputHandler {
    private static final String KEY_MAP_CATEGORY = "Flight HUD Mod";

    private static final KeyBinding TRIGGER_SHOW_ON =
            new KeyBinding(I18n.format("flightHud.keyBind.description.show"), GLFW_KEY_H, KEY_MAP_CATEGORY);
    private static final KeyBinding CHANGE_INTERVAL =
            new KeyBinding(I18n.format("flightHud.keyBind.description.interval"), GLFW_KEY_J, KEY_MAP_CATEGORY);

    public static void registerBindings(){
        ClientRegistry.registerKeyBinding(KeyInputHandler.TRIGGER_SHOW_ON);
        ClientRegistry.registerKeyBinding(KeyInputHandler.CHANGE_INTERVAL);
    }

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

        //[H] trigger show HUD
        if(TRIGGER_SHOW_ON.isKeyDown()){
            modSettings.setShowHud(!modSettings.isShowHud());
        }

        //[J] change pitch gauge interval
        if(CHANGE_INTERVAL.isKeyDown()){
            if(!Screen.hasShiftDown()){
                modSettings.addInterval();
            }else{
                modSettings.subInterval();
            }
        }

    }
}
