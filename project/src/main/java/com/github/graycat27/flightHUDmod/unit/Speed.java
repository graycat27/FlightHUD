package com.github.graycat27.flightHUDmod.unit;

import net.minecraft.client.entity.player.ClientPlayerEntity;
import net.minecraft.util.math.vector.Vector3d;

import static com.github.graycat27.flightHUDmod.consts.GuiTextFormat.floatStr3f;

public class Speed implements IUnit {

    /** Minecraft TICK par Second<br>
     * 20ticks/sec */
    private final int TPS = 20;

    /** always Positive value */
    private double horizonSpeed;
    /** Positive means going up,
     * Negative means going down */
    private double verticalSpeed;
    /** total speed, Positive value */
    private double actualSpeed;

    /**
     * コンストラクタ
     * playerを基に速度値を算出してセットする
     * @param player 算出対象のプレイヤー
     * @throws IllegalArgumentException playerがnullの場合 または、算出値が不正の場合。
     * 前者は原因にNullPointerExceptionを持つ
     */
    public Speed(ClientPlayerEntity player){
        if(player == null){
            throw new IllegalArgumentException("player is null", new NullPointerException());
        }

        Vector3d v3d = player.getMotion();

        double horizonSpeed = calcSpeed(v3d.getX(), v3d.getZ());
        double verticalSpeed = calcSpeed(v3d.getY());
        if(v3d.getY() < 0){
            verticalSpeed *= (-1);  //calcSpeed() always returns positive Val
        }

        setSpeed(horizonSpeed, verticalSpeed);
    }

    private Speed(double h, double v, double a){
        this.horizonSpeed = h;
        this.verticalSpeed = v;
        this.actualSpeed = a;
    }

    /** 1次元軸での秒速値を算出する */
    private double calcSpeed(double delta){
        return calcSpeed(delta, 0);
    }

    /** 2次元座標面での秒速値を算出する */
    private double calcSpeed(double delta1, double delta2){
        return pythagoras(delta1, delta2) * TPS;
    }

    /** 三平方(ピタゴラス)の定理による斜辺長の算出 */
    private double pythagoras(double val1, double val2){
        return Math.sqrt(val1*val1 + val2*val2);
    }

    private void setSpeed(double horizonSpeed, double verticalSpeed){
        //check
        if(horizonSpeed < 0){
            throw new IllegalArgumentException("speed xz is too low "+ horizonSpeed);
        }

        //calc for total speed
        double speed = pythagoras(horizonSpeed, verticalSpeed);

        //set
        this.horizonSpeed = horizonSpeed;
        this.verticalSpeed = verticalSpeed;
        this.actualSpeed = speed;
    }

    /** 実速度(秒速値)を返す*/
    public double getActualSpeed(){
        return actualSpeed;
    }
    public String getActualSpeedValStr(){
        return String.format(floatStr3f, actualSpeed);
    }

    /** 水平速度を返す */
    public double getHorizonSpeed(){
        return horizonSpeed;
    }
    public String getHorizonSpeedValStr(){
        return String.format(floatStr3f, horizonSpeed);
    }

    /** 鉛直速度を返す */
    public double getVerticalSpeed(){
        return verticalSpeed;
    }
    public String getVerticalSpeedValStr(){
        return String.format(floatStr3f, verticalSpeed);
    }

    @Override
    /** Horizon, Vertical, Actual<br>
     * 精度は小数第2位まで保証 */
    public String valToString(){
        StringBuilder sb = new StringBuilder();
        sb.append(getHorizonSpeedValStr()).append(';');
        sb.append(getVerticalSpeedValStr()).append(';');
        sb.append(getActualSpeedValStr());
        return sb.toString();
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("h-speed").append(':').append(getHorizonSpeed()).append(',');
        sb.append("v-speed").append(':').append(getVerticalSpeed()).append(',');
        sb.append("speed").append(':').append(getActualSpeed());
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Speed clone(){
        return new Speed(horizonSpeed, verticalSpeed, actualSpeed);
    }

}
