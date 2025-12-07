package tourapp.ui.command;

import java.util.Scanner;

public class LoadFromFileCommand extends BaseCommand {

    public LoadFromFileCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        Scanner scanner = context.getScanner();
        System.out.print("Enter path to file for loading catalog: ");
        String path = scanner.nextLine().trim();

        if (path.isEmpty()) {
            System.out.println("Path cannot be empty.");
            return;
        }

        context.getCatalogManager().loadFromFile(path);
    }
}
