package tourapp.logging;

import java.io.IOException;
import java.util.logging.*;

public final class LoggingConfig {

    private static boolean initialized = false;

    private LoggingConfig() {
    }

    public static synchronized Logger getLogger(Class<?> clazz) {
        if (!initialized) {
            configureRootLogger();
            initialized = true;
        }
        return Logger.getLogger(clazz.getName());
    }

    private static void configureRootLogger() {
        Logger root = Logger.getLogger("");

        // Remove default handlers (to avoid duplicate logs)
        for (Handler handler : root.getHandlers()) {
            root.removeHandler(handler);
        }

        root.setLevel(Level.INFO);

        // 1) File handler
        try {
            // Log file in working directory, append = true
            FileHandler fileHandler = new FileHandler("tour_app.log", true);
            fileHandler.setEncoding("UTF-8");
            fileHandler.setFormatter(new SimpleFormatter());
            fileHandler.setLevel(Level.INFO);
            root.addHandler(fileHandler);
        } catch (IOException e) {
            System.err.println("Failed to create file handler for logger: " + e.getMessage());
        }

        // 2) Console handler
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setLevel(Level.INFO);
        root.addHandler(consoleHandler);

        // 3) E-mail handler for critical errors (SEVERE)
        Handler emailHandler = createEmailHandler();
        if (emailHandler != null) {
            root.addHandler(emailHandler);
        }
    }

    private static Handler createEmailHandler() {
        try {



            String smtpHost = "smtp.ukr.net";
            int smtpPort = 465;

            String smtpUser = "tourapp.test@ukr.net";       // твоя нова пошта
            String smtpPassword = "l0tLag1eYlOmjZxO";    // пароль від цієї пошти

            String fromAddress = "tourapp.test@ukr.net";    // від кого
            String toAddress = "roman.havryliuk.oi.2024@lpnu.ua"; // куди (тобі на універську)


            boolean useTls = false;
            boolean useSsl = true;

            CriticalEmailHandler handler = new CriticalEmailHandler(
                    smtpHost,
                    smtpPort,
                    smtpUser,
                    smtpPassword,
                    fromAddress,
                    toAddress,
                    useTls,
                    useSsl
            );
            handler.setLevel(Level.SEVERE);
            return handler;
        } catch (Exception ex) {
            System.err.println("Failed to create CriticalEmailHandler: " + ex.getMessage());
            return null;
        }
    }

}

