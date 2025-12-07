package tourapp;

import tourapp.core.TourCatalogManager;
import tourapp.ui.command.ApplicationContext;
import tourapp.ui.menu.ConsoleMenu;

import java.util.Scanner;

public class TourApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        TourCatalogManager catalogManager = new TourCatalogManager();

        ApplicationContext context = new ApplicationContext(scanner, catalogManager);
        ConsoleMenu menu = new ConsoleMenu(context);

        menu.start();

        scanner.close();
    }
}
