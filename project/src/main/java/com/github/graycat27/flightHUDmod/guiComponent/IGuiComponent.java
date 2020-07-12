package com.github.graycat27.flightHUDmod.guiComponent;

public interface IGuiComponent {

    /** 部品を表示する
     * @return isDisplayed
     */
    public boolean show();

    /**
     * 部品を非表示にする
     * @return isDisplayed
     */
    public boolean hide();

}
