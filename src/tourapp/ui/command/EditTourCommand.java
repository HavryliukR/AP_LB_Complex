package tourapp.ui.command;

import tourapp.model.*;

import java.util.Scanner;

public class EditTourCommand extends BaseCommand {

    public EditTourCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        Scanner scanner = context.getScanner();
        System.out.println("--- Edit tour ---");

        long id = ConsoleInputUtils.readLong(scanner, "Enter tour id to edit: ");
        if (context.getCatalogManager().findTourById(id) == null) {
            System.out.println("Tour with id " + id + " not found.");
            return;
        }

        System.out.println("Enter new data for tour (all fields required).");

        String name = ConsoleInputUtils.readNonEmptyString(scanner, "Tour name: ");
        TourType type = ConsoleInputUtils.readEnum(scanner, "Tour type", TourType.class);

        String country = ConsoleInputUtils.readNonEmptyString(scanner, "Country: ");
        String city = ConsoleInputUtils.readNonEmptyString(scanner, "City: ");
        Location location = new Location(country, city);

        String hotelName = ConsoleInputUtils.readNonEmptyString(scanner, "Hotel name: ");
        int stars = ConsoleInputUtils.readIntInRange(scanner, "Hotel stars (1-5): ", 1, 5);
        double hotelRating = ConsoleInputUtils.readDoubleMin(scanner, "Hotel rating (0-10): ", 0.0);
        Hotel hotel = new Hotel(hotelName, stars, hotelRating);

        TransportType transport = ConsoleInputUtils.readEnum(scanner, "Transport type", TransportType.class);
        FoodType foodType = ConsoleInputUtils.readEnum(scanner, "Food type", FoodType.class);

        int days = ConsoleInputUtils.readIntInRange(scanner, "Days (1..365): ", 1, 365);
        double price = ConsoleInputUtils.readDoubleMin(scanner, "Price (>=0): ", 0.0);
        double rating = ConsoleInputUtils.readDoubleMin(scanner, "Tour rating (0-10): ", 0.0);

        System.out.print("Description (optional, can be empty): ");
        String description = scanner.nextLine();

        Tour updated = new Tour(name, type, location, hotel, transport, foodType, days, price, rating, description);
        boolean ok = context.getCatalogManager().updateTour(id, updated);

        if (ok) {
            System.out.println("Tour updated successfully.");
        } else {
            System.out.println("Failed to update tour (tour not found).");
        }
    }
}
