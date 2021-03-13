package com.github.graycat27.fabric.flighthudmod.handler;

import com.github.graycat27.fabric.flighthudmod.handler.fml.FmlKeyInputHandler;
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

    /**
     * key down handler
     */
    @Inject(
            at = {@At("HEAD")},
            method = {"handleInputEvents"}
    )
    public static void keyInputHandler(CallbackInfo info){
        FmlKeyInputHandler fmlHandler = new FmlKeyInputHandler();
        fmlHandler.keyInputHandler();

    }
}
