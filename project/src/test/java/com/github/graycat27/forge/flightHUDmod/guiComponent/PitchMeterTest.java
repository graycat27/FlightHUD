package com.github.graycat27.forge.flightHUDmod.guiComponent;

import org.junit.Test;

import static org.junit.jupiter.api.Assertions.*;

public class PitchMeterTest {

    private final float f1 = 45.678f;
    private final float f0 = 0.0f;
    private final float fn1 = -45.678f;

    /* getDgrString */
    @Test
    public void testGetDgrString(){
        String s = PitchMeter.getDgrString((int)f0);
        assertEquals(" 0°", s);
    }
    @Test
    public void testGetDgrString1(){
        String s = PitchMeter.getDgrString((int)f1);
        assertEquals("+45°", s);    //intキャストで切り捨て
    }
    @Test
    public void testGetDgrString2(){
        String s = PitchMeter.getDgrString((int)fn1);
        assertEquals("-45°", s);
    }

    /* getDgrStringDecimal1 */
    @Test
    public void testGetDgrStringDecimal1(){
        String s = PitchMeter.getDgrStringDecimal1(f0);
        assertEquals(" 0.0°", s);
    }
    @Test
    public void testGetDgrStringDecimal11(){
        String s = PitchMeter.getDgrStringDecimal1(f1);
        assertEquals("+45.7°", s);  //formatで四捨五入
    }
    @Test
    public void testGetDgrStringDecimal12(){
        String s = PitchMeter.getDgrStringDecimal1(fn1);
        assertEquals("-45.7°", s);
    }

}