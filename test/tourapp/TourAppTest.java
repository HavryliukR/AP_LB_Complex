package tourapp;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class TourAppTest {

    @Test
    void mainStartsAndExitsOnExitOption() {
        String input = "12\n"; // choose EXIT in menu
        InputStream originalIn = System.in;
        PrintStream originalOut = System.out;

        try {
            System.setIn(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
            System.setOut(new PrintStream(new ByteArrayOutputStream()));

            assertDoesNotThrow(() -> TourApp.main(new String[]{}));
        } finally {
            System.setIn(originalIn);
            System.setOut(originalOut);
        }
    }
}
