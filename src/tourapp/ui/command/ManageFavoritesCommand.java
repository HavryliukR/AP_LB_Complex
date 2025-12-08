package tourapp.ui.command;

import tourapp.core.TourCatalogManager;
import tourapp.logging.LoggingConfig;

import java.util.Scanner;
import java.util.logging.Logger;

public class ManageFavoritesCommand extends BaseCommand {

    private static final Logger LOGGER = LoggingConfig.getLogger(ManageFavoritesCommand.class);

    public ManageFavoritesCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        LOGGER.info("Executing ManageFavoritesCommand");
        Scanner scanner = context.getScanner();
        TourCatalogManager catalogManager = context.getCatalogManager();

        boolean back = false;
        while (!back) {
            System.out.println("=== Favorites menu ===");
            System.out.println("1 - Show favorites");
            System.out.println("2 - Add tour to favorites");
            System.out.println("3 - Remove tour from favorites");
            System.out.println("4 - Back to main menu");

            int choice = ConsoleInputUtils.readIntInRange(scanner, "Enter option: ", 1, 4);
            switch (choice) {
                case 1:
                    catalogManager.manageFavorites();
                    break;
                case 2: {
                    long id = ConsoleInputUtils.readLong(scanner, "Enter tour id to add to favorites: ");
                    boolean ok = catalogManager.addToFavorites(id);
                    if (!ok) {
                        System.out.println("Cannot add tour to favorites. Check id.");
                    }
                    break;
                }
                case 3: {
                    long id = ConsoleInputUtils.readLong(scanner, "Enter tour id to remove from favorites: ");
                    boolean ok = catalogManager.removeFromFavorites(id);
                    if (!ok) {
                        System.out.println("Cannot remove tour from favorites. Check id.");
                    }
                    break;
                }
                case 4:
                    back = true;
                    break;
                default:
                    LOGGER.warning("Unknown favorites menu option: " + choice);
            }
        }

        LOGGER.info("ManageFavoritesCommand finished");
    }
}
