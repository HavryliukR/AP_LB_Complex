package tourapp.ui.command;

import org.junit.jupiter.api.Test;
import tourapp.core.TourCatalogManager;
import tourapp.model.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ViewAllToursCommandTest {

    @Test
    void executePrintsTours() {
        Scanner scanner = new Scanner(new ByteArrayInputStream("".getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();

        Location location = new Location("Country", "City");
        Hotel hotel = new Hotel("Hotel", 4, 8.0);
        manager.addTour(new Tour("Name", TourType.EXCURSION, location, hotel,
                TransportType.TRAIN, FoodType.BB, 3, 300.0, 8.0, "Desc"));

        ApplicationContext context = new ApplicationContext(scanner, manager);
        ViewAllToursCommand command = new ViewAllToursCommand(context);

        command.execute();
        assertEquals(1, manager.getAllTours().size());
    }
}
