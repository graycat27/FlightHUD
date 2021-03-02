package com.github.graycat27.flightHUDmod.listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.gui.ForgeIngameGui;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.*;
import com.github.graycat27.flightHUDmod.FlightHUDGUIController;
import com.github.graycat27.flightHUDmod.FlightHUDMod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class RenderListener {

    /**
     * Overlay GUI drawing handler
     */
    @SubscribeEvent
    public static void renderTickHandler(TickEvent.RenderTickEvent event){

        if(event.phase != TickEvent.Phase.END){
            //Phase require START
            return;
        }

        ForgeIngameGui.renderCrosshairs = true;

        PlayerEntity player =  Minecraft.getInstance().player;
        if(player == null){
            // 起動直後ワールド読み込み中に頻発
            // FlightHUDMod.getLogger().debug("object player is null");
            return;
        }

        FlightHUDGUIController controller = FlightHUDMod.getGuiController();

        Screen curScreen = Minecraft.getInstance().currentScreen;
        if(curScreen != null && (curScreen.shouldCloseOnEsc() || curScreen.isPauseScreen())){
            //controller.hideAllComponent();
            return;
        }

        if((player.isOnGround() || player.fallDistance <= 1.25) &&
                (!player.isSwimming() && !player.isElytraFlying())){
            //単純なジャンプ時の描画抑止
            //controller.hideAllComponent();
            return;
        }

        //render GUI components
        ForgeIngameGui.renderCrosshairs = false;
        controller.showAllComponent();
        controller.updateAllComponent();

    }
}
