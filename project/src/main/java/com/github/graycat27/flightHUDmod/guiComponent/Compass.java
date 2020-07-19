package com.github.graycat27.flightHUDmod.guiComponent;

import com.github.graycat27.flightHUDmod.FlightHUDMod;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

/**
 * 方角（N-E-S-W 0-90-180-270-360）の描画
 */
public class Compass extends GuiComponent {

    /** 北を示す定数値 0ではなく360を用いる */
    private final int NORTH = 360;
    /** 東を示す定数値 */
    private final int EAST = 90;
    /** 南を示す定数値 */
    private final int SOUTH = 180;
    /** 西を示す定数値 */
    private final int WEST = 270;
    /** 未設定状態を示す定数値 */
    private final int UNSET = 0;
    /** 1周を示す定数値 */
    private final int ROUND = 360;

    /** direction - player facing
     * 0は未設定を意味する（北の場合は360） */
    private int direction = UNSET;

    /** setter for direction<br>
     * require dir between 1 - 360
     * @param dir 1-360 value
     * @throws IllegalArgumentException when param is not between 1-360 */
    private void setDirection(int dir){
        if( dir <= UNSET || ROUND < dir){
            //must between 1 - 360 (not 0)
            throw new IllegalArgumentException("direction must in 1-360 but was "+ dir);
        }
        this.direction = dir;
    }

    private void resetDirection(){
        this.direction = UNSET;
    }

    private int getDirection() throws IllegalStateException{
        if(direction == UNSET){
            throw new IllegalStateException("direction is unset");
        }
        return direction;
    }

    public Compass(){
        super(Minecraft.getInstance());
    }

    @Override
    public void show(){
        super.show();
    //    displayTitle("TESTtitle", "TESTsubTITLE", 5, 30, 3);

        update();

    }

    @Override
    public void update(){
        ClientPlayerEntity player =  Minecraft.getInstance().player;
        if(player == null){
            resetDirection();
            return;
        }

        //プレイヤーの向いている方角を算出
        /* playerYaw = SOUTH(=0)を基準に右回りを正として、回転した角度分増減する */
        float playerYaw = player.rotationYaw;
        while(playerYaw < SOUTH){
            playerYaw += ROUND;
        }
        int intFlightDirection = Math.round((playerYaw + SOUTH) % ROUND);
        if(intFlightDirection == UNSET){
            intFlightDirection = NORTH;
        }

        setDirection(intFlightDirection);

        FlightHUDMod.getLogger().debug(getDirection());

    }


}
