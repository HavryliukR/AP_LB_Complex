package tourapp.ui.menu;

import tourapp.ui.command.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

/**
 * Console menu that prints options and executes corresponding commands.
 */
public class ConsoleMenu {

    private final ApplicationContext context;
    private final Map<MenuOption, Command> commands = new HashMap<>();
    private boolean running = true;

    public ConsoleMenu(ApplicationContext context) {
        this.context = context;
        initCommands();
    }

    private void initCommands() {
        commands.put(MenuOption.LOAD_FROM_FILE, new LoadFromFileCommand(context));
        commands.put(MenuOption.SAVE_TO_FILE, new SaveToFileCommand(context));
        commands.put(MenuOption.VIEW_ALL_TOURS, new ViewAllToursCommand(context));
        commands.put(MenuOption.ADD_TOUR, new AddTourCommand(context));
        commands.put(MenuOption.EDIT_TOUR, new EditTourCommand(context));
        commands.put(MenuOption.DELETE_TOUR, new DeleteTourCommand(context));
        commands.put(MenuOption.SEARCH_TOURS, new SearchToursCommand(context));
        commands.put(MenuOption.SORT_TOURS, new SortToursCommand(context));
        commands.put(MenuOption.MANAGE_FAVORITES, new ManageFavoritesCommand(context));
        commands.put(MenuOption.MANAGE_BOOKINGS, new ManageBookingsCommand(context));
        commands.put(MenuOption.HELP, new HelpCommand(context));
        commands.put(MenuOption.EXIT, new ExitCommand(context));
    }

    public void start() {
        Scanner scanner = context.getScanner();

        while (running) {
            printMenu();
            System.out.print("Choose menu option: ");

            String input = scanner.nextLine();
            int code;
            try {
                code = Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Invalid value. Please try again.");
                continue;
            }

            MenuOption option = MenuOption.fromCode(code);
            if (option == null) {
                System.out.println("Unknown menu option. Please try again.");
                continue;
            }

            Command command = commands.get(option);
            if (command == null) {
                System.out.println("Command for this menu option is not implemented yet.");
                continue;
            }

            command.execute();

            if (option == MenuOption.EXIT) {
                running = false;
            }

            System.out.println();
        }
    }

    private void printMenu() {
        System.out.println("=== Main menu ===");
        for (MenuOption option : MenuOption.values()) {
            System.out.printf("%d. %s%n", option.getCode(), option.getDescription());
        }
    }
}
