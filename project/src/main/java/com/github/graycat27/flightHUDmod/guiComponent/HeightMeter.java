package com.github.graycat27.flightHUDmod.guiComponent;

import com.github.graycat27.flightHUDmod.FlightHUDMod;
import com.github.graycat27.flightHUDmod.unit.Height;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.client.particle.SpitParticle;

public class HeightMeter extends GuiComponent  {

    private Height height = null;

    private void resetHeight(){
        this.height = null;
    }

    public Height getHeight(){
        if(height == null){
            throw new IllegalStateException("height is unset");
        }
        return height;
    }

    public HeightMeter(){
        super();
    }

    @Override
    public void update() {
        ClientPlayerEntity player = Minecraft.getInstance().player;

        //プレイヤーの高度を取得
        height = new Height(player);

        FlightHUDMod.getLogger().debug(getHeight().toString());
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("isDisplayed").append(':').append(isDisplayed()).append(',');
        sb.append("height").append(':').append(height.toString());
        sb.append('}');
        return sb.toString();
    }
}
