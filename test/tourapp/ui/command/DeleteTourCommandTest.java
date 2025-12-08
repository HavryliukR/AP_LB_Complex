package tourapp.ui.command;

import org.junit.jupiter.api.Test;
import tourapp.core.TourCatalogManager;
import tourapp.model.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class DeleteTourCommandTest {

    private Tour createSampleTour() {
        Location location = new Location("Italy", "Rome");
        Hotel hotel = new Hotel("Test Hotel", 3, 8.0);
        return new Tour("Test Tour", TourType.EXCURSION, location, hotel,
                TransportType.PLANE, FoodType.BB, 3, 400.0, 8.1, "Test");
    }

    @Test
    void executeDeletesExistingTour() {
        TourCatalogManager manager = new TourCatalogManager();
        Tour tour = manager.addTour(createSampleTour());

        String input = tour.getId() + "\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ApplicationContext context = new ApplicationContext(scanner, manager);

        DeleteTourCommand command = new DeleteTourCommand(context);

        assertEquals(1, manager.getAllTours().size());
        command.execute();
        assertEquals(0, manager.getAllTours().size());
    }

    @Test
    void executeWithUnknownIdDoesNotThrow() {
        TourCatalogManager manager = new TourCatalogManager();
        String input = "9999\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ApplicationContext context = new ApplicationContext(scanner, manager);

        DeleteTourCommand command = new DeleteTourCommand(context);
        command.execute();

        assertEquals(0, manager.getAllTours().size());
    }
}
