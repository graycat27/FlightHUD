package com.github.graycat27.forge.flightHUDmod.guiComponent;

import com.github.graycat27.forge.flightHUDmod.consts.CompassScaleValue;
import com.github.graycat27.forge.flightHUDmod.consts.DirectionValue;
import com.github.graycat27.forge.flightHUDmod.consts.TextHorizontalPosition;
import com.github.graycat27.forge.flightHUDmod.guiDisplay.IGuiValueDisplay;
import com.github.graycat27.forge.flightHUDmod.guiDisplay.TextDisplay;
import com.github.graycat27.forge.flightHUDmod.unit.Direction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

import static com.github.graycat27.forge.flightHUDmod.FlightHUDMod.modSettings;

import java.util.ArrayList;
import java.util.List;

/**
 * 方角（N-E-S-W 0-90-180-270-360）の描画
 */
public class Compass extends GuiComponent {

    /** direction - player facing */
    private Direction direction = null;

    private IGuiValueDisplay degreesDisplay;
    private List<IGuiValueDisplay> scaleDisplayList;

    private String facingTextFormat = "%s";

    public Compass(){
        super();
        initDisplayComponent();
    }

    private void initDisplayComponent(){
        Minecraft mc = Minecraft.getInstance();
        int windowWidth = mc.getMainWindow().getScaledWidth();
        int windowHeight = mc.getMainWindow().getScaledHeight();

        final int centerPosX = windowWidth / 2;
        int posY = (int)(windowHeight * modSettings.getPositionCompass());
        int width = mc.fontRenderer.getStringWidth("360");
        int height = mc.fontRenderer.FONT_HEIGHT;
        boolean isVisible = this.isDisplayed();
        String text = "";
        TextHorizontalPosition hPos = TextHorizontalPosition.CENTER;
        degreesDisplay = new TextDisplay(centerPosX, posY + 2*height, width, height, isVisible, text, hPos);

        //scale
        scaleDisplayList = new ArrayList<>();
        int halfWidthPx = windowWidth / 2;
        double fov = mc.gameSettings.fov;
        int widthDgr = (int)Math.round(halfWidthPx * fov / windowHeight);   //画面の半分の幅に収まる視野角

        String bar = "-";
        int charWidth = mc.fontRenderer.getStringWidth(bar);

        StringBuilder scaleBuilder = new StringBuilder();
        for(int i = 0; i < halfWidthPx/charWidth; i++){
            scaleBuilder.append(bar);
        }
        text = scaleBuilder.toString();
        width = mc.fontRenderer.getStringWidth(text);
        scaleDisplayList.add(new TextDisplay(centerPosX, posY+height, width, height, isVisible, text, hPos));

        //中心マーク
        text = "‡";
        width = mc.fontRenderer.getStringWidth(text);
        scaleDisplayList.add(new TextDisplay(centerPosX, posY+height, width, height, isVisible, text, hPos));

        if(direction != null) {
            final double leftDirection = direction.value() - widthDgr / 2.0;
            final double rightDirection = direction.value() + widthDgr / 2.0;

            final double pxParDgr = windowHeight / fov;

            for (CompassScaleValue v : CompassScaleValue.values()) {
                //表示不要な方角は生成しない
                if(leftDirection <= v.value() && v.value() <= rightDirection) {
                    int deltaX = (int)((v.value() - direction.value()) * pxParDgr);
                    text = v.toString();
                    width = mc.fontRenderer.getStringWidth(text);
                    scaleDisplayList.add(new TextDisplay(centerPosX + deltaX, posY,
                            width, height, isVisible, text, hPos));
                    text = "+";
                    width =mc.fontRenderer.getStringWidth(text);
                    scaleDisplayList.add(new TextDisplay(centerPosX + deltaX, posY + height,
                            width, height, isVisible, text, hPos));
                    continue;
                }
                if(leftDirection - Direction.ROUND <= v.value() && v.value() < rightDirection - Direction.ROUND){
                    int deltaX = (int)((v.value() - direction.value() + Direction.ROUND) * pxParDgr);
                    text = v.toString();
                    width = mc.fontRenderer.getStringWidth(text);
                    scaleDisplayList.add(new TextDisplay(centerPosX + deltaX, posY,
                            width, height, isVisible, text, hPos));
                    text = "+";
                    width =mc.fontRenderer.getStringWidth(text);
                    scaleDisplayList.add(new TextDisplay(centerPosX + deltaX, posY + height,
                            width, height, isVisible, text, hPos));
                    continue;
                }
                if(leftDirection + Direction.ROUND <= v.value() && v.value() < rightDirection + Direction.ROUND){
                    int deltaX = (int)((v.value() - direction.value() - Direction.ROUND) * pxParDgr);
                    text = v.toString();
                    width = mc.fontRenderer.getStringWidth(text);
                    scaleDisplayList.add(new TextDisplay(centerPosX + deltaX, posY,
                            width, height, isVisible, text, hPos));
                    text = "+";
                    width =mc.fontRenderer.getStringWidth(text);
                    scaleDisplayList.add(new TextDisplay(centerPosX + deltaX, posY + height,
                            width, height, isVisible, text, hPos));
                    continue;
                }
            }
        }

    }

    @Override
    protected void drawDisplayComponent(){
        degreesDisplay.setVisible(true);
    }

    @Override
    public void update(){
        ClientPlayerEntity player =  Minecraft.getInstance().player;
        if(player == null){
            return;
        }

        //プレイヤーの向いている方角を算出
        /* playerYaw = SOUTH(=0)を基準に右回りを正として、回転した角度分増減する */
        float playerYaw = player.rotationYaw;
        while(playerYaw < DirectionValue.SOUTH){
            playerYaw += Direction.ROUND;
        }
        int intFlightDirection = Math.round((playerYaw + DirectionValue.SOUTH) % Direction.ROUND);
        if(intFlightDirection == Direction.MIN_VAL){
            intFlightDirection = DirectionValue.NORTH;
        }

        direction = new Direction(intFlightDirection);

        initDisplayComponent();
        degreesDisplay.setDispValue(String.format(facingTextFormat, direction.valToString()));
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("isDisplayed").append(':').append(isDisplayed()).append(',');
        sb.append("direction").append(':').append(direction.toString());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Direction value() {
        return direction == null ? null : direction.clone();
    }
}
