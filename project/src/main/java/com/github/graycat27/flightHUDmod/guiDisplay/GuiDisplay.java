package com.github.graycat27.flightHUDmod.guiDisplay;


import com.github.graycat27.flightHUDmod.FlightHUDMod;
import com.github.graycat27.flightHUDmod.setting.GuiColor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.StringUtils;
import net.minecraftforge.client.gui.ForgeIngameGui;
import org.lwjgl.system.CallbackI;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * subclasses must override method "equals","clone"
 */
//abstract public class GuiDisplay extends ForgeIngameGui implements IGuiDisplay {
abstract public class GuiDisplay extends AbstractGui implements IGuiDisplay {

    /** リソースフォルダパス */
    protected String resourcePath = "";
    protected String generateResourcePath(String fileName){
        if(StringUtils.isNullOrEmpty(fileName)){
            String errMsg = "param 'fileName' was empty";
            FlightHUDMod.getLogger().warn(errMsg);
            throw new NullPointerException(errMsg);
        }
        StringBuilder sb = new StringBuilder();
        sb.append(FlightHUDMod.MODID).append(':')
                .append(color.name()).append(File.pathSeparator)
                .append(fileName);
        return sb.toString();
    }


    /** 表示位置x */
    private int dispPosX;
    /** 表示位置y */
    private int dispPosY;
    /** 表示幅 */
    private int dispWidth;
    /** 表示高さ */
    private int dispHeight;
    /** 表示文字色 */
    private GuiColor color;

    /** 子要素リスト */
    public List<IGuiDisplay> children = new ArrayList<>();
    /** 親要素 */
    private IGuiDisplay parent;

    /** 可視？ */
    private boolean visible;

    public GuiDisplay(int posX, int posY, int width, int height, boolean isVisible){
        //super(Minecraft.getInstance());
        super();
        dispPosX = posX;
        dispPosY = posY;
        dispWidth = width;
        dispHeight = height;
        visible = isVisible;
        color = GuiColor.DEFAULT;
    }
    public GuiDisplay(int posX, int posY, int width, int height, boolean isVisible, GuiColor color){
        //super(Minecraft.getInstance());
        super();
        dispPosX = posX;
        dispPosY = posY;
        dispWidth = width;
        dispHeight = height;
        visible = isVisible;
        this.color = color;
    }

    @Override
    public int getDispPosX(){
        return dispPosX;
    }
    @Override
    public int getDispPosY(){
        return dispPosY;
    }
    @Override
    public int getDispWidth(){
        return dispWidth;
    }
    @Override
    public int getDispHeight(){
        return dispHeight;
    }

    protected GuiColor getColor(){
        return color;
    }

    @Override
    public void addChildren(IGuiDisplay child){
        if(children.contains(child)){
            return;
        }
        children.add(child.clone());
    }
    @Override
    public void removeChildren(IGuiDisplay target){
        children.remove(target);
    }
    @Override
    public List<IGuiDisplay> getChildren(){
        List<IGuiDisplay> res = new ArrayList<>();
        for(IGuiDisplay child : children){
            res.add(child.clone());
        }
        return res;
    }
    @Override
    public IGuiDisplay getChildren(int index){
        return children.get(index).clone();
    }
    @Override
    public IGuiDisplay getParent(){
        return parent.clone();
    }

    @Override
    public void setVisible(boolean willVisible){
        visible = willVisible;
        if(!visible){

        }
    }
    @Override
    public boolean isVisible(){
        return visible;
    }

    /** visible は判定対象としない */
    @Override
    public boolean equals(Object other){
        try {
            if(!(other instanceof GuiDisplay)) {
                return false;
            }
            GuiDisplay another = (GuiDisplay) other;
            if(this.getDispPosX() != another.getDispPosX() ||
                    this.getDispPosY() != another.getDispPosY() ||
                    this.getDispWidth() != another.getDispWidth() ||
                    this.getDispHeight() != another.getDispHeight() ||
                    this.color != another.color
            ) {
                return false;
            }
            if(!this.parent.equals(another.parent)) {
                return false;
            }
            if(!this.children.equals(another.children)) {
                return false;
            }
        }catch (Exception e){
            FlightHUDMod.getLogger().warn("exception occurred with checking equals", e);
            return false;
        }
        return true;
    }

    abstract public GuiDisplay clone();
}
