import com.github.graycat27.flightHUDmod.consts.GuiTextFormat;
import org.junit.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class OutputTest {

    @Test
    public void testFloatStr3f() {
        float f = 1234.5678f;
        String s = String.format(GuiTextFormat.floatStr3f, f);
        assertEquals("1,234.568", s);
    }
}
