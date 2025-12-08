package tourapp.ui.command;

import tourapp.core.TourCatalogManager;
import tourapp.logging.LoggingConfig;
import tourapp.model.*;

import java.util.Scanner;
import java.util.logging.Logger;

public class EditTourCommand extends BaseCommand {

    private static final Logger LOGGER = LoggingConfig.getLogger(EditTourCommand.class);

    public EditTourCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        LOGGER.info("Executing EditTourCommand");
        Scanner scanner = context.getScanner();
        TourCatalogManager catalogManager = context.getCatalogManager();

        long id = ConsoleInputUtils.readLong(scanner, "Enter tour id to edit: ");
        Tour existing = catalogManager.findTourById(id);
        if (existing == null) {
            LOGGER.warning("Attempt to edit non-existing tour id=" + id);
            System.out.println("Tour with id " + id + " not found.");
            return;
        }

        String name = ConsoleInputUtils.readNonEmptyString(scanner, "Enter new tour name: ");
        TourType type = ConsoleInputUtils.readEnum(scanner, "Enter tour type", TourType.class);

        String country = ConsoleInputUtils.readNonEmptyString(scanner, "Enter country: ");
        String city = ConsoleInputUtils.readNonEmptyString(scanner, "Enter city: ");
        Location location = new Location(country, city);

        String hotelName = ConsoleInputUtils.readNonEmptyString(scanner, "Enter hotel name: ");
        int stars = ConsoleInputUtils.readIntInRange(scanner, "Enter hotel stars (1-5): ", 1, 5);
        double hotelRating = ConsoleInputUtils.readDoubleMin(scanner, "Enter hotel rating (0-10): ", 0.0);
        Hotel hotel = new Hotel(hotelName, stars, hotelRating);

        TransportType transportType = ConsoleInputUtils.readEnum(scanner, "Enter transport type", TransportType.class);
        FoodType foodType = ConsoleInputUtils.readEnum(scanner, "Enter food type", FoodType.class);

        int days = ConsoleInputUtils.readIntInRange(scanner, "Enter number of days: ", 1, 365);
        double price = ConsoleInputUtils.readDoubleMin(scanner, "Enter price: ", 0.0);
        double rating = ConsoleInputUtils.readDoubleMin(scanner, "Enter tour rating (0-10): ", 0.0);

        String description = ConsoleInputUtils.readNonEmptyString(scanner, "Enter description: ");

        Tour updated = new Tour(name, type, location, hotel, transportType, foodType, days, price, rating, description);
        boolean ok = catalogManager.updateTour(id, updated);
        if (ok) {
            LOGGER.info("Tour id=" + id + " updated");
            System.out.println("Tour updated successfully.");
        } else {
            LOGGER.warning("Update failed for tour id=" + id);
            System.out.println("Failed to update tour.");
        }
    }
}
