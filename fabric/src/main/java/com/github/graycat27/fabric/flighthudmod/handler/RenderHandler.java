package com.github.graycat27.fabric.flighthudmod.handler;

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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static com.github.graycat27.fabric.flighthudmod.FlightHUDMod.modSettings;

@Environment(EnvType.CLIENT)
@Mixin({MinecraftClient.class})
public class RenderHandler {

    /**
     * Overlay GUI drawing handler
     */
    @Inject(
            at = {@At("HEAD")},
            method = {"tick"}
    )
    public static void renderTickHandler(CallbackInfo info){

        MinecraftClient mc = MinecraftClient.getInstance();
        PlayerEntity player =  mc.player;
        if(player == null){
            // 起動直後ワールド読み込み中に頻発
            // FlightHUDMod.getLogger().debug("object player is null");
            return;
        }

        FlightHUDGUIController controller = FlightHUDMod.getGuiController();

        if(mc.options.hudHidden || !modSettings.isShowHud()){
            return;
        }
        Screen curScreen = mc.currentScreen;
        if(curScreen != null && (curScreen.shouldCloseOnEsc() || curScreen.isPauseScreen())){
            //controller.hideAllComponent();
            return;
        }

        if( player.isSpectator() ||
            (!player.isOnGround() && player.fallDistance > 1.25) || //滞空時。単純なジャンプ時の描画抑止
            (player.getVehicle() != null) || player.isSwimming() || player.isFallFlying()  //表示対象の条件
        ){
            //render GUI components
            controller.showAllComponent();
            controller.updateAllComponent();
        }else{
            //controller.hideAllComponent();
            return;
        }

    }
}
