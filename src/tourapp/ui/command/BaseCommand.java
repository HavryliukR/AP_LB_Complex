package tourapp.ui.command;

/**
 * Base command that stores a reference to ApplicationContext.
 * All concrete commands extend this class.
 */
public abstract class BaseCommand implements Command {

    protected final ApplicationContext context;

    protected BaseCommand(ApplicationContext context) {
        this.context = context;
    }
}
