package tourapp.ui.command;

import tourapp.model.Tour;

import java.util.List;
import java.util.Scanner;

public class ManageFavoritesCommand extends BaseCommand {

    public ManageFavoritesCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        Scanner scanner = context.getScanner();
        boolean back = false;

        while (!back) {
            System.out.println("--- Favorite tours ---");
            System.out.println("1 - Show favorite tours");
            System.out.println("2 - Add tour to favorites");
            System.out.println("3 - Remove tour from favorites");
            System.out.println("4 - Back to main menu");

            int choice = ConsoleInputUtils.readIntInRange(scanner, "Choose option (1-4): ", 1, 4);

            switch (choice) {
                case 1:
                    List<Tour> favorites = context.getCatalogManager().getFavoriteTours();
                    if (favorites.isEmpty()) {
                        System.out.println("Favorite list is empty.");
                    } else {
                        for (Tour tour : favorites) {
                            System.out.println(tour);
                        }
                    }
                    break;
                case 2:
                    long addId = ConsoleInputUtils.readLong(scanner, "Enter tour id to add to favorites: ");
                    if (context.getCatalogManager().addToFavorites(addId)) {
                        System.out.println("Tour added to favorites.");
                    } else {
                        System.out.println("Failed to add tour to favorites (tour not found or already in list).");
                    }
                    break;
                case 3:
                    long removeId = ConsoleInputUtils.readLong(scanner, "Enter tour id to remove from favorites: ");
                    if (context.getCatalogManager().removeFromFavorites(removeId)) {
                        System.out.println("Tour removed from favorites.");
                    } else {
                        System.out.println("Tour not found in favorites.");
                    }
                    break;
                case 4:
                    back = true;
                    break;
            }
            System.out.println();
        }
    }
}
