package com.github.graycat27.flightHUDmod.guiComponent;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.*;

public abstract class GuiComponent extends ForgeIngameGui implements IGuiComponent {

    //field



    //constructor
    public GuiComponent(Minecraft mc)
    {
        super(mc);
    }

    //method

    @Override
    public boolean show(){
        //TODO makeThis

        return false;
    }

    @Override
    public boolean hide() {
        //TODO makeThis

        return false;
    }
}
