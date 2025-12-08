package tourapp.ui.command;

import org.junit.jupiter.api.Test;
import tourapp.core.TourCatalogManager;
import tourapp.model.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class EditTourCommandTest {

    private Tour createSampleTour() {
        Location location = new Location("Country", "City");
        Hotel hotel = new Hotel("Hotel", 4, 8.0);
        return new Tour("Name", TourType.RELAX, location, hotel,
                TransportType.PLANE, FoodType.BB, 7, 700.0, 8.5, "Desc");
    }

    @Test
    void executeUpdatesExistingTour() {
        TourCatalogManager manager = new TourCatalogManager();
        Tour tour = manager.addTour(createSampleTour());

        String input = String.join("\n",
                String.valueOf(tour.getId()),
                "New Name",
                "CRUISE",
                "Italy",
                "Venice",
                "New Hotel",
                "5",
                "9.5",
                "SHIP",
                "AI",
                "10",
                "2000.0",
                "9.1",
                "Updated description"
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ApplicationContext context = new ApplicationContext(scanner, manager);
        EditTourCommand command = new EditTourCommand(context);

        command.execute();

        Tour updated = manager.findTourById(tour.getId());
        assertEquals("New Name", updated.getName());
        assertEquals(TourType.CRUISE, updated.getType());
        assertEquals("Italy", updated.getLocation().getCountry());
    }

    @Test
    void executeWithUnknownIdDoesNotThrow() {
        TourCatalogManager manager = new TourCatalogManager();

        String input = "9999\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ApplicationContext context = new ApplicationContext(scanner, manager);
        EditTourCommand command = new EditTourCommand(context);

        command.execute();
        assertEquals(0, manager.getAllTours().size());
    }
}
