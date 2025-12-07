package tourapp.ui.command;

public class SearchToursCommand extends BaseCommand {

    public SearchToursCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        System.out.println("[Command] Search and filter tours (logic will be implemented on stage 3).");
        context.getCatalogManager().searchAndFilterTours();
    }
}
