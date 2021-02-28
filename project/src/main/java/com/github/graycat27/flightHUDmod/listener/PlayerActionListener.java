package com.github.graycat27.flightHUDmod.listener;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.vector.Matrix4f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
//import net.minecraftforge.event.entity.player.PlayerEvent.LivingUpdateEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.*;
import com.github.graycat27.flightHUDmod.FlightHUDGUIController;
import com.github.graycat27.flightHUDmod.FlightHUDMod;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PlayerActionListener {

    @SubscribeEvent
    public static void playerFall(TickEvent.RenderTickEvent e){
        // RenderGameOverlayEvent
        //


   /*     LivingEntity ent = e.getEntityLiving();
        if(!(ent instanceof PlayerEntity)){
            return;
        }

*/

        PlayerEntity player =  Minecraft.getInstance().player;
        if(player == null){
            // 起動直後ワールド読み込み中に頻発
            // FlightHUDMod.getLogger().debug("object player is null");
            return;
        }

        double lastPosX = player.lastTickPosX;

        if(player.isOnGround()){
            //TODO GUIの非表示処理
            return;
        }

        if(player.isSwimming()){
            FlightHUDMod.getLogger().debug("player is swimming");
        }

        if(!player.isElytraFlying()){
            FlightHUDMod.getLogger().debug("player is not flying with Elytra");
        }

        if(player.isInWater() || player.isInLava()){
            FlightHUDMod.getLogger().debug("player is in the water or lava");
        }
        int flyingTicks = player.getTicksElytraFlying();

        if(flyingTicks % 4 == 0) {

            FlightHUDMod.getLogger().debug("player is flying for " + player.getTicksElytraFlying() + " ticks");

            FlightHUDGUIController controller = FlightHUDMod.getGuiController();

            controller.showAllComponent();

            controller.updateAllComponent();

/*
            //FontRenderer.drawString(MatrixStack, String, Float, Float, Integer)
            RenderSystem.pushMatrix();
            Minecraft.getInstance().fontRenderer.func_238421_b_(new MatrixStack(),
                    "TEXT", 0f, 0f, 0x123456);
            RenderSystem.popMatrix();

 */
        }
        //controller.showAllComponent();
    }
}
