package com.github.graycat27.fabric.flighthudmod.guiComponent;

import com.github.graycat27.fabric.flighthudmod.consts.TextHorizontalPosition;
import com.github.graycat27.fabric.flighthudmod.guiDisplay.IGuiValueDisplay;
import com.github.graycat27.fabric.flighthudmod.guiDisplay.TextDisplay;
import com.github.graycat27.fabric.flighthudmod.unit.Height;
import com.github.graycat27.fabric.flighthudmod.unit.Pitch;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.network.ClientPlayerEntity;

import static com.github.graycat27.fabric.flighthudmod.FlightHUDMod.modSettings;

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
        MinecraftClient mc = MinecraftClient.getInstance();
        int windowWidth = mc.getWindow().getScaledWidth();
        int windowHeight = mc.getWindow().getScaledHeight();

        int posX = (int)(windowWidth * modSettings.getPositionHeight());
        int posY = windowHeight / 2;
        int width = mc.textRenderer.getWidth("12,345,678.123");
        int height = mc.textRenderer.fontHeight;
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
        ClientPlayerEntity player = MinecraftClient.getInstance().player;
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