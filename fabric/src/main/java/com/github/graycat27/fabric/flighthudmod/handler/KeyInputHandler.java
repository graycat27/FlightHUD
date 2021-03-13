package com.github.graycat27.fabric.flighthudmod.handler;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.resource.language.I18n;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.github.graycat27.fabric.flighthudmod.FlightHUDMod.modSettings;
import static org.lwjgl.glfw.GLFW.*;

@Environment(EnvType.CLIENT)
@Mixin({MinecraftClient.class})
public class KeyInputHandler {
    private static final String KEY_MAP_CATEGORY = "Flight HUD Mod";

    private static final KeyBinding TRIGGER_SHOW_ON =
            new KeyBinding(I18n.translate("flightHud.keyBind.description.show"), GLFW_KEY_H, KEY_MAP_CATEGORY);
    private static final KeyBinding CHANGE_INTERVAL =
            new KeyBinding(I18n.translate("flightHud.keyBind.description.interval"), GLFW_KEY_J, KEY_MAP_CATEGORY);

    public static void registerBindings(){
        KeyBindingHelper.registerKeyBinding(KeyInputHandler.TRIGGER_SHOW_ON);
        KeyBindingHelper.registerKeyBinding(KeyInputHandler.CHANGE_INTERVAL);
    }

    /**
     * key down handler
     */
    @Inject(
            at = {@At("HEAD")},
            method = {"handleInputEvents"}
    )
    public static void keyInputHandler(CallbackInfo info){

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
