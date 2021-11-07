package com.github.graycat27.forge.flightHUDmod.unit;

import com.github.graycat27.forge.flightHUDmod.guiComponent.PitchMeter;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Direction;

public class Pitch implements IUnit {

    public static final String DEGREES = "°";

    public static final int LEVEL = 0;

    public static final int DOWN = -90;

    public static final int UP = 90;

    /** 内部管理変数。仰俯角。下向きが正 */
    private float pitch;

    /** コンストラクタ<br>
     * @throws IllegalArgumentException player must not be null. pitch must in -90 to 90 */
    public Pitch(final LocalPlayer player){
        if(player == null){
            throw new IllegalArgumentException("player is null", new NullPointerException());
        }

        //プレイヤーの向いている仰俯角を算出
        /* playerPitch = LEVELを0として、下を正とした回転角度 */
        float playerPitch = (float)(player.getLookAngle().get(Direction.Axis.Y)) * 90;
        if( playerPitch < DOWN || UP < playerPitch){
            //must between -90to90
            throw new IllegalArgumentException("direction must in -90to90 but was "+ pitch);
        }
        this.pitch = playerPitch;
    }

    private Pitch(float pitch){
        this.pitch = pitch;
    }

    public float value(){
        return pitch;
    }

    @Override
    public String valToString(){
        return PitchMeter.getDgrStringDecimal1(pitch);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("pitch").append(':').append(pitch);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Pitch clone() {
        return new Pitch(this.pitch);
    }
}
