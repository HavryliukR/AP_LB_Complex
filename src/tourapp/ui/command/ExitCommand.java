package tourapp.ui.command;

public class ExitCommand extends BaseCommand {

    public ExitCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        System.out.println("Exiting application. Goodbye!");
    }
}
