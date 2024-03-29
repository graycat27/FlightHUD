package com.github.graycat27.forge.flightHUDmod.setting;

import com.github.graycat27.forge.flightHUDmod.FlightHUDMod;
import com.github.graycat27.forge.flightHUDmod.consts.ConfigFile;
import com.github.graycat27.forge.flightHUDmod.unit.Pitch;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;

public class ModSettings {

    private File configFile;

    private static class DefaultValue{
        static boolean showHUD = true;
        static int interval = 15;
        static int positionCompass = 25;
        static int positionHeight = 67;
        static int positionPitch = 50;
        static int positionSpeed = 33;
    }

    /** FlightHUDの表示ON/OFF */
    private boolean showHUD = DefaultValue.showHUD;
    public boolean isShowHud(){
        return showHUD;
    }
    public void setShowHud(boolean willShowHud){
        this.showHUD = willShowHud;
        saveConfigFile();
    }

    /** 姿勢計のゲージ間隔 */
    private int interval = DefaultValue.interval;
    private final int CHANGE_INTERVAL = 5;
    private final int MAX_INTERVAL = Pitch.UP / 2;
    private final int MIN_INTERVAL = Pitch.LEVEL;
    public int getInterval(){
        return interval;
    }
    public void addInterval(){
        if(interval + CHANGE_INTERVAL <= MAX_INTERVAL) {
            interval += CHANGE_INTERVAL;
            saveConfigFile();
        }
    }
    public void subInterval(){
        if(interval - CHANGE_INTERVAL > MIN_INTERVAL) {
            interval -= CHANGE_INTERVAL;
            saveConfigFile();
        }
    }

    /* 表示位置 */
    /** %値 */
    private int positionCompass = DefaultValue.positionCompass;
    private int positionHeight = DefaultValue.positionHeight;
    private int positionPitch = DefaultValue.positionPitch;
    private int positionSpeed = DefaultValue.positionSpeed;
    /** Compassの表示位置指定(上下移動)を返す。0.00～1.00の%値 */
    public double getPositionCompass(){
        return positionCompass / 100d;
    }
    /** 高度計の表示位置指定(左右移動)を%値で返す。0.00～1.00の%値 */
    public double getPositionHeight(){
        return positionHeight / 100d;
    }
    /** 姿勢計の表示位置指定(左右移動)を%値で返す。0.00～1.00の%値 */
    public double getPositionPitch(){
        return positionPitch / 100d;
    }
    /** 速度計の表示位置指定(左右移動)を%値で返す。0.00～1.00の%値 */
    public double getPositionSpeed(){
        return positionSpeed / 100d;
    }

    /** コンストラクタ */
    public ModSettings(){
        configFile = getConfigFile();
        readConfigFile();
    }
    /** only for constructor */
    private File getConfigFile(){
        String configDir = Minecraft.getInstance().gameDirectory.getAbsolutePath() + ConfigFile.configDirName;
        File dir = new File(configDir);
        FlightHUDMod.getLogger().info("flightHudMod config directory is here: "+ dir.toString());
        dir.mkdirs();
        return new File(configDir + File.separator + ConfigFile.configFileName);
    }

    public void readConfigFile(){
        Logger lg = FlightHUDMod.getLogger();
        boolean successToRead = true;
        try (FileReader reader = new FileReader(configFile)){
            Properties prop = new Properties();
            prop.load(reader);

            //show
            String showStr = prop.getProperty(ConfigFile.SHOW);
            boolean showBool = (showStr == null) || (showStr.length() == 0) || Boolean.parseBoolean(showStr);
            lg.debug("prop read: show : "+ showBool);
            this.showHUD = showBool;

            //interval
            String intervalStr = prop.getProperty(ConfigFile.PITCH_INTERVAL);
            lg.debug("prop read: interval : "+ intervalStr);
            int intervalInt = DefaultValue.interval;
            try{
                intervalInt = Integer.parseInt(intervalStr);
            }catch(NumberFormatException e){
                lg.warn("Pitch interval is wrong value:" + intervalStr);
                successToRead = false;
            }
            this.interval = intervalInt;

            //position
            {
                //position.compass
                String compassPosStr = prop.getProperty(ConfigFile.COMPASS);
                int compassDbl = DefaultValue.positionCompass;
                try {
                    compassDbl = Integer.parseInt(compassPosStr);
                } catch (NumberFormatException e) {
                    lg.warn("compass position is wrong value:" + compassPosStr);
                    successToRead = false;
                }
                this.positionCompass = compassDbl;
                //position.height
                String heightPosStr = prop.getProperty(ConfigFile.HEIGHT);
                int heightDbl = DefaultValue.positionHeight;
                try {
                    heightDbl = Integer.parseInt(heightPosStr);
                } catch (NumberFormatException e) {
                    lg.warn("height position is wrong value:" + heightPosStr);
                    successToRead = false;
                }
                this.positionHeight = heightDbl;
                //position.pitch
                String pitchPosStr = prop.getProperty(ConfigFile.PITCH);
                int pitchDbl = DefaultValue.positionPitch;
                try {
                    pitchDbl = Integer.parseInt(pitchPosStr);
                } catch (NumberFormatException e) {
                    lg.warn("pitch position is wrong value:" + pitchPosStr);
                    successToRead = false;
                }
                this.positionPitch = pitchDbl;
                //position.speed
                String speedPosStr = prop.getProperty(ConfigFile.SPEED);
                int speedDbl = DefaultValue.positionSpeed;
                try {
                    speedDbl = Integer.parseInt(speedPosStr);
                } catch (NumberFormatException e) {
                    lg.warn("speed position is wrong value:" + speedPosStr);
                    successToRead = false;
                }
                this.positionSpeed = speedDbl;
            }

        }catch(FileNotFoundException e){
            FlightHUDMod.getLogger().info("config file not found. create and save default props.");
            successToRead = false;
        }
        catch(IOException e){
            FlightHUDMod.getLogger().error("error at reading config file");
            FlightHUDMod.getLogger().error(e);
            successToRead = false;
        }
        if(!successToRead) {
            saveConfigFile();
        }
    }

    public void saveConfigFile(){
        if(configFile.exists()){
            FlightHUDMod.getLogger().info("over write config file");
            configFile.delete();
        }else {
            FlightHUDMod.getLogger().info("generate new config file");
        }
        try {
            configFile.createNewFile();
        }catch(IOException e){
            throw new RuntimeException(e);
        }
        try(
            PrintWriter writer = new PrintWriter(new FileWriter(this.configFile))
        ){
            writer.println("# Flight HUD Mod Config");
            writer.print("# generated at ");
            writer.println(new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(new Date()));
            writer.println();

            writer.println(ConfigFile.SHOW + ":" + this.showHUD);
            writer.println(ConfigFile.PITCH_INTERVAL +":"+ this.interval);
            writer.println(ConfigFile.COMPASS +":"+ this.positionCompass);
            writer.println(ConfigFile.HEIGHT +":"+ this.positionHeight);
            writer.println(ConfigFile.PITCH +":"+ this.positionPitch);
            writer.println(ConfigFile.SPEED +":"+ this.positionSpeed);

        }
        catch(IOException e){
            FlightHUDMod.getLogger().error("Failed to save configs");
            FlightHUDMod.getLogger().error(e);
            throw new RuntimeException(e);
        }

    }

}
