package tourapp.ui.command;

import java.util.Scanner;

public class SaveToFileCommand extends BaseCommand {

    public SaveToFileCommand(ApplicationContext context) {
        super(context);
    }

    @Override
    public void execute() {
        Scanner scanner = context.getScanner();
        System.out.print("Enter path to file for saving catalog: ");
        String path = scanner.nextLine();

        context.getCatalogManager().saveToFile(path);
    }
}
