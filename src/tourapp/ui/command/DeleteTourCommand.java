package tourapp.ui.command;

import java.util.Scanner;

public class DeleteTourCommand extends BaseCommand {

    public DeleteTourCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        Scanner scanner = context.getScanner();
        System.out.println("--- Delete tour ---");

        long id = ConsoleInputUtils.readLong(scanner, "Enter tour id to delete: ");
        boolean ok = context.getCatalogManager().deleteTour(id);
        if (ok) {
            System.out.println("Tour deleted successfully.");
        } else {
            System.out.println("Tour with id " + id + " not found.");
        }
    }
}
