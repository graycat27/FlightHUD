package com.github.graycat27.forge.flightHUDmod.guiComponent;

import com.github.graycat27.forge.flightHUDmod.consts.TextHorizontalPosition;
import com.github.graycat27.forge.flightHUDmod.consts.TextRenderType;
import com.github.graycat27.forge.flightHUDmod.guiDisplay.IGuiValueDisplay;
import com.github.graycat27.forge.flightHUDmod.guiDisplay.TextDisplay;
import com.github.graycat27.forge.flightHUDmod.unit.Height;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;

import static com.github.graycat27.forge.flightHUDmod.FlightHUDMod.modSettings;

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
        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int windowHeight = mc.getWindow().getGuiScaledHeight();

        int posX = (int)(windowWidth * modSettings.getPositionHeight());
        int posY = windowHeight / 2;
        int width = mc.font.width("12,345,678.123");
        int height = mc.font.lineHeight;
        boolean isVisible = this.isDisplayed();
        String text = "";
        TextHorizontalPosition hPos = TextHorizontalPosition.LEFT;
        TextRenderType renderType = TextRenderType.OUTLINE;
        textDisplay = new TextDisplay(posX, posY, width, height, isVisible, text, hPos, renderType);
    }

    @Override
    protected void drawDisplayComponent(){
        textDisplay.setVisible(true);
    }

    @Override
    public void update() {
        LocalPlayer player = Minecraft.getInstance().player;
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
