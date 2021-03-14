package com.github.graycat27.forge.flightHUDmod.guiComponent;

import com.github.graycat27.forge.flightHUDmod.FlightHUDMod;
import com.github.graycat27.forge.flightHUDmod.consts.TextHorizontalPosition;
import com.github.graycat27.forge.flightHUDmod.guiDisplay.IGuiValueDisplay;
import com.github.graycat27.forge.flightHUDmod.guiDisplay.TextDisplay;
import com.github.graycat27.forge.flightHUDmod.unit.Pitch;
import com.github.graycat27.forge.flightHUDmod.unit.Speed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

import java.util.ArrayList;
import java.util.List;

import static com.github.graycat27.forge.flightHUDmod.consts.GuiTextFormat.pitchStr;
import static com.github.graycat27.forge.flightHUDmod.consts.GuiTextFormat.pitchStrDecimal;
import static com.github.graycat27.forge.flightHUDmod.FlightHUDMod.modSettings;

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
    private IGuiValueDisplay speedPitchTextDisplay;

    private List<IGuiValueDisplay> degreesMarkTextDisplays;

    public PitchMeter(){
        super();
        initDisplayComponent();
    }

    private void initDisplayComponent(){
        Minecraft mc = Minecraft.getInstance();
        int windowWidth = mc.getMainWindow().getScaledWidth();
        int windowHeight = mc.getMainWindow().getScaledHeight();

        /* このposYから3*heightはNG */
        int denyPosYTop = Compass.getDisplayPosY() - mc.fontRenderer.FONT_HEIGHT/2;
        int denyPosYBottom = denyPosYTop + 3*mc.fontRenderer.FONT_HEIGHT;

        int posX = (int)(windowWidth * modSettings.getPositionPitch());
        int centerY = windowHeight / 2;

        //center display
        int pitchWidth = mc.fontRenderer.getStringWidth(String.format(Line.angleText, getDgrStringDecimal1(Pitch.UP)));
        int markWidth = mc.fontRenderer.getStringWidth(Line.mark);
        int height = mc.fontRenderer.FONT_HEIGHT;
        boolean isVisible = this.isDisplayed();
        String text = "";
        TextHorizontalPosition hPos = TextHorizontalPosition.LEFT;
        pitchTextDisplay = new TextDisplay(posX+markWidth, centerY, pitchWidth, height, isVisible, text, hPos);
        hPos = TextHorizontalPosition.CENTER;
        centerMarkTextDisplay = new TextDisplay(posX, centerY, markWidth, height, isVisible, text, hPos);

        //each 15° display
        degreesMarkTextDisplays = new ArrayList<>();
        final double fov = mc.gameSettings.fov;
        if(pitch != null){
            //fov = windowHeight view angle
            int interval = modSettings.getInterval();

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

                    int dispPosY = (int)(centerY - levelY);
                    if(denyPosYTop <= dispPosY && dispPosY <= denyPosYBottom){
                        //コンパスと描画が重なるものは除く
                        continue;
                    }

                    String angleText = String.format(Line.angleText, getDgrString(dgr));
                    if((height) > 2 * Math.abs(levelY) ){
                        angleText = angleText.substring(0, angleText.length()-2);
                        angleText += "   ";
                    }
                    int width = mc.fontRenderer.getStringWidth(angleText);
                    IGuiValueDisplay angleDisplay = new TextDisplay(posX, dispPosY,
                            width, height, isVisible, angleText, hPos);
                    degreesMarkTextDisplays.add(angleDisplay);

                }
            }
        }

        //movement marker
        Speed speed = FlightHUDMod.getGuiController().getSpeed();
        if(speed != null){
            float flightDegrees;
            double levelY;
            if(speed.getHorizonSpeed() != 0) {
                double actualSpeedAngleRad = Math.atan(speed.getVerticalSpeed() / speed.getHorizonSpeed());
                flightDegrees = (float) Math.toDegrees(actualSpeedAngleRad);
            }else {
                flightDegrees = speed.getVerticalSpeed() == 0 ? Pitch.LEVEL : (speed.getVerticalSpeed() > 0) ? Pitch.UP : Pitch.DOWN;
            }

            float deltaDgr = flightDegrees - pitch.value();
            if(deltaDgr > Pitch.UP){
                deltaDgr = Pitch.UP;
            }
            if(deltaDgr < Pitch.DOWN){
                deltaDgr = Pitch.DOWN;
            }   //90～180のtan値は逆符号になってしまい、表示したい位置と異なってしまう
            double delta = Math.toRadians(deltaDgr);
            levelY = (windowHeight / 2.0) * Math.tan(delta) / Math.tan(Math.toRadians(fov / 2));
            //ウィンドウ視野内に必ず描画する
            if(levelY < windowHeight / -2.0 + height){
                levelY = windowHeight / -2.0 + height;
            }
            if(levelY > windowHeight / 2.0 - height){
                levelY = windowHeight / 2.0 - height;
            }

            String flightPitch = (-0.1f < flightDegrees && flightDegrees < 0.1f) ?
                    getDgrString((int)flightDegrees) : getDgrStringDecimal1(flightDegrees);
            String flightPitchText = String.format("> %s <", flightPitch);
            int width = mc.fontRenderer.getStringWidth(flightPitchText);
            speedPitchTextDisplay = new TextDisplay(posX, (int)(centerY - levelY),
                    width, height, isVisible, flightPitchText, hPos);
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
    public static String getDgrStringDecimal1(float pitchValue){
        if(pitchValue != Pitch.LEVEL) {
            return String.format(pitchStrDecimal, pitchValue);
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
        if(speedPitchTextDisplay != null) {
            speedPitchTextDisplay.setVisible(true);
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

    @Override
    public Pitch value() {
        return pitch == null ? null : pitch.clone();
    }
}
