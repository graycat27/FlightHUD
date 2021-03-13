package com.github.graycat27.fabric.flighthudmod.guiDisplay;

import com.github.graycat27.fabric.flighthudmod.FlightHUDMod;
import com.github.graycat27.fabric.flighthudmod.consts.TextHorizontalPosition;
import com.github.graycat27.fabric.flighthudmod.setting.GuiColor;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.math.MatrixStack;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.regex.Pattern;

public class TextDisplay extends GuiDisplay implements IGuiValueDisplay {

    private String dispValue = null;
    /** 禁止パターン 改行を含む場合 */
    private static final Pattern denyPattern = Pattern.compile(".*\\R.*");
    private final TextHorizontalPosition hPos;

    public TextDisplay(int posX, int posY, int width, int height, boolean isVisible,
                       String text, TextHorizontalPosition hPos){
        super(posX, posY, width, height, isVisible);
        if(hPos == null){
            throw new IllegalArgumentException("TextDisplay constructor param was null : hPos");
        }
        this.hPos = hPos;
        setDispValue(text);
    }
    public TextDisplay(int posX, int posY, int width, int height, boolean isVisible,
                       String text, TextHorizontalPosition hPos, GuiColor color){
        super(posX, posY, width, height, isVisible, color);
        if(hPos == null){
            throw new IllegalArgumentException("TextDisplay constructor param was null : hPos");
        }
        if(color == null){
            throw new IllegalArgumentException("TextDisplay constructor param was null : color");
        }
        this.hPos = hPos;
        setDispValue(text);
    }

    @Override
    public void setVisible(boolean willVisible){
        super.setVisible(willVisible);
        if(isVisible()){
            drawText();
        }
    }

    @Override
    public void setDispValue(Object obj){
        if(obj == null){
            throw new IllegalArgumentException("param value is null");
        }
        setDispValue(obj.toString());
    }

    private void setDispValue(String text){
        //validate
        //just single line text
        if(!isAllowedPattern(text)) {
            throw new IllegalArgumentException("text must in one line");
        }
        if(!isDisplayableWidth(text)) {
            throw new IllegalArgumentException("text is too long to display");
        }

        this.dispValue = text;
        if(isVisible()){
            drawText();
        }
    }

    public String getDispValue(){
        return dispValue;
    }

    private void drawText(){
        //posX,Y are left-top corner position
        int fixPosX = getDispPosX();
        int fixPosY = getDispPosY() - (getDispHeight() / 2);
        switch (hPos){
            case LEFT:
                break;
            case RIGHT:
                fixPosX -= (getDispWidth());
                break;
            default:  // case CENTER:
                fixPosX -= (getDispWidth()/2);
                break;
        }

        MinecraftClient.getInstance().textRenderer.drawWithShadow(new MatrixStack(),
                getDispValue(), fixPosX, fixPosY, getColor().getInt());
    }

    private boolean isAllowedPattern(String text){
        return !denyPattern.matcher(text).matches();
    }
    private boolean isDisplayableWidth(String text){
        return getDispWidth() >= MinecraftClient.getInstance().textRenderer.getWidth(text);
    }

    @Override
    public TextDisplay clone(){
        TextDisplay obj = null;
        try{
            obj = new TextDisplay(getDispPosX(), getDispPosY(), getDispWidth(), getDispHeight(),
                    isVisible(), getDispValue(), this.hPos, getColor());
        }catch(IllegalArgumentException e){
            FlightHUDMod.getLogger().warn("couldn't make clone object", e);
        }
        return obj;
    }

    @Override
    public boolean equals(Object other){
        try {
            if(!super.equals(other)) {
                return false;
            }
            if(!(other instanceof TextDisplay)) {
                return false;
            }
            TextDisplay another = (TextDisplay) other;
            if(!this.dispValue.equals(another.dispValue)) {
                return false;
            }
            if(this.hPos != another.hPos) {
                return false;
            }
        }catch(Exception e){

            NumberUtils.isDigits("param");

            FlightHUDMod.getLogger().warn("exception occurred with checking equals", e);
            return false;
        }
        return true;
    }

}
