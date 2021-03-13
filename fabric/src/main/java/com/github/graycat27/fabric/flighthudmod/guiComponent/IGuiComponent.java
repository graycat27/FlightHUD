package com.github.graycat27.fabric.flighthudmod.guiComponent;

import com.github.graycat27.fabric.flighthudmod.unit.IUnit;
import org.jetbrains.annotations.Nullable;

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

    /**
     * 表示している値
     * @return*/
    @Nullable
    IUnit value();

}
