package com.github.graycat27.fabric.flighthudmod.unit;

public interface IUnit {
    /** 値のみを書式整形して取得する */
    String valToString();

    /** json書式にて値を返す。書式整形しない */
    @Override
    String toString();

    /** 自己の複製を返す */
    IUnit clone();
}
