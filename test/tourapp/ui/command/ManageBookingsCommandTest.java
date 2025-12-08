package tourapp.ui.command;

import org.junit.jupiter.api.Test;
import tourapp.core.TourCatalogManager;
import tourapp.model.*;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ManageBookingsCommandTest {

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
        long tourId = tour.getId();

        // booking ids start from 1
        String input = String.join("\n",
                "1",               // show all bookings (none)
                "2",               // create booking
                String.valueOf(tourId),
                "2",               // persons
                "John Doe",        // name
                "123456",          // phone
                "john@example.com",// email
                "1",               // show bookings
                "3",               // cancel booking
                "1",               // booking id (first = 1)
                "4"                // back
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ApplicationContext context = new ApplicationContext(scanner, manager);
        ManageBookingsCommand command = new ManageBookingsCommand(context);

        command.execute();

        assertEquals(1, manager.getBookings().size());
        assertEquals(BookingStatus.CANCELED, manager.getBookings().get(0).getStatus());
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
        ManageBookingsCommand command = new ManageBookingsCommand(context);

        command.execute();

        assertTrue(manager.getBookings().isEmpty());
    }

    @Test
    void executeCancelUnknownBookingDoesNotFail() {
        TourCatalogManager manager = new TourCatalogManager();

        // спочатку створимо коректне бронювання,
        // щоб команда пройшла через гілку "id не знайдено"
        Location location = new Location("Country", "City");
        Hotel hotel = new Hotel("Hotel", 4, 8.0);
        Tour tour = manager.addTour(new Tour("Name", TourType.RELAX, location, hotel,
                TransportType.BUS, FoodType.BB, 5, 500.0, 8.0, "Desc"));

        Customer customer = new Customer(1L, "User", "123", "user@example.com");
        manager.createBooking(tour.getId(), customer, 2);

        String input = String.join("\n",
                "3",      // cancel booking
                "9999",   // wrong booking id
                "4"       // back
        ) + "\n";

        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        ApplicationContext context = new ApplicationContext(scanner, manager);
        ManageBookingsCommand command = new ManageBookingsCommand(context);

        command.execute();

        // бронювання повинно залишитися, бо id був невалідний
        assertEquals(1, manager.getBookings().size());
    }


}
