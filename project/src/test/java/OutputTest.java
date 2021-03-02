import com.github.graycat27.flightHUDmod.consts.GuiTextFormat;
import com.github.graycat27.flightHUDmod.unit.Pitch;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputTest {

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
