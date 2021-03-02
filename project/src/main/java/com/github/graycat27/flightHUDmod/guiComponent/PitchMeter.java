package com.github.graycat27.flightHUDmod.guiComponent;

import com.github.graycat27.flightHUDmod.FlightHUDMod;
import com.github.graycat27.flightHUDmod.consts.TextHorizontalPosition;
import com.github.graycat27.flightHUDmod.guiDisplay.IGuiValueDisplay;
import com.github.graycat27.flightHUDmod.guiDisplay.TextDisplay;
import com.github.graycat27.flightHUDmod.unit.Pitch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

/**
 * 仰俯角の描画
 * （水平Level=0, 直下=-90, 真上=90）
 */
public class PitchMeter extends GuiComponent {

    /** pitch - player facing */
    private Pitch pitch = null;

    private IGuiValueDisplay pitchTextDisplay;

    public PitchMeter(){
        super();
        initDisplaComponent();

    }

    private void initDisplaComponent(){
        Minecraft mc = Minecraft.getInstance();
        int windowWidth = mc.getMainWindow().getScaledWidth();
        int windowHeight = mc.getMainWindow().getScaledHeight();

        int centerX = windowWidth / 2;
        int centerY = windowHeight / 2;

        String text = "-- +90° --";
        int pitchWidth = mc.fontRenderer.getStringWidth(text);
        int height = mc.fontRenderer.FONT_HEIGHT;
        boolean isVisible = false;
        TextHorizontalPosition hPos = TextHorizontalPosition.CENTER;
        pitchTextDisplay = new TextDisplay(centerX, centerY, pitchWidth, height, isVisible, text, hPos);
    }

    @Override
    protected void drawDisplayComponent(){
        pitchTextDisplay.setVisible(true);
    }

    @Override
    public void update() {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if(player == null){
            return;
        }

        pitch = new Pitch(player);
        FlightHUDMod.getLogger().debug(pitch);

        String val = String.format("-- %s --", pitch.valToString());
        pitchTextDisplay.setDispValue(val);
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("isDisplayed").append(':').append(isDisplayed()).append(',');
        sb.append("pitch").append(':').append(pitch.toString());
        sb.append('}');
        return sb.toString();
    }

}
