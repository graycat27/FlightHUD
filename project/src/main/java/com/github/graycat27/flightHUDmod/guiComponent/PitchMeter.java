package com.github.graycat27.flightHUDmod.guiComponent;

import com.github.graycat27.flightHUDmod.FlightHUDMod;
import com.github.graycat27.flightHUDmod.consts.TextHorizontalPosition;
import com.github.graycat27.flightHUDmod.guiDisplay.IGuiValueDisplay;
import com.github.graycat27.flightHUDmod.guiDisplay.TextDisplay;
import com.github.graycat27.flightHUDmod.unit.Pitch;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

import java.util.ArrayList;
import java.util.List;

import static com.github.graycat27.flightHUDmod.consts.GuiTextFormat.pitchStr;

/**
 * 仰俯角の描画
 * （水平Level=0, 直下=-90, 真上=90）
 */
public class PitchMeter extends GuiComponent {

    /** pitch - player facing */
    private Pitch pitch = null;

    private class Line{
        public static final String mark = "+";
        public static final String angleText = "-- %s --";
    }

    private IGuiValueDisplay centerMarkTextDisplay;
    private IGuiValueDisplay pitchTextDisplay;

    private List<IGuiValueDisplay> degreesMarkTextDisplays;

    public PitchMeter(){
        super();
        initDisplayComponent();
    }

    private void initDisplayComponent(){
        Minecraft mc = Minecraft.getInstance();
        int windowWidth = mc.getMainWindow().getScaledWidth();
        int windowHeight = mc.getMainWindow().getScaledHeight();

        int centerX = windowWidth / 2;
        int centerY = windowHeight / 2;

        //center display
        int pitchWidth = mc.fontRenderer.getStringWidth(String.format(Line.angleText, getDgrString(Pitch.UP)));
        int markWidth = mc.fontRenderer.getStringWidth(Line.mark);
        int height = mc.fontRenderer.FONT_HEIGHT;
        boolean isVisible = this.isDisplayed();
        String text = "";
        TextHorizontalPosition hPos = TextHorizontalPosition.LEFT;
        pitchTextDisplay = new TextDisplay(centerX+markWidth, centerY, pitchWidth, height, isVisible, text, hPos);
        hPos = TextHorizontalPosition.CENTER;
        centerMarkTextDisplay = new TextDisplay(centerX, centerY, markWidth, height, isVisible, text, hPos);

        //each 15° display
        degreesMarkTextDisplays = new ArrayList<>();
        if(pitch != null){
            double fov = mc.gameSettings.fov;
            //fov = windowHeight view angle
            int interval = 15;

            /* 画面上辺の角度 */
            double topPitch = pitch.value() + (fov/2);
            /* 画面下辺の角度 */
            double bottomPitch = pitch.value() - (fov/2);
            int maxDgr = Pitch.LEVEL;
            while(maxDgr < Pitch.UP + (int)(fov/2)){
                maxDgr += interval;
            }
            int minDgr = Pitch.LEVEL;
            while(minDgr > Pitch.DOWN - (int)(fov/2)){
                minDgr -= interval;
            }

            for(int dgr = maxDgr; dgr >= minDgr; dgr -= interval){
                if(bottomPitch < dgr && dgr < topPitch){
                    //視野内に描画されるものを生成
                    double levelY = (windowHeight/2.0)  * Math.tan(Math.toRadians(dgr - pitch.value()))
                            / Math.tan(Math.toRadians(fov/2));

                    String angleText = String.format(Line.angleText, getDgrString(dgr));
                    if((height) > 2 * Math.abs(levelY) ){
                        angleText = angleText.substring(0, angleText.length()-2);
                        angleText += "   ";
                    }
                    int width = mc.fontRenderer.getStringWidth(angleText);
                    IGuiValueDisplay angleDisplay = new TextDisplay(centerX, (int)(centerY - levelY),
                            width, height, isVisible, angleText, hPos);
                    degreesMarkTextDisplays.add(angleDisplay);

                }
            }
        }
    }

    /** 角度値を文字列表現にして返します
     * @return 0°の場合「 0°」、他は「+1°」,「-1°」など */
    public static String getDgrString(int pitchValue){
        if(pitchValue != Pitch.LEVEL) {
            return String.format(pitchStr, pitchValue);
        }
        return " "+ pitchValue + Pitch.DEGREES;
    }


    @Override
    protected void drawDisplayComponent(){
        pitchTextDisplay.setVisible(true);
        centerMarkTextDisplay.setVisible(true);
        if(degreesMarkTextDisplays != null){
            for(IGuiValueDisplay d : degreesMarkTextDisplays){
                d.setVisible(true);
            }
        }
    }

    @Override
    public void update() {
        ClientPlayerEntity player = Minecraft.getInstance().player;
        if(player == null){
            return;
        }

        pitch = new Pitch(player);

        initDisplayComponent();
        String val = String.format(" %s -", pitch.valToString());
        pitchTextDisplay.setDispValue(val);
        centerMarkTextDisplay.setDispValue(Line.mark);
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
