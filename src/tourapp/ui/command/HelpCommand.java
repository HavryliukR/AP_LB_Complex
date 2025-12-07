package tourapp.ui.command;

public class HelpCommand extends BaseCommand {

    public HelpCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        System.out.println("Program help:");
        System.out.println("1. Load catalog from file - initialize tour list from file.");
        System.out.println("2. Save catalog to file - save current tour list to file.");
        System.out.println("3. View all tours - print all available tours.");
        System.out.println("4. Add / Edit / Delete tour - manage tour catalog.");
        System.out.println("7. Search / filter tours - find tours by type, country, transport, food, days, price.");
        System.out.println("8. Sort tours - order tours by price, days, or rating.");
        System.out.println("9. Favorite tours - manage list of preferred tours.");
        System.out.println("10. Bookings - create and manage bookings.");
        System.out.println("12. Exit - terminate the application.");
    }
}
