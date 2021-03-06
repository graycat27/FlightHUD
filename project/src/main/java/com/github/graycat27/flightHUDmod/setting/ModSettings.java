package com.github.graycat27.flightHUDmod.setting;

import com.github.graycat27.flightHUDmod.unit.Pitch;

public class ModSettings {
    /** FlightHUDの表示ON/OFF */
    private boolean showHUD = true;
    public boolean isShowHud(){
        return showHUD;
    }
    public void setShowHud(boolean willShowHud){
        this.showHUD = willShowHud;
    }

    /** 姿勢計のゲージ間隔 */
    private int interval = 15;
    private final int CHANGE_INTERVAL = 5;
    private final int MAX_INTERVAL = Pitch.UP / 2;
    private final int MIN_INTERVAL = Pitch.LEVEL;
    public int getInterval(){
        return interval;
    }
    public void addInterval(){
        if(interval + CHANGE_INTERVAL <= MAX_INTERVAL) {
            interval += CHANGE_INTERVAL;
        }
    }
    public void subInterval(){
        if(interval - CHANGE_INTERVAL > MIN_INTERVAL) {
            interval -= CHANGE_INTERVAL;
        }
    }

    /** コンストラクタ */
    public ModSettings(){
        //設定をファイルに書き出したりするならここで読み込み？
    }

}
