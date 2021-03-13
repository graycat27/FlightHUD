package com.github.graycat27.forge.flightHUDmod.consts;

import org.junit.Test;

import static com.github.graycat27.forge.flightHUDmod.consts.CompassScaleValue.getByDegrees;
import static org.junit.jupiter.api.Assertions.*;

public class CompassScaleValueTest {

    @Test
    public void testGetByDegrees0(){
        CompassScaleValue result = getByDegrees(0);
        assertEquals(CompassScaleValue.N, result);
    }
    @Test
    public void testGetByDegrees22(){
        CompassScaleValue result = getByDegrees(22);
        assertNull(result);
    }
    @Test
    public void testGetByDegrees22_5(){
        CompassScaleValue result = getByDegrees(22.5d);
        assertEquals(CompassScaleValue.NNE, result);
    }
    @Test
    public void testGetByDegreesMinus22_5(){
        CompassScaleValue result = getByDegrees(-22.5d);
        assertEquals(CompassScaleValue.NNW, result);
    }
    @Test
    public void testGetByDegreesOver360_45(){
        CompassScaleValue result = getByDegrees(405);
        assertEquals(CompassScaleValue.NE, result);
    }
    @Test
    public void testGetByDegreesOver720_67_5(){
        CompassScaleValue result = getByDegrees(787.5d);
        assertEquals(CompassScaleValue.ENE, result);
    }
    @Test
    public void testGetByDegreesUnder360_45(){
        CompassScaleValue result = getByDegrees(-405);
        assertEquals(CompassScaleValue.NW, result);
    }
    @Test
    public void testGetByDegreesUnder720_67_5(){
        CompassScaleValue result = getByDegrees(-787.5);
        assertEquals(CompassScaleValue.WNW, result);
    }
}