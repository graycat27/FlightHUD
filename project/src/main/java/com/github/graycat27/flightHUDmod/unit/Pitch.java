package com.github.graycat27.flightHUDmod.unit;

public class Pitch {

    public static final int LEVEL = 0;

    public static final int DOWN = -90;

    public static final int UP = 90;

    /** 内部管理変数 */
    private int pitch;

    /** コンストラクタ<br>
     * セッターを呼ぶ
     * @throws IllegalArgumentException must in 1-360 */
    public Pitch(int pitch){
        if( pitch < DOWN || UP < pitch){
            //must between -90to90
            throw new IllegalArgumentException("direction must in -90to90 but was "+ pitch);
        }
        this.pitch = pitch;
    }

    public int getPitch(){
        return pitch;
    }

    @Override
    public String toString(){
        StringBuilder sb = new StringBuilder();
        sb.append('{');
        sb.append("pitch").append(':').append(pitch);
        sb.append('}');
        return sb.toString();
    }
}
