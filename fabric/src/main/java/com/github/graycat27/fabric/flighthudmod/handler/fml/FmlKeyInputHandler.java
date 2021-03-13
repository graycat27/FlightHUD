package com.github.graycat27.fabric.flighthudmod.handler.fml;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.resource.language.I18n;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.github.graycat27.fabric.flighthudmod.FlightHUDMod.modSettings;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_H;
import static org.lwjgl.glfw.GLFW.GLFW_KEY_J;

public class FmlKeyInputHandler {
    private static final String KEY_MAP_CATEGORY = "Flight HUD Mod";

    private static final KeyBinding TRIGGER_SHOW_ON =
            new KeyBinding(I18n.translate("flightHud.keyBind.description.show"), GLFW_KEY_H, KEY_MAP_CATEGORY);
    private static final KeyBinding CHANGE_INTERVAL =
            new KeyBinding(I18n.translate("flightHud.keyBind.description.interval"), GLFW_KEY_J, KEY_MAP_CATEGORY);

    public static void registerBindings(){
        KeyBindingHelper.registerKeyBinding(FmlKeyInputHandler.TRIGGER_SHOW_ON);
        KeyBindingHelper.registerKeyBinding(FmlKeyInputHandler.CHANGE_INTERVAL);
    }

    public void keyInputHandler(){

        MinecraftClient mc = MinecraftClient.getInstance();
        if(mc.currentScreen != null && mc.currentScreen.shouldCloseOnEsc()){
            //チャット欄での入力に反応しないようにするため。
            return;
        }

        //[H] trigger show HUD
        if(TRIGGER_SHOW_ON.isPressed()){
            modSettings.setShowHud(!modSettings.isShowHud());
        }

        //[J] change pitch gauge interval
        if(CHANGE_INTERVAL.isPressed()){
            if(!Screen.hasShiftDown()){
                modSettings.addInterval();
            }else{
                modSettings.subInterval();
            }
        }

    }
}
