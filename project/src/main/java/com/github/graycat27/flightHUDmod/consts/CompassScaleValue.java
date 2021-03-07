package com.github.graycat27.flightHUDmod.consts;

import com.github.graycat27.flightHUDmod.unit.Direction;

public enum CompassScaleValue {
    N(360),
    NNE(23),
    NE(45),
    ENE(90-23),
    E(90),
    ESE(90+23),
    SE(135),
    SSE(180-23),
    S(180),
    SSW(180+23),
    SW(225),
    WSW(270-23),
    W(270),
    WNW(270+23),
    NW(315);

    private int degrees;

    CompassScaleValue(int degrees){
        this.degrees = degrees;
    }

    public int value(){
        return degrees;
    }

    public static CompassScaleValue getByDegrees(int degrees, int range){
        for(CompassScaleValue val : CompassScaleValue.values()){
            while(degrees <= Direction.MIN_VAL){
                degrees += Direction.ROUND;
            }
            while(Direction.ROUND < degrees){
                degrees -= Direction.ROUND;
            }
            if(degrees - range <= Direction.MIN_VAL){
                if(degrees - range + Direction.ROUND < val.degrees || val.degrees < degrees + range){
                    return val;
                }
            }
            if(Direction.ROUND < degrees + range){
                if(degrees - range < val.degrees || val.degrees < degrees + range - Direction.ROUND){
                    return val;
                }
            }
            if (degrees - range < val.degrees && val.degrees < degrees + range) {
                return val;
            }
        }
        return null;
    }

    public static CompassScaleValue getByDegrees(int degrees){
        for(CompassScaleValue val : CompassScaleValue.values()){
            if(val.degrees == degrees){
                return val;
            }
        }
        return null;
    }
}
