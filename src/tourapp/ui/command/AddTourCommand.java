package tourapp.ui.command;

public class AddTourCommand extends BaseCommand {

    public AddTourCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        System.out.println("[Command] Add new tour (logic will be implemented on stage 3).");
        context.getCatalogManager().addTour();
    }
}
