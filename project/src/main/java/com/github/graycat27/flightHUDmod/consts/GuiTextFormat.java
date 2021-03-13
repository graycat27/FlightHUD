package com.github.graycat27.flightHUDmod.consts;

import static com.github.graycat27.flightHUDmod.unit.Pitch.DEGREES;

/**
 * String.format(...)
 */
public class GuiTextFormat {

    /** 1,234.5 from Float */
    public static final String floatStr1f = "%,.1f";

    /** 1,234.568 from Float */
    public static final String floatStr3f = "%,.3f";

    public static final String pitchStr = "%+d"+ DEGREES;
}
