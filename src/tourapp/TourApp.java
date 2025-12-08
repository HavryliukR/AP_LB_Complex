package tourapp;

import tourapp.core.TourCatalogManager;
import tourapp.ui.command.ApplicationContext;
import tourapp.ui.menu.ConsoleMenu;

import java.util.Scanner;

import tourapp.logging.LoggingConfig;

import java.util.logging.Level;
import java.util.logging.Logger;

public class TourApp {

    private static final Logger LOGGER = LoggingConfig.getLogger(TourApp.class);

    public static void main(String[] args) {
        LOGGER.info("TourApp started");
        try {
            Scanner scanner = new Scanner(System.in);
            TourCatalogManager manager = new TourCatalogManager();
            ApplicationContext context = new ApplicationContext(scanner, manager);
            ConsoleMenu menu = new ConsoleMenu(context);
            menu.start();
        } catch (Exception e) {
            LOGGER.log(Level.SEVERE, "Unhandled exception in main()", e);
        } finally {
            LOGGER.info("TourApp finished");
        }
    }


//    public static void main(String[] args) {
//        LOGGER.info("TourApp started");
//
//        // ==== ТЕСТ КРИТИЧНОГО ЛОГУ ====
//        LOGGER.log(Level.SEVERE,
//                "Test critical error for e-mail handler",
//                new RuntimeException("This is a test exception from TourApp"));
//        // ==== КІНЕЦЬ ТЕСТУ ====
//
//        try {
//            Scanner scanner = new Scanner(System.in);
//            TourCatalogManager manager = new TourCatalogManager();
//            ApplicationContext context = new ApplicationContext(scanner, manager);
//            ConsoleMenu menu = new ConsoleMenu(context);
//            menu.start();
//        } catch (Exception e) {
//            LOGGER.log(Level.SEVERE, "Unhandled exception in main()", e);
//        } finally {
//            LOGGER.info("TourApp finished");
//        }
//    }

}
