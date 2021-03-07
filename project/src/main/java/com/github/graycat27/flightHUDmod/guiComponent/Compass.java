package com.github.graycat27.flightHUDmod.guiComponent;

import com.github.graycat27.flightHUDmod.consts.DirectionValue;
import com.github.graycat27.flightHUDmod.consts.TextHorizontalPosition;
import com.github.graycat27.flightHUDmod.guiDisplay.IGuiValueDisplay;
import com.github.graycat27.flightHUDmod.guiDisplay.TextDisplay;
import com.github.graycat27.flightHUDmod.unit.Direction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;

import static com.github.graycat27.flightHUDmod.FlightHUDMod.modSettings;

/**
 * 方角（N-E-S-W 0-90-180-270-360）の描画
 */
public class Compass extends GuiComponent {

    /** direction - player facing */
    private Direction direction = null;

    private IGuiValueDisplay textDisplay;
    private String facingTextFormat = "%s";

    public Compass(){
        super();
        initDisplayComponent();
    }

    private void initDisplayComponent(){
        Minecraft mc = Minecraft.getInstance();
        int windowWidth = mc.getMainWindow().getScaledWidth();
        int windowHeight = mc.getMainWindow().getScaledHeight();

        int posX = windowWidth / 2;
        int posY = (int)(windowHeight * modSettings.getPositionCompass());
        int width = mc.fontRenderer.getStringWidth("360");
        int height = mc.fontRenderer.FONT_HEIGHT;
        boolean isVisible = this.isDisplayed();
        String text = "";
        TextHorizontalPosition hPos = TextHorizontalPosition.CENTER;
        textDisplay = new TextDisplay(posX, posY, width, height, isVisible, text, hPos);
    }

    @Override
    protected void drawDisplayComponent(){
        textDisplay.setVisible(true);
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
        textDisplay.setDispValue(String.format(facingTextFormat, direction.valToString()));
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
