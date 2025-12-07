package tourapp.ui.command;

public class SortToursCommand extends BaseCommand {

    public SortToursCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        System.out.println("[Command] Sort tours (logic will be implemented on stage 3).");
        context.getCatalogManager().sortTours();
    }
}
