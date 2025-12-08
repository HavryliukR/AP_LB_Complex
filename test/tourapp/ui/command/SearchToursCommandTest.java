package tourapp.ui.command;

import org.junit.jupiter.api.Test;
import tourapp.core.TourCatalogManager;
import tourapp.model.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class SearchToursCommandTest {

    @Test
    void executeWithAllFiltersAndNoResults() {
        String input = String.join("\n",
                "y",        // filter by type
                "RELAX",
                "y",        // filter by country
                "Mars",
                "y",        // filter by transport
                "PLANE",
                "y",        // filter by food
                "AI",
                "y",        // filter by days
                "5",
                "10",
                "y",        // filter by price
                "100.0",
                "200.0"
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();

        Location location = new Location("Earth", "City");
        Hotel hotel = new Hotel("Hotel", 4, 8.0);
        manager.addTour(new Tour("Name", TourType.RELAX, location, hotel,
                TransportType.PLANE, FoodType.AI, 7, 300.0, 8.0, "Desc"));

        ApplicationContext context = new ApplicationContext(scanner, manager);
        SearchToursCommand command = new SearchToursCommand(context);

        command.execute();

        assertEquals(1, manager.getAllTours().size());
    }

    @Test
    void executeWithoutAnyFilters() {
        String input = String.join("\n",
                "n", // no tour type filter
                "n", // no country filter
                "n", // no transport filter
                "n", // no food filter
                "n", // no days range
                "n"  // no price range
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();
        Location location = new Location("Country", "City");
        Hotel hotel = new Hotel("Hotel", 4, 8.0);
        manager.addTour(new Tour("Name1", TourType.RELAX, location, hotel,
                TransportType.PLANE, FoodType.AI, 7, 300.0, 8.0, "Desc"));
        manager.addTour(new Tour("Name2", TourType.EXCURSION, location, hotel,
                TransportType.BUS, FoodType.BB, 5, 200.0, 7.5, "Desc2"));

        ApplicationContext context = new ApplicationContext(scanner, manager);
        SearchToursCommand command = new SearchToursCommand(context);

        command.execute();

        assertEquals(2, manager.getAllTours().size());
    }

}
