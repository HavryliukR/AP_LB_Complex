package tourapp.ui.command;

import tourapp.model.*;

import java.util.List;
import java.util.Scanner;

public class SearchToursCommand extends BaseCommand {

    public SearchToursCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        Scanner scanner = context.getScanner();
        System.out.println("--- Search / filter tours ---");

        TourType type = null;
        if (ConsoleInputUtils.readYesNo(scanner, "Filter by tour type?")) {
            type = ConsoleInputUtils.readEnum(scanner, "Tour type", TourType.class);
        }

        String country = null;
        if (ConsoleInputUtils.readYesNo(scanner, "Filter by country?")) {
            country = ConsoleInputUtils.readNonEmptyString(scanner, "Country: ");
        }

        TransportType transportType = null;
        if (ConsoleInputUtils.readYesNo(scanner, "Filter by transport type?")) {
            transportType = ConsoleInputUtils.readEnum(scanner, "Transport type", TransportType.class);
        }

        FoodType foodType = null;
        if (ConsoleInputUtils.readYesNo(scanner, "Filter by food type?")) {
            foodType = ConsoleInputUtils.readEnum(scanner, "Food type", FoodType.class);
        }

        Integer minDays = null;
        Integer maxDays = null;
        if (ConsoleInputUtils.readYesNo(scanner, "Filter by days range?")) {
            minDays = ConsoleInputUtils.readIntInRange(scanner, "Min days: ", 1, 365);
            maxDays = ConsoleInputUtils.readIntInRange(scanner, "Max days: ", minDays, 365);
        }

        Double minPrice = null;
        Double maxPrice = null;
        if (ConsoleInputUtils.readYesNo(scanner, "Filter by price range?")) {
            minPrice = ConsoleInputUtils.readDoubleMin(scanner, "Min price: ", 0.0);
            maxPrice = ConsoleInputUtils.readDoubleMin(scanner, "Max price (>= min price): ", minPrice);
        }

        List<Tour> result = context.getCatalogManager().findTours(
                type, country, transportType, foodType, minDays, maxDays, minPrice, maxPrice
        );

        if (result.isEmpty()) {
            System.out.println("No tours found for given criteria.");
        } else {
            System.out.println("Found " + result.size() + " tour(s):");
            for (Tour tour : result) {
                System.out.println(tour);
            }
        }
    }
}
