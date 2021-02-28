package com.github.graycat27.flightHUDmod.unit;

import net.minecraft.client.entity.player.ClientPlayerEntity;

import static com.github.graycat27.flightHUDmod.consts.GuiTextFormat.floatStr3f;

public class Height implements IUnit {

    /** height value, round(posY,3)<br>
     * when posY = 123.456789, value is 123456 */
    private int heightVal;

    /** calc with val to real val */
    private final int DIGIT = 1_000;

    public Height(final ClientPlayerEntity player){
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
        return String.format(floatStr3f, getHeight());
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
