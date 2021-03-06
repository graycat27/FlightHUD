package com.github.graycat27.flightHUDmod.setting;

import com.github.graycat27.flightHUDmod.FlightHUDMod;
import com.github.graycat27.flightHUDmod.consts.ConfigFile;
import com.github.graycat27.flightHUDmod.unit.Pitch;
import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Logger;

import java.io.*;
import java.util.Properties;

public class ModSettings {

    private File configFile;

    /** FlightHUDの表示ON/OFF */
    private boolean showHUD = true;
    public boolean isShowHud(){
        return showHUD;
    }
    public void setShowHud(boolean willShowHud){
        this.showHUD = willShowHud;
    }

    /** 姿勢計のゲージ間隔 */
    private int interval = 15;
    private final int CHANGE_INTERVAL = 5;
    private final int MAX_INTERVAL = Pitch.UP / 2;
    private final int MIN_INTERVAL = Pitch.LEVEL;
    public int getInterval(){
        return interval;
    }
    public void addInterval(){
        if(interval + CHANGE_INTERVAL <= MAX_INTERVAL) {
            interval += CHANGE_INTERVAL;
        }
    }
    public void subInterval(){
        if(interval - CHANGE_INTERVAL > MIN_INTERVAL) {
            interval -= CHANGE_INTERVAL;
        }
    }

    /** コンストラクタ */
    public ModSettings(){
        configFile = getConfigFile();
        readConfigFile();
    }
    /** only for constructor */
    private File getConfigFile(){
        String configDir = Minecraft.getInstance().gameDir.getAbsolutePath() + ConfigFile.configDirName;
        File dir = new File(configDir);
        FlightHUDMod.getLogger().info("flightHudMod config directory is here: "+ dir.toString());
        dir.mkdirs();
        return new File(configDir + ConfigFile.configFileName);
    }

    public void readConfigFile(){
        Logger lg = FlightHUDMod.getLogger();
        boolean successToRead = true;
        try (FileReader reader = new FileReader(configFile)){
            Properties prop = new Properties();
            prop.load(reader);

            //show
            String showStr = prop.getProperty(ConfigFile.SHOW);
            boolean showBool = showStr == null || showStr.length() == 0 || Boolean.parseBoolean(showStr);
            lg.debug("prop read: show : "+ showBool);
            this.showHUD = showBool;

            //interval
            String intervalStr = prop.getProperty(ConfigFile.PITCH_INTERVAL);
            lg.debug("prop read: interval : "+ intervalStr);
            int intervalInt = 15;
            try{
                intervalInt = Integer.parseInt(intervalStr);
            }catch(NumberFormatException e){
                lg.warn("Pitch interval is wrong value:" + intervalStr);
                successToRead = false;
            }
            this.interval = intervalInt;

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
        try(
            PrintWriter writer = new PrintWriter(new FileWriter(this.configFile))
        ){

        }
        catch(IOException e){
            FlightHUDMod.getLogger().error("Failed to save configs");
            FlightHUDMod.getLogger().error(e);
            throw new RuntimeException(e);
        }

    }

}
