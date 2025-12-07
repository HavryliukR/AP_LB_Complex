package tourapp.ui.command;

import tourapp.model.*;

import java.util.List;
import java.util.Scanner;

public class ManageBookingsCommand extends BaseCommand {

    public ManageBookingsCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        Scanner scanner = context.getScanner();
        boolean back = false;

        while (!back) {
            System.out.println("--- Bookings ---");
            System.out.println("1 - Show all bookings");
            System.out.println("2 - Create new booking");
            System.out.println("3 - Cancel booking");
            System.out.println("4 - Back to main menu");

            int choice = ConsoleInputUtils.readIntInRange(scanner, "Choose option (1-4): ", 1, 4);

            switch (choice) {
                case 1:
                    List<Booking> bookings = context.getCatalogManager().getBookings();
                    if (bookings.isEmpty()) {
                        System.out.println("No bookings found.");
                    } else {
                        for (Booking booking : bookings) {
                            System.out.println(booking);
                        }
                    }
                    break;
                case 2:
                    createBooking(scanner);
                    break;
                case 3:
                    cancelBooking(scanner);
                    break;
                case 4:
                    back = true;
                    break;
            }
            System.out.println();
        }
    }

    private void createBooking(Scanner scanner) {
        long tourId = ConsoleInputUtils.readLong(scanner, "Enter tour id to book: ");
        int persons = ConsoleInputUtils.readIntInRange(scanner, "Number of persons (1..20): ", 1, 20);

        String fullName = ConsoleInputUtils.readNonEmptyString(scanner, "Customer full name: ");
        String phone = ConsoleInputUtils.readNonEmptyString(scanner, "Customer phone: ");
        String email = ConsoleInputUtils.readNonEmptyString(scanner, "Customer email: ");

        Customer customer = new Customer(0L, fullName, phone, email);
        Booking booking = context.getCatalogManager().createBooking(tourId, customer, persons);

        if (booking == null) {
            System.out.println("Tour with id " + tourId + " not found.");
        } else {
            System.out.println("Booking created: " + booking);
        }
    }

    private void cancelBooking(Scanner scanner) {
        long bookingId = ConsoleInputUtils.readLong(scanner, "Enter booking id to cancel: ");
        boolean ok = context.getCatalogManager().cancelBooking(bookingId);
        if (ok) {
            System.out.println("Booking canceled.");
        } else {
            System.out.println("Booking with id " + bookingId + " not found.");
        }
    }
}
