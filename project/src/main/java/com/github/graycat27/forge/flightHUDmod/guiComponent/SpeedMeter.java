package com.github.graycat27.forge.flightHUDmod.guiComponent;

import com.github.graycat27.forge.flightHUDmod.consts.TextHorizontalPosition;
import com.github.graycat27.forge.flightHUDmod.guiDisplay.IGuiValueDisplay;
import com.github.graycat27.forge.flightHUDmod.guiDisplay.TextDisplay;
import com.github.graycat27.forge.flightHUDmod.unit.Speed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

import static com.github.graycat27.forge.flightHUDmod.FlightHUDMod.modSettings;

/**
 * 速度計<br>
 * 秒速で表示する
 */
public class SpeedMeter extends GuiComponent {

    private Speed speed = null;

    private IGuiValueDisplay horizonSpeedTextDisplay;
    private IGuiValueDisplay verticalSpeedTextDisplay;
    private IGuiValueDisplay actualSpeedTextDisplay;
    private IGuiValueDisplay speedUnitDisplay;

    public SpeedMeter(){
        super();
        initDisplayComponent();
    }

    private void initDisplayComponent(){
        Minecraft mc = Minecraft.getInstance();
        int windowWidth = mc.getMainWindow().getScaledWidth();
        int windowHeight = mc.getMainWindow().getScaledHeight();

        int posX = (int)(windowWidth * modSettings.getPositionSpeed());
        int basePosY = windowHeight / 2;
        int width = mc.fontRenderer.getStringWidth("1,234.321");
        int height = mc.fontRenderer.FONT_HEIGHT;
        boolean isVisible = this.isDisplayed();
        String text = "";
        TextHorizontalPosition hPos = TextHorizontalPosition.RIGHT;

        String unitText = "[m/s]";
        int unitWidth = mc.fontRenderer.getStringWidth(unitText);
        TextHorizontalPosition unitPos = TextHorizontalPosition.LEFT;

        //init and display
        horizonSpeedTextDisplay = new TextDisplay(posX + unitWidth, basePosY, width, height, isVisible, text, hPos);
        speedUnitDisplay = new TextDisplay(posX - width, basePosY, unitWidth, height, isVisible, unitText, unitPos);

        if(speed != null && speed.getVerticalSpeed() >= 0){
            //going UP
            verticalSpeedTextDisplay = new TextDisplay(posX, basePosY-height,
                    width, height, isVisible, text, hPos);
            actualSpeedTextDisplay = new TextDisplay(posX + width*2/3, basePosY - (int)(2.5*height),
                    width, height, isVisible, text, hPos);
        }else{
            //going DOWN
            verticalSpeedTextDisplay = new TextDisplay(posX, basePosY+height,
                    width, height, isVisible, text, hPos);
            actualSpeedTextDisplay = new TextDisplay(posX + width*2/3, basePosY + (int)(2.5*height),
                    width, height, isVisible, text, hPos);
        }

    }

    @Override
    protected void drawDisplayComponent(){
        horizonSpeedTextDisplay.setVisible(true);
        verticalSpeedTextDisplay.setVisible(true);
        actualSpeedTextDisplay.setVisible(true);
        speedUnitDisplay.setVisible(true);
    }

    @Override
    public void update() {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if(player == null){
            return;
        }

        speed = new Speed(player);

        initDisplayComponent();
        horizonSpeedTextDisplay.setDispValue(speed.getHorizonSpeedValStr() +"→");
        String verticalStr = speed.getVerticalSpeedValStr();
        verticalStr += (speed.getVerticalSpeed()>=0 ? "↑": "↓");
        verticalSpeedTextDisplay.setDispValue(verticalStr);
        actualSpeedTextDisplay.setDispValue(speed.getActualSpeedValStr());
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("isDisplayed").append(':').append(isDisplayed()).append(',');
        sb.append("speed").append(':').append(speed.toString());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Speed value(){
        return speed == null ? null : speed.clone();
    }

}
