package com.github.graycat27.forge.flightHUDmod.guiDisplay;

import com.github.graycat27.forge.flightHUDmod.FlightHUDMod;
import com.github.graycat27.forge.flightHUDmod.consts.TextHorizontalPosition;
import com.github.graycat27.forge.flightHUDmod.consts.TextRenderType;
import com.github.graycat27.forge.flightHUDmod.setting.GuiColor;

import com.github.graycat27.forge.flightHUDmod.util.TextRenderUtil;
import net.minecraft.client.Minecraft;
import org.apache.commons.lang3.math.NumberUtils;

import java.util.regex.Pattern;

public class TextDisplay extends GuiDisplay implements IGuiValueDisplay {

    private String dispValue = null;
    /** 禁止パターン 改行を含む場合 */
    private static final Pattern denyPattern = Pattern.compile(".*\\R.*");
    private final TextHorizontalPosition hPos;
    private final TextRenderType renderType;

    public TextDisplay(int posX, int posY, int width, int height, boolean isVisible,
                       String text, TextHorizontalPosition hPos, TextRenderType renderType){
        super(posX, posY, width, height, isVisible);
        if(hPos == null){
            throw new IllegalArgumentException("TextDisplay constructor param was null : hPos");
        }
        if(renderType == null){
            throw new IllegalArgumentException("TextDisplay constructor param was null : renderType");
        }
        this.hPos = hPos;
        this.renderType = renderType;
        setDispValue(text);
    }
    public TextDisplay(int posX, int posY, int width, int height, boolean isVisible,
                       String text, TextHorizontalPosition hPos, TextRenderType renderType, GuiColor color){
        super(posX, posY, width, height, isVisible, color);
        if(hPos == null){
            throw new IllegalArgumentException("TextDisplay constructor param was null : hPos");
        }
        if(color == null){
            throw new IllegalArgumentException("TextDisplay constructor param was null : color");
        }
        if(renderType == null){
            throw new IllegalArgumentException("TextDisplay constructor param was null : renderType");
        }
        this.hPos = hPos;
        this.renderType = renderType;
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
            throw new IllegalArgumentException("text is too long to display : "+ text);
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

        switch (renderType){
            case SHADOW:
                TextRenderUtil.drawStringWithShadow(getDispValue(), fixPosX, fixPosY, getColor());
                break;
            case OUTLINE:
                TextRenderUtil.drawStringWithOutLine(getDispValue(), fixPosX, fixPosY, getColor(), GuiColor.BLACK);
                break;
            default:
                TextRenderUtil.drawString(getDispValue(), fixPosX, fixPosY, getColor());
        }
    }

    private boolean isAllowedPattern(String text){
        return !denyPattern.matcher(text).matches();
    }
    private boolean isDisplayableWidth(String text){
        return getDispWidth() >= Minecraft.getInstance().font.width(text);
    }

    @Override
    public TextDisplay clone(){
        TextDisplay obj = null;
        try{
            obj = new TextDisplay(getDispPosX(), getDispPosY(), getDispWidth(), getDispHeight(),
                    isVisible(), getDispValue(), this.hPos, this.renderType, getColor());
        }catch(IllegalArgumentException e){
            FlightHUDMod.getLogger().warn("couldn't make clone object", e);
        }
        return obj;
    }

    @Override
    public boolean equals(Object other){
        try {
            if(!(other instanceof TextDisplay another)) {
                return false;
            }
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
