package com.github.graycat27.flightHUDmod.setting;

public class ModSettings {
    /** FlightHUDの表示ON/OFF */
    private boolean showHUD = true;
    public boolean isShowHud(){
        return showHUD;
    }
    public void setShowHud(boolean willShowHud){
        this.showHUD = willShowHud;
    }

    /** コンストラクタ */
    public ModSettings(){
        //設定をファイルに書き出したりするならここで読み込み？
    }

}
