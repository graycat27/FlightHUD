package com.github.graycat27.flightHUDmod.consts;

public enum Fonts {

    DEFAULT("default"),
    //SHINY("font/shiny-s-font.png"),
    //OCRB("font/ocrb.ttf"),
    Simple("font/simple/simple.ttf"),
    KakomiB("font/kakomib/kakomib.ttf");

    private String path;
    public String getPath(){
        return path;
    }

    Fonts(String path){
        this.path = path;
    }
}
