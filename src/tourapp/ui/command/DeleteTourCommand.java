package tourapp.ui.command;

import tourapp.core.TourCatalogManager;
import tourapp.logging.LoggingConfig;

import java.util.Scanner;
import java.util.logging.Logger;

public class DeleteTourCommand extends BaseCommand {

    private static final Logger LOGGER = LoggingConfig.getLogger(DeleteTourCommand.class);

    public DeleteTourCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        LOGGER.info("Executing DeleteTourCommand");
        Scanner scanner = context.getScanner();
        TourCatalogManager catalogManager = context.getCatalogManager();

        long id = ConsoleInputUtils.readLong(scanner, "Enter tour id to delete: ");
        boolean removed = catalogManager.deleteTour(id);
        if (removed) {
            LOGGER.info("Tour id=" + id + " deleted");
            System.out.println("Tour deleted successfully.");
        } else {
            LOGGER.warning("Attempt to delete non-existing tour id=" + id);
            System.out.println("Tour with id " + id + " not found.");
        }
    }
}
