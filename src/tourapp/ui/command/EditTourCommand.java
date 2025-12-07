package tourapp.ui.command;

public class EditTourCommand extends BaseCommand {

    public EditTourCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        System.out.println("[Command] Edit existing tour (logic will be implemented on stage 3).");
        context.getCatalogManager().editTour();
    }
}
