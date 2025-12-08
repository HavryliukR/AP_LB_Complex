package tourapp.ui.command;

import org.junit.jupiter.api.Test;
import tourapp.core.TourCatalogManager;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class AddTourCommandTest {

    @Test
    void executeAddsNewTour() {
        String input = String.join("\n",
                "Summer Tour",
                "RELAX",
                "Greece",
                "Athens",
                "Sea Breeze",
                "4",
                "8.7",
                "PLANE",
                "AI",
                "7",
                "900.0",
                "8.9",
                "Nice tour"
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();
        ApplicationContext context = new ApplicationContext(scanner, manager);

        AddTourCommand command = new AddTourCommand(context);

        assertEquals(0, manager.getAllTours().size());
        command.execute();
        assertEquals(1, manager.getAllTours().size());
        assertEquals("Summer Tour", manager.getAllTours().get(0).getName());
    }
}
