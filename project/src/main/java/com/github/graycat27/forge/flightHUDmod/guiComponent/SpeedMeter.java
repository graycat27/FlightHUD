package com.github.graycat27.forge.flightHUDmod.guiComponent;

import com.github.graycat27.forge.flightHUDmod.consts.TextHorizontalPosition;
import com.github.graycat27.forge.flightHUDmod.consts.TextRenderType;
import com.github.graycat27.forge.flightHUDmod.guiDisplay.IGuiValueDisplay;
import com.github.graycat27.forge.flightHUDmod.guiDisplay.TextDisplay;
import com.github.graycat27.forge.flightHUDmod.unit.Speed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.Position;
import net.minecraft.core.PositionImpl;

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

    private Position lastTickPlayerPos;

    public SpeedMeter(){
        super();
        lastTickPlayerPos = null;
    }

    private void initDisplayComponent(){
        Minecraft mc = Minecraft.getInstance();
        int windowWidth = mc.getWindow().getGuiScaledWidth();
        int windowHeight = mc.getWindow().getGuiScaledHeight();

        int posX = (int)(windowWidth * modSettings.getPositionSpeed());
        int basePosY = windowHeight / 2;
        int width = mc.font.width("123,456.321");
        int height = mc.font.lineHeight;
        boolean isVisible = this.isDisplayed();
        String text = "";
        TextHorizontalPosition hPos = TextHorizontalPosition.RIGHT;

        String unitText = "[m/s]";
        int unitWidth = mc.font.width(unitText);
        TextHorizontalPosition unitPos = TextHorizontalPosition.LEFT;
        TextRenderType rTy = TextRenderType.SHADOW;

        //init and display
        speedUnitDisplay = new TextDisplay(posX - width, basePosY, unitWidth, height, isVisible, unitText, unitPos, rTy);
        rTy = TextRenderType.OUTLINE;
        horizonSpeedTextDisplay = new TextDisplay(posX + 1 + unitWidth, basePosY, width, height, isVisible, text, hPos, rTy);

        if(speed != null && speed.getVerticalSpeed() >= 0){
            //going UP
            verticalSpeedTextDisplay = new TextDisplay(posX, basePosY-height,
                    width, height, isVisible, text, hPos, rTy);
            actualSpeedTextDisplay = new TextDisplay(posX + width*2/3, basePosY - (int)(2.5*height),
                    width, height, isVisible, text, hPos, rTy);
        }else{
            //going DOWN
            verticalSpeedTextDisplay = new TextDisplay(posX, basePosY+height,
                    width, height, isVisible, text, hPos, rTy);
            actualSpeedTextDisplay = new TextDisplay(posX + width*2/3, basePosY + (int)(2.5*height),
                    width, height, isVisible, text, hPos, rTy);
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
        LocalPlayer player = Minecraft.getInstance().player;
        if(player == null){
            return;
        }
        if( lastTickPlayerPos != null &&
            player.getX() == lastTickPlayerPos.x() &&
            player.getY() == lastTickPlayerPos.y() &&
            player.getZ() == lastTickPlayerPos.z()){
            // do not update value when the position is same
        }else {

            speed = new Speed(player, lastTickPlayerPos);
            lastTickPlayerPos = new PositionImpl(player.getX(), player.getY(), player.getZ());
        }
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
