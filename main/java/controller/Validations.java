package controller;

import java.util.Scanner;

public class Validations {

    public static boolean getBooleanInput(Scanner scanner, String message) {
        while (true) {
            System.out.print(message + " (true/false): ");
            String input = scanner.nextLine().trim().toLowerCase();
            if (input.equals("true")) {
                return true;
            } else if (input.equals("false")) {
                return false;
            } else {
                System.out.println("Entrada no válida. Por favor, ingrese 'true' o 'false'.");
            }
        }
    }

    public static int getIntInput(Scanner scanner, String message) {
        while (true) {
            System.out.print(message + ": ");
            String input = scanner.nextLine().trim();
            try {
                return Integer.parseInt(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número entero.");
            }
        }
    }

    public static double getDoubleInput(Scanner scanner, String message) {
        while (true) {
            System.out.print(message + ": ");
            String input = scanner.nextLine().trim();
            try {
                return Double.parseDouble(input);
            } catch (NumberFormatException e) {
                System.out.println("Entrada no válida. Por favor, ingrese un número decimal.");
            }
        }
    }

    public static String getStringInput(Scanner scanner, String message) {
        System.out.print(message + ": ");
        return scanner.nextLine().trim();
    }

}
