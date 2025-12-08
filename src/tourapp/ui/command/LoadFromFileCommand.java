package tourapp.ui.command;

import tourapp.core.TourCatalogManager;
import tourapp.logging.LoggingConfig;

import java.util.Scanner;
import java.util.logging.Logger;

public class LoadFromFileCommand extends BaseCommand {

    private static final Logger LOGGER = LoggingConfig.getLogger(LoadFromFileCommand.class);

    public LoadFromFileCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        LOGGER.info("Executing LoadFromFileCommand");
        Scanner scanner = context.getScanner();
        TourCatalogManager catalogManager = context.getCatalogManager();

        System.out.print("Enter file path to load: ");
        String path = scanner.nextLine().trim();
        if (path.isEmpty()) {
            LOGGER.info("No file path entered, skipping load");
            System.out.println("Path is empty. Nothing to load.");
            return;
        }
        catalogManager.loadFromFile(path);
    }
}
