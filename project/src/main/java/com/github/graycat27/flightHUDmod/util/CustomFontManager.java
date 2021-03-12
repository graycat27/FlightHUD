package com.github.graycat27.flightHUDmod.util;

import com.github.graycat27.flightHUDmod.FlightHUDMod;
import com.github.graycat27.flightHUDmod.consts.Fonts;
import com.github.graycat27.flightHUDmod.setting.GuiColor;
import com.mojang.blaze3d.matrix.MatrixStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.fonts.Font;
import net.minecraft.client.gui.fonts.FontResourceManager;
import net.minecraft.client.gui.fonts.FontTexture;
import net.minecraft.client.renderer.texture.SimpleTexture;
import net.minecraft.client.renderer.texture.Texture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.resources.IResourceManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import net.minecraftforge.fml.packs.ModFileResourcePack;
import net.minecraftforge.forgespi.locating.ModFileFactory;
import org.apache.logging.log4j.Logger;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class CustomFontManager {

    //public static final ResourceLocation fontLoc = new ResourceLocation(FlightHUDMod.MODID, Fonts.OCRB.getPath());
    public static final ResourceLocation fontLoc2 = new ResourceLocation(FlightHUDMod.MODID, Fonts.Simple.getPath());
    public static final ResourceLocation fontLoc3 = new ResourceLocation(FlightHUDMod.MODID, Fonts.KakomiB.getPath());
    //public static final ResourceLocation fontLoc4 = new ResourceLocation(FlightHUDMod.MODID, Fonts.SHINY.getPath());

    private FontRenderer fontRenderer = null;

    public CustomFontManager(){
    }

    public void bindFontTexture(){
        Logger lg = FlightHUDMod.getLogger();
        Minecraft mc = Minecraft.getInstance();
        TextureManager txMng = mc.getTextureManager();

        Texture texture = new FontTexture(fontLoc2, false);
        txMng.loadTexture(fontLoc2, texture);
        fontRenderer = new FontRenderer(resourceLocation ->
                new Font(txMng, fontLoc2)
        );

        //ModFileResourcePack modResource = new ModFileResourcePack();


//        ObfuscationReflectionHelper.getPrivateValue(FontRenderer.class, fontRenderer, "");
    }

    public void getFontFromResource() {
        if(fontRenderer == null){
            bindFontTexture();
        }

        fontRenderer.drawString(new MatrixStack(),
                "abc123TEST漢字フォント", 5, 5, GuiColor.RED.getInt());
    }
}
