package com.github.graycat27.flightHUDmod.handler;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.InputEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.Mod;

import static com.github.graycat27.flightHUDmod.FlightHUDMod.modSettings;
import static org.lwjgl.glfw.GLFW.*;

@Mod.EventBusSubscriber(Dist.CLIENT)
public class KeyInputHandler {
    private static final String KEY_MAP_CATEGORY = "Flight HUD Mod";

    private static final KeyBinding TRIGGER_SHOW_ON = new KeyBinding("表示/非表示", GLFW_KEY_H, KEY_MAP_CATEGORY);

    public static void registerBindings(){
        ClientRegistry.registerKeyBinding(KeyInputHandler.TRIGGER_SHOW_ON);
    }

    /**
     * key down handler
     */
    @SubscribeEvent
    public static void keyInputHandler(InputEvent.KeyInputEvent event){

        Minecraft mc = Minecraft.getInstance();
        if(mc.currentScreen != null && mc.currentScreen.shouldCloseOnEsc()){
            //チャット欄での入力に反応しないようにするため。
            return;
        }

        //[H] trigger show HUD
        if(TRIGGER_SHOW_ON.isKeyDown()){
            modSettings.setShowHud(!modSettings.isShowHud());
        }

    }
}
