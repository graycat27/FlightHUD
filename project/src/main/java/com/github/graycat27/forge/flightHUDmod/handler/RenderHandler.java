package com.github.graycat27.forge.flightHUDmod.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.player.LocalPlayer;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.*;
import com.github.graycat27.forge.flightHUDmod.FlightHUDGUIController;
import com.github.graycat27.forge.flightHUDmod.FlightHUDMod;

import static com.github.graycat27.forge.flightHUDmod.FlightHUDMod.modSettings;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class RenderHandler {

    /**
     * Overlay GUI drawing handler
     */
    @SubscribeEvent
    public static void renderTickHandler(TickEvent.RenderTickEvent event){

        if(event.phase != TickEvent.Phase.END){
            //Phase require START
            return;
        }

        Minecraft mc = Minecraft.getInstance();
        LocalPlayer player =  mc.player;
        if(player == null){
            // 起動直後ワールド読み込み中に頻発
            // FlightHUDMod.getLogger().debug("object player is null");
            return;
        }

        FlightHUDGUIController controller = FlightHUDMod.getGuiController();

        if(mc.options.hideGui || !modSettings.isShowHud()){
            return;
        }
        Screen curScreen = mc.screen;
        if(curScreen != null && (curScreen.shouldCloseOnEsc() || curScreen.isPauseScreen())){
            //controller.hideAllComponent();
            return;
        }

        if( player.isSpectator() ||
            (!player.isOnGround() && player.fallDistance > 1.25) || //滞空時。単純なジャンプ時の描画抑止
            (player.getVehicle() != null) || player.isSwimming() || player.tryToStartFallFlying()  //表示対象の条件
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
