package tourapp.ui.command;

public class DeleteTourCommand extends BaseCommand {

    public DeleteTourCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        System.out.println("[Command] Delete tour (logic will be implemented on stage 3).");
        context.getCatalogManager().deleteTour();
    }
}
