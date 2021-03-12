package com.github.graycat27.flightHUDmod;

import com.github.graycat27.flightHUDmod.guiComponent.*;
import com.github.graycat27.flightHUDmod.unit.Direction;
import com.github.graycat27.flightHUDmod.unit.Height;
import com.github.graycat27.flightHUDmod.unit.Pitch;
import com.github.graycat27.flightHUDmod.unit.Speed;
import com.github.graycat27.flightHUDmod.util.CustomFontManager;
import net.minecraft.util.datafix.fixes.CustomNameStringToComponentEntity;

public class FlightHUDGUIController {

    private CustomFontManager fontManager;
    public CustomFontManager getFontManager(){
        return fontManager;
    }

    private Compass compass;
    private HeightMeter heightMeter;
    private PitchMeter pitchMeter;
    private SpeedMeter speedMeter;
    private Warning warning;

    /** コンストラクタ */
    public FlightHUDGUIController(){
        fontManager = new CustomFontManager();
    }

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

    public Direction getDirection(){
        return compass == null ? null : compass.value();
    }
    public Speed getSpeed(){
        return speedMeter == null ? null : speedMeter.value();
    }
    public Height getHeight(){
        return heightMeter == null ? null : heightMeter.value();
    }
    public Pitch getPitch(){
        return pitchMeter == null ? null : pitchMeter.value();
    }

}
