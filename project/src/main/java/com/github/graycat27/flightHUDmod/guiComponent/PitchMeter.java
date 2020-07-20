package com.github.graycat27.flightHUDmod.guiComponent;

import com.github.graycat27.flightHUDmod.FlightHUDMod;
import com.github.graycat27.flightHUDmod.unit.Pitch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

/**
 * 仰俯角の描画
 * （水平Level=0, 直下=-90, 真上=90）
 */
public class PitchMeter extends GuiComponent {

    /** pitch - player facing
     * Integer.MIN_VALUE if unset
     */
    private Pitch pitch;

    private void resetPitch(){
        this.pitch = null;
    }

    public Pitch getPitch(){
        if(pitch == null){
            throw new IllegalStateException("pitch is unset");
        }
        return pitch;
    }

    public PitchMeter(){
        super(Minecraft.getInstance());

    }

    @Override
    public void show(){
        super.show();
        update();
    }

    @Override
    public void update() {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if(player == null){
            resetPitch();
            return;
        }

        //プレイヤーの向いている仰俯角を算出
        /* playerPitch = LEVELを0として、下を正とした回転角度 */
        float playerPitch = player.rotationPitch;
        int intFlightPitch = Math.round((-1)*playerPitch);
        pitch = new Pitch(intFlightPitch);

        FlightHUDMod.getLogger().debug(getPitch());
    }

}
