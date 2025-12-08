package tourapp.ui.command;

import tourapp.logging.LoggingConfig;

import java.util.logging.Logger;

public class ExitCommand extends BaseCommand {

    private static final Logger LOGGER = LoggingConfig.getLogger(ExitCommand.class);

    public ExitCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        LOGGER.info("Executing ExitCommand");
        System.out.println("Exiting application. Goodbye!");
    }
}
