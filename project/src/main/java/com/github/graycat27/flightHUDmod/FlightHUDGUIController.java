package com.github.graycat27.flightHUDmod;

import com.github.graycat27.flightHUDmod.guiComponent.*;

import java.util.List;

public class FlightHUDGUIController {

    private Compass compass;
    private Height height;
    private Pitch pitch;
    private Speed speed;
    private Warning warning;

    //HUDの各部品を一括で制御する
    public void showAllComponent(){

        checkComponent();

        if(!compass.isDisplayed()) {
            compass.show();
        }
//        height.show();
        if(!pitch.isDisplayed()){
            pitch.show();
        }
//        speed.show();
//        warning.show();

    }

    public void updateAllComponent(){
        compass.update();
        pitch.update();
    }

    public void hideAllComponent(){
        compass.hide();
        pitch.hide();

    }

    private void checkComponent(){
        if(compass == null){
            compass = new Compass();
            FlightHUDMod.getLogger().debug("compass has created!");
        }
//        if(height == null){
//            height = new Height();
//        }
        if(pitch == null){
            pitch = new Pitch();
            FlightHUDMod.getLogger().debug("pitch has created!");
        }
//        if(speed == null){
//            speed = new Speed();
//        }
//        if(warning == null){
//            warning = new Warning();
//        }
        FlightHUDMod.getLogger().debug("GUI components check done!");
    }
}
