package com.github.graycat27.flightHUDmod.guiComponent;

import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.gui.*;

public abstract class GuiComponent extends ForgeIngameGui implements IGuiComponent {

    //field
    /** 部品が表示状態か */
    private boolean displayed = false;
    public boolean isDisplayed(){
        return displayed;
    }

//    private final ResourceLocation gui = new ResourceLocation( "");

    //constructor
    public GuiComponent(Minecraft mc)
    {
        super(mc);
    }

    //method

    @Override
    public void show(){
        update();   //value Update

        if(displayed){
            //nothing to do
            return;
        }

        //TODO makeThis
        //show GUI

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

    @Override
    public void update() {
        throw new UnsupportedOperationException();
    }

}
