package com.github.graycat27.fabric.flighthudmod.guiComponent;

import com.github.graycat27.fabric.flighthudmod.FlightHUDMod;

public abstract class GuiComponent implements IGuiComponent {

    //field
    /** 部品が表示状態か */
    private boolean displayed = false;
    public boolean isDisplayed(){
        return displayed;
    }

//    private final ResourceLocation gui = new ResourceLocation( "");

    //constructor
    public GuiComponent(){
        //nothing to do
    }

    //method

    @Override
    public void show(){
        update();   //value Update

        if(displayed){
            //nothing to do
            return;
        }

        //show GUI
        try {
            drawDisplayComponent();
        }catch(UnsupportedOperationException e){
            FlightHUDMod.getLogger().error(e);
        }
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

    abstract protected void drawDisplayComponent();

}
