package com.github.graycat27.flightHUDmod.consts;

import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class GuiTextFormatTest {

    @Test
    public void testFloatStr1f(){
        float f = 1234.56f;
        String s = String.format(GuiTextFormat.floatStr1f, f);
        assertEquals("1,234.6", s); //rounded number
    }
    @Test
    public void testFloatStr1fRoundDown(){
        float f = 1234.32f;
        String s = String.format(GuiTextFormat.floatStr1f, f);
        assertEquals("1,234.3", s); //rounded number
    }

    @Test
    public void testFloatStr3f() {
        float f = 1234.5678f;
        String s = String.format(GuiTextFormat.floatStr3f, f);
        assertEquals("1,234.568", s);
    }

    @Test
    public void testPitchPlus(){
        int i = 90;
        String s = String.format(GuiTextFormat.pitchStr, i);
        assertEquals("+90°", s);
    }
    @Test
    public void testPitchMinus(){
        int i = -90;
        String s = String.format(GuiTextFormat.pitchStr, i);
        assertEquals("-90°", s);
    }
    @Test
    public void testPitchZero() {
        int i = 0;
        String s = String.format(GuiTextFormat.pitchStr, i);
        assertEquals("+0°", s);
    }
}
