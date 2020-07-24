package com.github.graycat27.flightHUDmod.guiDisplay;

import com.github.graycat27.flightHUDmod.FlightHUDMod;
import com.github.graycat27.flightHUDmod.consts.TextHorizontalPosition;
import com.github.graycat27.flightHUDmod.setting.GuiColor;

import net.minecraft.client.Minecraft;

import java.util.regex.Pattern;

public class TextDisplay extends GuiDisplay {

    private String dispValue = null;
    /** 禁止パターン 改行を含む場合 */
    private Pattern denyPattern = Pattern.compile(".*\\R.*");
    private int MARGIN = Minecraft.getInstance().fontRenderer.getStringWidth(" ");
    private TextHorizontalPosition hPos;

    public TextDisplay(int posX, int posY, int width, int height, boolean isVisible,
                       String text, TextHorizontalPosition hPos){
        super(posX, posY, width, height, isVisible);
        if(hPos == null){
            throw new IllegalArgumentException("TextDisplay constructor param was null : hPos");
        }
        setDispValue(text);
        this.hPos = hPos;
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
        setDispValue(text);
        this.hPos = hPos;
    }

    private void setDispValue(String text){
        //validate
        //just single line text
        if(text == null){
            throw new IllegalArgumentException("text is null");
        }
        if(!isAllowedPattern(text)) {
            throw new IllegalArgumentException("text must in one line");
        }
        if(!isDisplayableWidth(text)) {
            throw new IllegalArgumentException("text is too long to display");
        }

        this.dispValue = text;
    }

    public String getDispValue(){
        return dispValue;
    }

    private void drawText(){
        int color = getColor().getInt();
        switch (hPos){
            case LEFT:
                //renderer, text, x, y, color
                super.drawString(getFontRenderer(), dispValue, getDispPosX(), getDispPosY(), color);
                break;
            case RIGHT:
                super.drawRightAlignedString(getFontRenderer(), dispValue, getDispPosX(), getDispPosY(), color);
                break;
            default:  // case CENTER:
                super.drawCenteredString(getFontRenderer(), dispValue, getDispPosX(), getDispPosY(), color);
        }
    }

    private boolean isAllowedPattern(String text){
        return !denyPattern.matcher(text).matches();
    }
    private boolean isDisplayableWidth(String text){
        return (getDispWidth() - MARGIN*2) > Minecraft.getInstance().fontRenderer.getStringWidth(text);
    }

    @Override
    public TextDisplay clone(){
        TextDisplay obj = null;
        try{
            obj = new TextDisplay(getDispPosX(), getDispPosY(), getDispWidth(), getDispHeight(),
                    isVisible(), getDispValue(), this.hPos, getColor());
        }catch(IllegalArgumentException e){
            FlightHUDMod.getLogger().warn("couldn`t make clone object", e);
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
            FlightHUDMod.getLogger().warn("exception occurred with checking equals", e);
            return false;
        }
        return true;
    }

}
