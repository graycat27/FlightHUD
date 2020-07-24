package com.github.graycat27.flightHUDmod.guiDisplay;

import java.util.List;

public interface IGuiDisplay {

    int getDispPosX();
    int getDispPosY();

    int getDispWidth();
    int getDispHeight();

    void addChildren(IGuiDisplay child);
    void removeChildren(IGuiDisplay target);
    List<IGuiDisplay> getChildren();
    IGuiDisplay getChildren(int index);

    IGuiDisplay getParent();

    void setVisible(boolean willVisible);
    boolean isVisible();

    IGuiDisplay clone();
    boolean equals(Object other);


}
