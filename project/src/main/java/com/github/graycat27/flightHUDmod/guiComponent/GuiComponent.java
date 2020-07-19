package com.github.graycat27.flightHUDmod.guiComponent;

import net.minecraft.client.Minecraft;
import net.minecraftforge.client.gui.*;

public abstract class GuiComponent extends ForgeIngameGui implements IGuiComponent {

    //field
    private boolean displayed = false;

    public boolean isDisplayed(){
        return displayed;
    }

    //constructor
    public GuiComponent(Minecraft mc)
    {
        super(mc);
    }

    //method

    @Override
    public void show(){
        if(displayed){
            //nothing to do
            return;
        }

        //TODO makeThis

        displayed = true;
    }

    @Override
    public void hide() {
        if(!displayed){
            //nothing to do
            return;
        }

        //TODO makeThis


        displayed = false;
    }

}
