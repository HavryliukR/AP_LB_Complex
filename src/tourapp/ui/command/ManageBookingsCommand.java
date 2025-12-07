package tourapp.ui.command;

public class ManageBookingsCommand extends BaseCommand {

    public ManageBookingsCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        System.out.println("[Command] Manage bookings (logic will be implemented on stage 3).");
        context.getCatalogManager().manageBookings();
    }
}
