package tourapp.ui.command;

import tourapp.core.TourCatalogManager;
import tourapp.logging.LoggingConfig;

import java.util.Scanner;
import java.util.logging.Logger;

public class SaveToFileCommand extends BaseCommand {

    private static final Logger LOGGER = LoggingConfig.getLogger(SaveToFileCommand.class);

    public SaveToFileCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        LOGGER.info("Executing SaveToFileCommand");
        Scanner scanner = context.getScanner();
        TourCatalogManager catalogManager = context.getCatalogManager();

        System.out.print("Enter file path to save: ");
        String path = scanner.nextLine().trim();
        if (path.isEmpty()) {
            LOGGER.info("No file path entered, skipping save");
            System.out.println("Path is empty. Nothing to save.");
            return;
        }
        catalogManager.saveToFile(path);
    }
}
