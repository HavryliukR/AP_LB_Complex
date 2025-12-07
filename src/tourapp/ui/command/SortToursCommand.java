package tourapp.ui.command;

import tourapp.model.SortField;
import tourapp.model.Tour;

import java.util.List;
import java.util.Scanner;

public class SortToursCommand extends BaseCommand {

    public SortToursCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        Scanner scanner = context.getScanner();
        System.out.println("--- Sort tours ---");
        System.out.println("1 - by price");
        System.out.println("2 - by days");
        System.out.println("3 - by rating");

        int choice = ConsoleInputUtils.readIntInRange(scanner, "Choose sort field (1-3): ", 1, 3);
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
        List<Tour> sorted = context.getCatalogManager().getToursSortedBy(field, ascending);

        if (sorted.isEmpty()) {
            System.out.println("No tours to sort.");
            return;
        }

        System.out.println("Sorted tours:");
        for (Tour tour : sorted) {
            System.out.println(tour);
        }
    }
}
