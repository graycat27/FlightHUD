package com.github.graycat27.flightHUDmod.unit;

import net.minecraft.client.entity.player.ClientPlayerEntity;

public class Height implements IUnit {

    /** height value, round(posY,3)<br>
     * when posY = 123.456789, value is 123456 */
    private int heightVal;

    /** calc with val to real val */
    private final int DIGIT = 1_000;

    /** 建築高度限界 y255 */
    public static final int BUILD_LIMIT = 255;
    /** 海面高度 y63 */
    public static final int SEA_LEVEL = 63;
    /** 岩盤表面高度 y5 */
    public static final int BED_LOCK = 5;
    /** 奈落高度 y0 */
    public static final int VOID = 0;
    /** 奈落ダメージ高度 y-64 */
    public static final int HURT_VOID = -64;

    public Height(ClientPlayerEntity player){
        if(player == null){
            throw new IllegalArgumentException("player is null", new NullPointerException());
        }

        double posY = player.getPosY();

        posY *= DIGIT;

        if(posY < Integer.MIN_VALUE || Integer.MAX_VALUE < posY){
            throw new IllegalArgumentException("posY is out of range:"+ posY);
        }
        heightVal = Math.round((long)posY);
    }

    /** 高度値。精度は小数第2位まで保証 */
    public double getHeight(){
        return (double)heightVal/DIGIT;
    }

    /** 精度は小数第2位まで保証(3桁取得) */
    public String valToString(){
        return String.format("%.3f", getHeight());
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("height").append(':').append(valToString());
        sb.append('}');
        return sb.toString();
    }
}
