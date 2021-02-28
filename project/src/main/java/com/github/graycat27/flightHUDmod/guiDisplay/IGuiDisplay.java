package com.github.graycat27.flightHUDmod.guiDisplay;

import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

import java.util.List;

@OnlyIn(Dist.CLIENT)
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
