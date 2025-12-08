package tourapp.ui.command;

import tourapp.core.TourCatalogManager;
import tourapp.logging.LoggingConfig;
import tourapp.model.*;

import java.util.Scanner;
import java.util.logging.Logger;

public class SearchToursCommand extends BaseCommand {

    private static final Logger LOGGER = LoggingConfig.getLogger(SearchToursCommand.class);

    public SearchToursCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        LOGGER.info("Executing SearchToursCommand");
        Scanner scanner = context.getScanner();
        TourCatalogManager catalogManager = context.getCatalogManager();

        TourType type = null;
        if (ConsoleInputUtils.readYesNo(scanner, "Filter by tour type?")) {
            type = ConsoleInputUtils.readEnum(scanner, "Enter tour type", TourType.class);
        }

        String country = null;
        if (ConsoleInputUtils.readYesNo(scanner, "Filter by country?")) {
            country = ConsoleInputUtils.readNonEmptyString(scanner, "Enter country: ");
        }

        TransportType transportType = null;
        if (ConsoleInputUtils.readYesNo(scanner, "Filter by transport type?")) {
            transportType = ConsoleInputUtils.readEnum(scanner, "Enter transport type", TransportType.class);
        }

        FoodType foodType = null;
        if (ConsoleInputUtils.readYesNo(scanner, "Filter by food type?")) {
            foodType = ConsoleInputUtils.readEnum(scanner, "Enter food type", FoodType.class);
        }

        Integer minDays = null;
        Integer maxDays = null;
        if (ConsoleInputUtils.readYesNo(scanner, "Filter by days range?")) {
            minDays = ConsoleInputUtils.readIntInRange(scanner, "Enter minimum days: ", 1, 365);
            maxDays = ConsoleInputUtils.readIntInRange(scanner, "Enter maximum days: ", minDays, 365);
        }

        Double minPrice = null;
        Double maxPrice = null;
        if (ConsoleInputUtils.readYesNo(scanner, "Filter by price range?")) {
            minPrice = ConsoleInputUtils.readDoubleMin(scanner, "Enter minimum price: ", 0.0);
            maxPrice = ConsoleInputUtils.readDoubleMin(scanner, "Enter maximum price: ", minPrice);
        }

        catalogManager.searchAndFilterTours(
                type,
                country,
                transportType,
                foodType,
                minDays,
                maxDays,
                minPrice,
                maxPrice
        );
    }
}
