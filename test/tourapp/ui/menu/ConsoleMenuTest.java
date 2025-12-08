package tourapp.ui.menu;

import org.junit.jupiter.api.Test;
import tourapp.core.TourCatalogManager;
import tourapp.ui.command.ApplicationContext;

import java.io.*;
import java.lang.reflect.Field;
import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.*;

public class ConsoleMenuTest {

    @Test
    void startExitsOnExitOption() {
        String input = "12\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();
        ApplicationContext context = new ApplicationContext(scanner, manager);

        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            ConsoleMenu menu = new ConsoleMenu(context);
            menu.start();
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void startHandlesUnknownOption() {
        String input = "999\n12\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();
        ApplicationContext context = new ApplicationContext(scanner, manager);

        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            ConsoleMenu menu = new ConsoleMenu(context);
            menu.start();
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    @SuppressWarnings("unchecked")
    void startHandlesMenuOptionWithoutCommand() throws Exception {
        String input = "11\n12\n"; // 11 = HELP
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();
        ApplicationContext context = new ApplicationContext(scanner, manager);

        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            ConsoleMenu menu = new ConsoleMenu(context);

            Field commandsField = ConsoleMenu.class.getDeclaredField("commands");
            commandsField.setAccessible(true);
            Map<MenuOption, ?> commands = (Map<MenuOption, ?>) commandsField.get(menu);
            commands.remove(MenuOption.HELP);

            menu.start();
        } finally {
            System.setOut(originalOut);
        }
    }

    @Test
    void startHandlesNonNumericInput() {
        String input = "abc\n12\n";
        Scanner scanner = new Scanner(new ByteArrayInputStream(input.getBytes(StandardCharsets.UTF_8)));
        TourCatalogManager manager = new TourCatalogManager();
        ApplicationContext context = new ApplicationContext(scanner, manager);

        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(new ByteArrayOutputStream()));
        try {
            ConsoleMenu menu = new ConsoleMenu(context);
            menu.start();
        } finally {
            System.setOut(originalOut);
        }
    }

}
