package com.github.graycat27.flightHUDmod.consts;

import com.github.graycat27.flightHUDmod.unit.Direction;

public enum CompassScaleValue {
    N(360),
    NNE(22.5),
    NE(45),
    ENE(90-22.5),
    E(90),
    ESE(90+22.5),
    SE(135),
    SSE(180-22.5),
    S(180),
    SSW(180+22.5),
    SW(225),
    WSW(270-22.5),
    W(270),
    WNW(270+22.5),
    NW(315),
    NNW(360-22.5)
    ;

    private double degrees;

    CompassScaleValue(double degrees){
        this.degrees = degrees;
    }

    public double value(){
        return degrees;
    }

    public static CompassScaleValue getByDegrees(double degrees){
        for(CompassScaleValue val : CompassScaleValue.values()){
            if(val.degrees == degrees){
                return val;
            }
        }
        return null;
    }
}
