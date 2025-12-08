package tourapp.ui.command;

import tourapp.model.SortField;
import tourapp.core.TourCatalogManager;
import tourapp.logging.LoggingConfig;

import java.util.Scanner;
import java.util.logging.Logger;

public class SortToursCommand extends BaseCommand {

    private static final Logger LOGGER = LoggingConfig.getLogger(SortToursCommand.class);

    public SortToursCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        LOGGER.info("Executing SortToursCommand");
        Scanner scanner = context.getScanner();
        TourCatalogManager catalogManager = context.getCatalogManager();

        System.out.println("Choose sort field:");
        System.out.println("1 - price");
        System.out.println("2 - days");
        System.out.println("3 - rating");

        int choice = ConsoleInputUtils.readIntInRange(scanner, "Enter option: ", 1, 3);
        SortField field;
        switch (choice) {
            case 1:
                field = SortField.PRICE;
                break;
            case 2:
                field = SortField.DAYS;
                break;
            case 3:
                field = SortField.RATING;
                break;
            default:
                field = SortField.PRICE;
        }

        boolean ascending = ConsoleInputUtils.readYesNo(scanner, "Sort ascending?");
        catalogManager.sortTours(field, ascending);
    }
}
