package com.github.graycat27.forge.flightHUDmod.util;

import com.github.graycat27.forge.flightHUDmod.setting.GuiColor;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;

public class TextRenderUtil {

    private static Minecraft mc = Minecraft.getInstance();
    private static FontRenderer fRnd = mc.fontRenderer;

    public static void drawString(String text, int posX, int posY, GuiColor color){
        fRnd.drawString(new MatrixStack(), text, (float)posX, (float)posY, color.getInt());
    }

    public static void drawStringWithShadow(String text, int posX, int posY, GuiColor color){
        fRnd.drawStringWithShadow(new MatrixStack(), text, (float)posX, (float)posY, color.getInt());
    }

    public static void drawStringWithOutLine(String text, int posX, int posY, GuiColor foreColor, GuiColor backColor){
        drawString(text, posX-1, posY, backColor);
        drawString(text, posX+1, posY, backColor);
        drawString(text, posX, posY-1, backColor);
        drawString(text, posX, posY+1, backColor);
        drawString(text, posX, posY, foreColor);
    }
}
