package com.github.graycat27.flightHUDmod.listener;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
//import net.minecraftforge.event.entity.player.PlayerEvent.LivingUpdateEvent;
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

        PlayerEntity player =  Minecraft.getInstance().player;
        if(player == null){
            // 起動直後ワールド読み込み中に頻発
            // FlightHUDMod.getLogger().debug("object player is null");
            return;
        }

        Screen curScreen = Minecraft.getInstance().currentScreen;
        if(curScreen != null && (curScreen.shouldCloseOnEsc() || curScreen.isPauseScreen())){
            //controller.hideAllComponent();
            return;
        }

        FlightHUDGUIController controller = FlightHUDMod.getGuiController();
        if(player.isOnGround()){
            //controller.hideAllComponent();
            return;
        }

        if(player.fallDistance != 0 && player.fallDistance < 1
                && (!player.isSwimming() && !player.isElytraFlying())){
            //単純なジャンプ時の描画抑止
            //controller.hideAllComponent();
            return;
        }

        if(player.isInWater() || player.isInLava()){
            FlightHUDMod.getLogger().debug("player is in the water or lava");
        }

        //render GUI components
        controller.showAllComponent();
        controller.updateAllComponent();

    }
}
