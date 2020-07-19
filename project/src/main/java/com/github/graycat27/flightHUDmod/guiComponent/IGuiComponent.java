package com.github.graycat27.flightHUDmod.guiComponent;

public interface IGuiComponent {

    /** 部品を表示する。
     * 表示時点でupdateも行うこと
     */
    void show();

    /**
     * 部品を非表示にする
     */
    void hide();

    /**
     * 表示内容を更新する
     */
    void update();

}
