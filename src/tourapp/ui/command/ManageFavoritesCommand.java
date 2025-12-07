package tourapp.ui.command;

public class ManageFavoritesCommand extends BaseCommand {

    public ManageFavoritesCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        System.out.println("[Command] Manage favorite tours (logic will be implemented on stage 3).");
        context.getCatalogManager().manageFavorites();
    }
}
