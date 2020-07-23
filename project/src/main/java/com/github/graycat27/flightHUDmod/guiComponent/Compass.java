package com.github.graycat27.flightHUDmod.guiComponent;

import com.github.graycat27.flightHUDmod.FlightHUDMod;
import com.github.graycat27.flightHUDmod.unit.Direction;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerEntity;

/**
 * 方角（N-E-S-W 0-90-180-270-360）の描画
 */
public class Compass extends GuiComponent {

    /** 北を示す定数値 0ではなく360を用いる */
    private final Direction NORTH = new Direction(360);
    /** 東を示す定数値 */
    private final Direction EAST = new Direction(90);
    /** 南を示す定数値 */
    private final Direction SOUTH = new Direction(180);
    /** 西を示す定数値 */
    private final Direction WEST = new Direction(270);

    /** direction - player facing
     * 0は未設定を意味する（北の場合は360） */
    private Direction direction = null;


    /** setter for direction<br>
     * require dir between 1 - 360
     * @param dir direction 1-360 value
     * @throws IllegalArgumentException when param is not between 1-360 */
    private void setDirection(int dir){
        direction = new Direction(dir);
    }

    private void resetDirection(){
        this.direction = null;
    }

    private Direction getDirection() throws IllegalStateException{
        if(direction == null){
            throw new IllegalStateException("direction is unset");
        }
        return direction;
    }

    public Compass(){
        super();
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
        while(playerYaw < SOUTH.getDirection()){
            playerYaw += Direction.ROUND;
        }
        int intFlightDirection = Math.round((playerYaw + SOUTH.getDirection()) % Direction.ROUND);
        if(intFlightDirection == Direction.MIN_VAL){
            intFlightDirection = NORTH.getDirection();
        }

        setDirection(intFlightDirection);

        FlightHUDMod.getLogger().debug(getDirection());

    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("isDisplayed").append(':').append(isDisplayed()).append(',');
        sb.append("direction").append(':').append(direction.toString());
        sb.append('}');
        return sb.toString();
    }

}
