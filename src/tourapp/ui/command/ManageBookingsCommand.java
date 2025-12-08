package tourapp.ui.command;

import tourapp.core.TourCatalogManager;
import tourapp.logging.LoggingConfig;
import tourapp.model.Booking;
import tourapp.model.Customer;

import java.util.Scanner;
import java.util.logging.Logger;

public class ManageBookingsCommand extends BaseCommand {

    private static final Logger LOGGER = LoggingConfig.getLogger(ManageBookingsCommand.class);

    public ManageBookingsCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        LOGGER.info("Executing ManageBookingsCommand");
        Scanner scanner = context.getScanner();
        TourCatalogManager catalogManager = context.getCatalogManager();

        boolean back = false;
        while (!back) {
            System.out.println("=== Bookings menu ===");
            System.out.println("1 - Show all bookings");
            System.out.println("2 - Create booking");
            System.out.println("3 - Cancel booking");
            System.out.println("4 - Back to main menu");

            int choice = ConsoleInputUtils.readIntInRange(scanner, "Enter option: ", 1, 4);
            switch (choice) {
                case 1:
                    catalogManager.manageBookings();
                    break;
                case 2:
                    createBooking(scanner, catalogManager);
                    break;
                case 3:
                    cancelBooking(scanner, catalogManager);
                    break;
                case 4:
                    back = true;
                    break;
                default:
                    LOGGER.warning("Unknown bookings menu option: " + choice);
            }
        }

        LOGGER.info("ManageBookingsCommand finished");
    }

    private void createBooking(Scanner scanner, TourCatalogManager catalogManager) {
        long tourId = ConsoleInputUtils.readLong(scanner, "Enter tour id: ");
        String fullName = ConsoleInputUtils.readNonEmptyString(scanner, "Enter customer full name: ");
        String phone = ConsoleInputUtils.readNonEmptyString(scanner, "Enter customer phone: ");
        String email = ConsoleInputUtils.readNonEmptyString(scanner, "Enter customer e-mail: ");

        int persons = ConsoleInputUtils.readIntInRange(scanner, "Enter number of persons: ", 1, 100);

        Customer customer = new Customer(0L, fullName, phone, email);
        Booking booking = catalogManager.createBooking(tourId, customer, persons);
        if (booking == null) {
            System.out.println("Failed to create booking. Tour id not found.");
        } else {
            System.out.println("Booking created with id=" + booking.getId());
        }
    }

    private void cancelBooking(Scanner scanner, TourCatalogManager catalogManager) {
        long bookingId = ConsoleInputUtils.readLong(scanner, "Enter booking id to cancel: ");
        boolean ok = catalogManager.cancelBooking(bookingId);
        if (!ok) {
            System.out.println("Booking id not found.");
        } else {
            System.out.println("Booking canceled.");
        }
    }
}
