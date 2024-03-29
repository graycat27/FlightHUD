package com.github.graycat27.forge.flightHUDmod.unit;

import com.github.graycat27.forge.flightHUDmod.consts.GuiTextFormat;
import net.minecraft.client.player.LocalPlayer;

public class Height implements IUnit {

    /** height value, round(posY,3)<br>
     * when posY = 123.456789, value is 123456 */
    private int heightVal;

    /** calc with val to real val */
    private final int DIGIT = 1_000;

    public Height(final LocalPlayer player){
        if(player == null){
            throw new IllegalArgumentException("player is null", new NullPointerException());
        }

        double posY = player.getY();
        posY *= DIGIT;

        if(posY < Integer.MIN_VALUE || Integer.MAX_VALUE < posY){
            throw new IllegalArgumentException("posY is out of range:"+ posY);
        }
        heightVal = Math.round((long)posY);
    }

    private Height(int height){
        this.heightVal = height;
    }

    /** 高度値。精度は小数第2位まで保証 */
    public double getHeight(){
        return (double)heightVal/DIGIT;
    }

    /** 精度は整数桁のみ保証(小数第2位で四捨五入した値を取得) */
    public String valToString(){
        return String.format(GuiTextFormat.floatStr1f, getHeight());
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("height").append(':').append(valToString());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Height clone() {
        return new Height(this.heightVal);
    }
}
