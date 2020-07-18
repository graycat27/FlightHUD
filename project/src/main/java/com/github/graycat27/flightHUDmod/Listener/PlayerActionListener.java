package com.github.graycat27.flightHUDmod.Listener;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.*;
import com.github.graycat27.flightHUDmod.FlightHUDGUIController;
import com.github.graycat27.flightHUDmod.FlightHUDMod;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraft.util.text.StringTextComponent;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class PlayerActionListener {

    @SubscribeEvent
    public static void playerFall(LivingEvent.LivingUpdateEvent e){
        LivingEntity ent = e.getEntityLiving();
        ClientPlayerEntity player =  Minecraft.getInstance().player;

        if(player == null){
            // 起動直後ワールド読み込み中に頻発
            // FlightHUDMod.getLogger().debug("object player is null");
            return;
        }
        if(!player.getUniqueID().equals(ent.getUniqueID())){
            // FlightHUDMod.getLogger().debug("Event not caused by player");
            return;
        }

        double lastPosX = player.lastTickPosX;

        if(player.onGround){
            //TODO GUIの非表示処理
            return;
        }

        if(player.isSwimming()){
            FlightHUDMod.getLogger().debug("player is swimming");
        }

        if(!player.isElytraFlying()){
            FlightHUDMod.getLogger().debug("player is not flying with Elytra");
        }

        int flyingTicks = player.getTicksElytraFlying();

        if(flyingTicks % 4 == 0) {

            FlightHUDMod.getLogger().debug("player is flying for " + player.getTicksElytraFlying() + " ticks");

//          FlightHUDGUIController controller = FlightHUDMod.getGuiController();

            player.sendMessage(new StringTextComponent("flying for " + player.getTicksElytraFlying() + " ticks"));
        }
        //controller.showAllComponent();
    }
}
