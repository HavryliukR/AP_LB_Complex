package tourapp.ui.command;

import java.util.Scanner;

public final class ConsoleInputUtils {

    private ConsoleInputUtils() {
    }

    public static String readNonEmptyString(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            if (!line.isEmpty()) {
                return line;
            }
            System.out.println("Value cannot be empty. Please try again.");
        }
    }

    public static int readIntInRange(Scanner scanner, String prompt, int min, int max) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                int value = Integer.parseInt(line);
                if (value < min || value > max) {
                    System.out.println("Please enter a number between " + min + " and " + max + ".");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid integer value. Please try again.");
            }
        }
    }

    public static long readLong(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Long.parseLong(line);
            } catch (NumberFormatException e) {
                System.out.println("Invalid long value. Please try again.");
            }
        }
    }

    public static double readDoubleMin(Scanner scanner, String prompt, double min) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                double value = Double.parseDouble(line);
                if (value < min) {
                    System.out.println("Value must be at least " + min + ". Please try again.");
                    continue;
                }
                return value;
            } catch (NumberFormatException e) {
                System.out.println("Invalid decimal number. Please try again.");
            }
        }
    }

    public static boolean readYesNo(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt + " (y/n): ");
            String line = scanner.nextLine().trim().toLowerCase();
            if ("y".equals(line) || "yes".equals(line)) {
                return true;
            }
            if ("n".equals(line) || "no".equals(line)) {
                return false;
            }
            System.out.println("Please enter 'y' or 'n'.");
        }
    }

    public static <E extends Enum<E>> E readEnum(Scanner scanner, String prompt, Class<E> enumClass) {
        while (true) {
            System.out.print(prompt + " ");
            System.out.print("(");
            E[] values = enumClass.getEnumConstants();
            for (int i = 0; i < values.length; i++) {
                System.out.print(values[i].name());
                if (i < values.length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.print("): ");

            String line = scanner.nextLine().trim().toUpperCase();
            try {
                return Enum.valueOf(enumClass, line);
            } catch (IllegalArgumentException e) {
                System.out.println("Invalid value. Please enter one of the listed options.");
            }
        }
    }
}
