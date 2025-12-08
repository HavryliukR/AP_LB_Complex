package tourapp.ui.command;

import org.junit.jupiter.api.Test;
import tourapp.core.TourCatalogManager;
import tourapp.model.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

public class SortToursCommandTest {

    private Tour createTour(String name, double price, int days, double rating) {
        Location location = new Location("Country", "City");
        Hotel hotel = new Hotel("Hotel", 4, 8.0);
        return new Tour(name, TourType.RELAX, location, hotel,
                TransportType.PLANE, FoodType.BB, days, price, rating, "Desc");
    }

    @Test
    void executeSortByPriceAscending() {
        String input = String.join("\n",
                "1", // price
                "y"  // ascending
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();
        manager.addTour(createTour("A", 300, 5, 8.0));
        manager.addTour(createTour("B", 200, 7, 9.0));

        ApplicationContext context = new ApplicationContext(scanner, manager);
        SortToursCommand command = new SortToursCommand(context);

        command.execute();
    }

    @Test
    void executeSortByDaysDescending() {
        String input = String.join("\n",
                "2",
                "n"
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();
        manager.addTour(createTour("A", 300, 5, 8.0));
        manager.addTour(createTour("B", 200, 7, 9.0));

        ApplicationContext context = new ApplicationContext(scanner, manager);
        SortToursCommand command = new SortToursCommand(context);

        command.execute();
    }

    @Test
    void executeSortByRatingAscending() {
        String input = String.join("\n",
                "3",
                "y"
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();
        manager.addTour(createTour("A", 300, 5, 7.0));
        manager.addTour(createTour("B", 200, 7, 9.0));

        ApplicationContext context = new ApplicationContext(scanner, manager);
        SortToursCommand command = new SortToursCommand(context);

        command.execute();
    }
}
