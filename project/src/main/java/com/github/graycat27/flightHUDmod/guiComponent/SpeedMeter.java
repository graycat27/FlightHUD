package com.github.graycat27.flightHUDmod.guiComponent;

import com.github.graycat27.flightHUDmod.FlightHUDMod;
import com.github.graycat27.flightHUDmod.unit.Speed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

/**
 * 速度計<br>
 * 秒速で表示する
 */
public class SpeedMeter extends GuiComponent {

    private Speed speed = null;

    public SpeedMeter(){
        super(Minecraft.getInstance());
    }

    @Override
    public void update() {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if(player == null){
            return;
        }

        speed = new Speed(player);

        FlightHUDMod.getLogger().debug(speed);

    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("isDisplayed").append(':').append(isDisplayed()).append(',');
        sb.append("speed").append(':').append(speed.toString());
        sb.append('}');
        return sb.toString();
    }


}
