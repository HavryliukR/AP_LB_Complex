package tourapp.ui.menu;

import tourapp.logging.LoggingConfig;
import tourapp.ui.command.*;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleMenu {

    private static final Logger LOGGER = LoggingConfig.getLogger(ConsoleMenu.class);

    private final ApplicationContext context;
    private final Map<MenuOption, Command> commands;

    public ConsoleMenu(ApplicationContext context) {
        this.context = context;
        Map<MenuOption, Command> map = new LinkedHashMap<>();
        map.put(MenuOption.LOAD_FROM_FILE, new LoadFromFileCommand(context));
        map.put(MenuOption.SAVE_TO_FILE, new SaveToFileCommand(context));
        map.put(MenuOption.VIEW_ALL_TOURS, new ViewAllToursCommand(context));
        map.put(MenuOption.SEARCH_TOURS, new SearchToursCommand(context));
        map.put(MenuOption.SORT_TOURS, new SortToursCommand(context));
        map.put(MenuOption.ADD_TOUR, new AddTourCommand(context));
        map.put(MenuOption.EDIT_TOUR, new EditTourCommand(context));
        map.put(MenuOption.DELETE_TOUR, new DeleteTourCommand(context));
        map.put(MenuOption.MANAGE_FAVORITES, new ManageFavoritesCommand(context));
        map.put(MenuOption.MANAGE_BOOKINGS, new ManageBookingsCommand(context));
        map.put(MenuOption.HELP, new HelpCommand(context));
        map.put(MenuOption.EXIT, new ExitCommand(context));
        this.commands = map;
    }

    public void start() {
        LOGGER.info("Console menu started");
        boolean exit = false;

        while (!exit) {
            try {
                printMenu();
                int choice = readMenuChoice();

                MenuOption option = MenuOption.fromCode(choice);
                if (option == null) {
                    LOGGER.warning("Unknown menu option selected: " + choice);
                    System.out.println("Unknown menu option. Please try again.");
                    continue;
                }

                LOGGER.info("Menu option selected: " + option);
                Command command = commands.get(option);
                if (command == null) {
                    LOGGER.warning("No command registered for menu option: " + option);
                    System.out.println("Sorry, this option is not implemented yet.");
                    continue;
                }

                command.execute();

                if (option == MenuOption.EXIT) {
                    exit = true;
                }
            } catch (Exception e) {
                LOGGER.log(Level.SEVERE, "Unexpected error in ConsoleMenu loop", e);
                System.out.println("Unexpected error, please try again.");
            }
        }

        LOGGER.info("Console menu finished");
    }

    private void printMenu() {
        System.out.println("===== TOUR APP MENU =====");
        for (MenuOption option : MenuOption.values()) {
            System.out.printf("%2d - %s%n", option.getCode(), option.getDescription());
        }
        System.out.print("Enter menu option: ");
    }

    private int readMenuChoice() {
        Scanner scanner = context.getScanner();
        while (true) {
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.print("Invalid number, please try again: ");
            }
        }
    }
}
