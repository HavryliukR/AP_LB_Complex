package tourapp.ui.command;

import org.junit.jupiter.api.Test;
import tourapp.core.TourCatalogManager;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class HelpCommandTest {

    @Test
    void executePrintsHelp() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();
        ApplicationContext context = new ApplicationContext(scanner, manager);

        HelpCommand command = new HelpCommand(context);

        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            assertDoesNotThrow(command::execute);
        } finally {
            System.setOut(originalOut);
        }
    }
}
