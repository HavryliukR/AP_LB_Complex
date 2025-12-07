package tourapp.ui.command;

public class HelpCommand extends BaseCommand {

    public HelpCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        System.out.println("Program help:");
        System.out.println("- Load catalog from file: initialize initial set of tours;");
        System.out.println("- Save catalog to file: write current catalog to disk;");
        System.out.println("- View all tours: show all available tours;");
        System.out.println("- Add / Edit / Delete tour: manage tour catalog;");
        System.out.println("- Search / Filter: find tours by parameters;");
        System.out.println("- Sort tours: order tours by price, days, rating;");
        System.out.println("- Favorite tours: manage list of preferred tours;");
        System.out.println("- Bookings: create and manage preliminary bookings;");
        System.out.println("- Exit: terminate the application.");
    }
}
