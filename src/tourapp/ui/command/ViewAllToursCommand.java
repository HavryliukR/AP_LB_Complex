package tourapp.ui.command;

public class ViewAllToursCommand extends BaseCommand {

    public ViewAllToursCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        context.getCatalogManager().viewAllTours();
    }
}
