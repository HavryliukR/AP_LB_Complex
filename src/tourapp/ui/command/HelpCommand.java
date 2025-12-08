package tourapp.ui.command;

import tourapp.logging.LoggingConfig;

import java.util.logging.Logger;

public class HelpCommand extends BaseCommand {

    private static final Logger LOGGER = LoggingConfig.getLogger(HelpCommand.class);

    public HelpCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        LOGGER.info("Executing HelpCommand");
        System.out.println("===== HELP =====");
        System.out.println("This application allows you to manage a catalog of tours,");
        System.out.println("search and sort tours, manage favorites and bookings,");
        System.out.println("and load/save catalog from/to a file.");
    }
}
