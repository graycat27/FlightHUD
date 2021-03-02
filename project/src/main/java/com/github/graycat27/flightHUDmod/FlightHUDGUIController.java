package com.github.graycat27.flightHUDmod;

import com.github.graycat27.flightHUDmod.guiComponent.*;

public class FlightHUDGUIController {

    private Compass compass;
    private HeightMeter heightMeter;
    private PitchMeter pitchMeter;
    private SpeedMeter speedMeter;
    private Warning warning;

    //HUDの各部品を一括で制御する
    public void showAllComponent(){

        checkComponent();

        if(!compass.isDisplayed()) {
            compass.show();
        }
        if(!heightMeter.isDisplayed()){
            heightMeter.show();
        }
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
        heightMeter.update();
        pitchMeter.update();
        speedMeter.update();
    }

    public void hideAllComponent(){
        if(compass != null && compass.isDisplayed()){
            compass.hide();
        }
        if(heightMeter != null && heightMeter.isDisplayed()){
            heightMeter.hide();
        }
        if(pitchMeter != null && pitchMeter.isDisplayed()){
            pitchMeter.hide();
        }
        if(speedMeter != null && speedMeter.isDisplayed()){
            speedMeter.hide();
        }
    }

    private void checkComponent(){
        if(compass == null){
            compass = new Compass();
        }
        if(heightMeter == null){
            heightMeter = new HeightMeter();
        }
        if(pitchMeter == null){
            pitchMeter = new PitchMeter();
        }
        if(speedMeter == null){
            speedMeter = new SpeedMeter();
        }
//        if(warning == null){
//            warning = new Warning();
//        }
    }
}
