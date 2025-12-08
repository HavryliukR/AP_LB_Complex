package tourapp.ui.command;

import org.junit.jupiter.api.Test;
import tourapp.model.TourType;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ConsoleInputUtilsTest {

    @Test
    void readNonEmptyStringSkipsEmptyLines() {
        String input = "   \n\nHello\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        String result = ConsoleInputUtils.readNonEmptyString(scanner, "Enter: ");
        assertEquals("Hello", result);
    }

    @Test
    void readIntInRangeSkipsInvalidAndOutOfRange() {
        String input = "abc\n100\n5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        int result = ConsoleInputUtils.readIntInRange(scanner, "Enter: ", 1, 10);
        assertEquals(5, result);
    }

    @Test
    void readLongSkipsInvalid() {
        String input = "abc\n123\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        long result = ConsoleInputUtils.readLong(scanner, "Enter: ");
        assertEquals(123L, result);
    }

    @Test
    void readDoubleMinSkipsInvalidAndTooSmall() {
        String input = "abc\n0.1\n2.5\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        double result = ConsoleInputUtils.readDoubleMin(scanner, "Enter: ", 1.0);
        assertEquals(2.5, result);
    }

    @Test
    void readYesNoSkipsInvalid() {
        String input = "maybe\ny\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        boolean result = ConsoleInputUtils.readYesNo(scanner, "Confirm");
        assertEquals(true, result);
    }

    @Test
    void readEnumSkipsInvalid() {
        String input = "wrong\nrelax\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));

        TourType value = ConsoleInputUtils.readEnum(scanner, "Type", TourType.class);
        assertEquals(TourType.RELAX, value);
    }
}
