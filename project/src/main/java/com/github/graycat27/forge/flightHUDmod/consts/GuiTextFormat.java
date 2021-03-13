package com.github.graycat27.forge.flightHUDmod.consts;

import static com.github.graycat27.forge.flightHUDmod.unit.Pitch.DEGREES;

/**
 * String.format(...)
 */
public class GuiTextFormat {

    /** 1,234.5 from Float */
    public static final String floatStr1f = "%,.1f";
    /** 1,234.568 from Float */
    public static final String floatStr3f = "%,.3f";

    /** 12° from int */
    public static final String pitchStr = "%+d"+ DEGREES;
    /** 12.3° from float */
    public static final String pitchStrDecimal = "%+.1f" + DEGREES;
}
