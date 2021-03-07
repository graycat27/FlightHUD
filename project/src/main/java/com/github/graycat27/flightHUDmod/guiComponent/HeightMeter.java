package com.github.graycat27.flightHUDmod.guiComponent;

import com.github.graycat27.flightHUDmod.consts.TextHorizontalPosition;
import com.github.graycat27.flightHUDmod.guiDisplay.IGuiValueDisplay;
import com.github.graycat27.flightHUDmod.guiDisplay.TextDisplay;
import com.github.graycat27.flightHUDmod.unit.Height;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

import static com.github.graycat27.flightHUDmod.FlightHUDMod.modSettings;

/**
 * 高度計
 */
public class HeightMeter extends GuiComponent  {

    private Height height = null;

    private IGuiValueDisplay textDisplay;

    public HeightMeter(){
        super();
        initDisplayComponent();
    }

    private void initDisplayComponent(){
        Minecraft mc = Minecraft.getInstance();
        int windowWidth = mc.getMainWindow().getScaledWidth();
        int windowHeight = mc.getMainWindow().getScaledHeight();

        int posX = (int)(windowWidth * modSettings.getPositionHeight());
        int posY = windowHeight / 2;
        int width = mc.fontRenderer.getStringWidth("12,345,678.123");
        int height = mc.fontRenderer.FONT_HEIGHT;
        boolean isVisible = this.isDisplayed();
        String text = "";
        TextHorizontalPosition hPos = TextHorizontalPosition.LEFT;
        textDisplay = new TextDisplay(posX, posY, width, height, isVisible, text, hPos);
    }

    @Override
    protected void drawDisplayComponent(){
        textDisplay.setVisible(true);
    }

    @Override
    public void update() {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if(player == null){
            return;
        }

        //プレイヤーの高度を取得
        height = new Height(player);

        initDisplayComponent();
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

    @Override
    public Height value() {
        return height == null ? null : height.clone();
    }
}
