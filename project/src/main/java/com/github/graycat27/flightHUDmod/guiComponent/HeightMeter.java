package com.github.graycat27.flightHUDmod.guiComponent;

import com.github.graycat27.flightHUDmod.FlightHUDMod;
import com.github.graycat27.flightHUDmod.consts.TextHorizontalPosition;
import com.github.graycat27.flightHUDmod.guiDisplay.IGuiValueDisplay;
import com.github.graycat27.flightHUDmod.guiDisplay.TextDisplay;
import com.github.graycat27.flightHUDmod.unit.Height;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

/**
 * 高度計
 */
public class HeightMeter extends GuiComponent  {

    private Height height = null;

    private IGuiValueDisplay textDisplay;

    private void resetHeight(){
        this.height = null;
    }

    public Height getHeight(){
        if(height == null){
            throw new IllegalStateException("height is unset");
        }
        return height;
    }

    public HeightMeter(){
        super();
        initDisplayComponent();
    }

    private void initDisplayComponent(){
        Minecraft mc = Minecraft.getInstance();
        int windowWidth = mc.getMainWindow().getScaledWidth();
        int windowHeight = mc.getMainWindow().getScaledHeight();

        int posX = windowWidth * 4/5;
        int posY = windowHeight / 2;
        int width = mc.fontRenderer.getStringWidth("12345678.123");
        int height = mc.fontRenderer.FONT_HEIGHT;
        boolean isVisible = false;
        String text = "";
        TextHorizontalPosition hPos = TextHorizontalPosition.RIGHT;
        textDisplay = new TextDisplay(posX, posY, width, height, isVisible, text, hPos);
    }

    @Override
    protected void drawDisplayComponent(){
        textDisplay.setVisible(true);
    }

    @Override
    public void update() {
        ClientPlayerEntity player = Minecraft.getInstance().player;

        //プレイヤーの高度を取得
        height = new Height(player);

        FlightHUDMod.getLogger().debug(getHeight().toString());
        textDisplay.setDispValue(height.valToString());
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("isDisplayed").append(':').append(isDisplayed()).append(',');
        sb.append("height").append(':').append(height.toString());
        sb.append('}');
        return sb.toString();
    }
}
