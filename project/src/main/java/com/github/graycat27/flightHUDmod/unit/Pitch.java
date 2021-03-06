package com.github.graycat27.flightHUDmod.unit;

import net.minecraft.client.entity.player.ClientPlayerEntity;

import static com.github.graycat27.flightHUDmod.guiComponent.PitchMeter.getDgrString;

public class Pitch implements IUnit {

    public static final String DEGREES = "°";

    public static final int LEVEL = 0;

    public static final int DOWN = -90;

    public static final int UP = 90;

    /** 内部管理変数。仰俯角。下向きが正 */
    private int pitch;

    /** コンストラクタ<br>
     * @throws IllegalArgumentException player must not be null. pitch must in -90 to 90 */
    public Pitch(final ClientPlayerEntity player){
        if(player == null){
            throw new IllegalArgumentException("player is null", new NullPointerException());
        }

        //プレイヤーの向いている仰俯角を算出
        /* playerPitch = LEVELを0として、下を正とした回転角度 */
        float playerPitch = player.rotationPitch;
        int intFlightPitch = Math.round((-1)*playerPitch);
        if( intFlightPitch < DOWN || UP < intFlightPitch){
            //must between -90to90
            throw new IllegalArgumentException("direction must in -90to90 but was "+ pitch);
        }
        this.pitch = intFlightPitch;
    }

    private Pitch(int pitch){
        this.pitch = pitch;
    }

    public int value(){
        return pitch;
    }

    @Override
    public String valToString(){
        return getDgrString(pitch);
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
