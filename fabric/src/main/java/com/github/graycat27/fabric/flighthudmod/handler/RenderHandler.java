package com.github.graycat27.fabric.flighthudmod.handler;

import com.github.graycat27.fabric.flighthudmod.handler.fml.FmlRenderHandler;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import com.github.graycat27.fabric.flighthudmod.FlightHUDGUIController;
import com.github.graycat27.fabric.flighthudmod.FlightHUDMod;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;

import static com.github.graycat27.fabric.flighthudmod.FlightHUDMod.modSettings;

@Mixin({MinecraftClient.class})
public class RenderHandler {

    /**
     * tick handler
     */
    @Inject(
            at = {@At("HEAD")},
            method = {"tick"}
    )
    public void fmlRenderTickHandler(){

        FmlRenderHandler fmlHandler = new FmlRenderHandler();
        fmlHandler.fmlRenderTickHandler();

    }
}
