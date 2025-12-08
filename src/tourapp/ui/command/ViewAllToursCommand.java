package tourapp.ui.command;

import tourapp.core.TourCatalogManager;
import tourapp.logging.LoggingConfig;

import java.util.logging.Logger;

public class ViewAllToursCommand extends BaseCommand {

    private static final Logger LOGGER = LoggingConfig.getLogger(ViewAllToursCommand.class);

    public ViewAllToursCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        LOGGER.info("Executing ViewAllToursCommand");
        TourCatalogManager catalogManager = context.getCatalogManager();
        catalogManager.viewAllTours();
    }
}
