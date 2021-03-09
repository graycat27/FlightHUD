package com.github.graycat27.flightHUDmod.unit;

public class Direction implements IUnit {

    /** 設定下限値 */
    public static final int MIN_VAL = 0;
    /** 1周を示す定数値 */
    public static final int ROUND = 360;

    /** 内部管理変数 */
    private int direction;

    /** コンストラクタ<br>
     * セッターを呼ぶ
     * @throws IllegalArgumentException must in 1-360 */
    public Direction(int direction){
        if( direction <= MIN_VAL || ROUND < direction){
            //must between 1 - 360 (not 0)
            throw new IllegalArgumentException("direction must in 1-360 but was "+ direction);
        }
        this.direction = direction;
    }

    public int value(){
        return direction;
    }

    @Override
    public String valToString(){
        return String.format("%03d", value());
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("direction").append(':').append(direction);
        sb.append('}');
        return sb.toString();
    }

    @Override
    public Direction clone() {
        return new Direction(this.direction);
    }
}
