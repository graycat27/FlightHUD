package com.github.graycat27.flightHUDmod;

import com.github.graycat27.flightHUDmod.guiComponent.*;
import com.github.graycat27.flightHUDmod.unit.Speed;

public class FlightHUDGUIController {

    private Compass compass;
    private Height height;
    private PitchMeter pitchMeter;
    private SpeedMeter speedMeter;
    private Warning warning;

    //HUDの各部品を一括で制御する
    public void showAllComponent(){

        checkComponent();

        if(!compass.isDisplayed()) {
            compass.show();
        }
//        height.show();
        if(!pitchMeter.isDisplayed()){
            pitchMeter.show();
        }
        if(!speedMeter.isDisplayed()){
            speedMeter.show();
        }
//        warning.show();

    }

    public void updateAllComponent(){
        compass.update();
        pitchMeter.update();
        speedMeter.update();
    }

    public void hideAllComponent(){
        compass.hide();
        pitchMeter.hide();
        speedMeter.hide();

    }

    private void checkComponent(){
        if(compass == null){
            compass = new Compass();
            FlightHUDMod.getLogger().debug("compass has created!");
        }
//        if(height == null){
//            height = new Height();
//        }
        if(pitchMeter == null){
            pitchMeter = new PitchMeter();
            FlightHUDMod.getLogger().debug("pitch has created!");
        }
        if(speedMeter == null){
            speedMeter = new SpeedMeter();
        }
//        if(warning == null){
//            warning = new Warning();
//        }
        FlightHUDMod.getLogger().debug("GUI components check done!");
    }
}
