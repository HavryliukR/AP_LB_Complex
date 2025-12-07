package tourapp.ui.command;

import tourapp.core.TourCatalogManager;

import java.util.Scanner;

public class ApplicationContext {

    private final Scanner scanner;
    private final TourCatalogManager catalogManager;

    public ApplicationContext(Scanner scanner, TourCatalogManager catalogManager) {
        this.scanner = scanner;
        this.catalogManager = catalogManager;
    }

    public Scanner getScanner() {
        return scanner;
    }

    public TourCatalogManager getCatalogManager() {
        return catalogManager;
    }
}
