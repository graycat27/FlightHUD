package com.github.graycat27.flightHUDmod.setting;

/**
 * GetColorCode with name
 */
public enum GuiColor {

    WHITE("FFFFFF"),
    RED("FF3030"),
    BLUE("00C0FF"),


    DEFAULT("00FF44");


    private final String colorCode;

    GuiColor(String colorCode){
        this.colorCode = colorCode;
    }

    public int getInt(){
        return Integer.parseInt(this.colorCode, 16);
    }


}
