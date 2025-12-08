package tourapp.ui.command;

import org.junit.jupiter.api.Test;
import tourapp.core.TourCatalogManager;
import tourapp.model.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ManageFavoritesCommandTest {

    private Tour createSampleTour() {
        Location location = new Location("Country", "City");
        Hotel hotel = new Hotel("Hotel", 4, 8.0);
        return new Tour("Name", TourType.RELAX, location, hotel,
                TransportType.PLANE, FoodType.BB, 7, 700.0, 8.5, "Desc");
    }

    @Test
    void executeHandlesAllMenuOptions() {
        TourCatalogManager manager = new TourCatalogManager();
        Tour tour = manager.addTour(createSampleTour());
        long id = tour.getId();

        String input = String.join("\n",
                "1",           // show favorites (empty)
                "2", String.valueOf(id), // add to favorites
                "1",           // show favorites (non-empty)
                "3", String.valueOf(id), // remove from favorites
                "4"            // back
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ApplicationContext context = new ApplicationContext(scanner, manager);
        ManageFavoritesCommand command = new ManageFavoritesCommand(context);

        command.execute();

        assertEquals(0, manager.getFavoriteTours().size());
    }

    @Test
    void executeHandlesInvalidMenuChoice() {
        TourCatalogManager manager = new TourCatalogManager();
        String input = String.join("\n",
                "5",  // invalid menu option
                "4"   // back
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ApplicationContext context = new ApplicationContext(scanner, manager);
        ManageFavoritesCommand command = new ManageFavoritesCommand(context);

        command.execute();

        // просто перевіряємо, що нічого не впало і список фаворитів пустий
        assertTrue(manager.getFavoriteTours().isEmpty());
    }
    @Test
    void executeRemoveNonExistingFavoriteDoesNotFail() {
        TourCatalogManager manager = new TourCatalogManager();

        String input = String.join("\n",
                "3",      // remove from favorites
                "9999",   // id that is not in favorites
                "4"       // back
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ApplicationContext context = new ApplicationContext(scanner, manager);
        ManageFavoritesCommand command = new ManageFavoritesCommand(context);

        command.execute();

        assertTrue(manager.getFavoriteTours().isEmpty());
    }

}
