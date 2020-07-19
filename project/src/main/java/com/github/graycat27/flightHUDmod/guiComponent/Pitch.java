package com.github.graycat27.flightHUDmod.guiComponent;

import com.github.graycat27.flightHUDmod.FlightHUDMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

/**
 * 仰俯角の描画
 * （水平Level=0, 直下=-90, 真上=90）
 */
public class Pitch extends GuiComponent {

    /** 水平を示す定数値 */
    private final int LEVEL = 0;
    /** 真上を示す定数値 */
    private final int TOP = 90;
    /** 真下を示す定数値 */
    private final int BELOW = -90;

    /** 未設定値を示す定数値 */
    private final int UNSET = Integer.MIN_VALUE;

    /** pitch - player facing
     * Integer.MIN_VALUE if unset
     */
    private int pitch = UNSET;

    /**
     * setter for pitch<br>
     * require pitch between -90 to 90
     * @param pit -90 to 90 value
     * @throws IllegalArgumentException when param is not between -90 to 90
     */
    private void setPitch(int pit){
        if(pit < BELOW || TOP < pit ){
            //must between -90 - 90
            throw new IllegalArgumentException("pitch must in -90to90 but was "+ pit);
        }
        this.pitch = pit;
    }

    private void resetPitch(){
        this.pitch = UNSET;
    }

    public int getPitch(){
        if(pitch == UNSET){
            throw new IllegalStateException("pitch is unset");
        }
        return pitch;
    }

    public Pitch(){
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
        setPitch(intFlightPitch);

        FlightHUDMod.getLogger().debug(getPitch());
    }

}
