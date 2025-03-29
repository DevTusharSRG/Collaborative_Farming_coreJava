package com.validation;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Validation {

    // Validate positive integer inputs (userId, serviceId, propertyId, duration)
    public static int validatePositiveInt(Scanner scanner, String prompt) {
        int value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextInt()) {
                value = scanner.nextInt();
                if (value > 0) {
                    scanner.nextLine(); // Clear the buffer
                    return value;
                } else {
                    System.out.println("Error: Value must be a positive number.");
                }
            } else {
                System.out.println("Error: Invalid input. Please enter a valid number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    // Validate positive double input (cost)
    public static double validatePositiveDouble(Scanner scanner, String prompt) {
        double value;
        while (true) {
            System.out.print(prompt);
            if (scanner.hasNextDouble()) {
                value = scanner.nextDouble();
                if (value > 0) {
                    scanner.nextLine(); // Clear the buffer
                    return value;
                } else {
                    System.out.println("Error: Value must be a positive number.");
                }
            } else {
                System.out.println("Error: Invalid input. Please enter a valid decimal number.");
                scanner.next(); // Clear invalid input
            }
        }
    }

    // Validate non-empty string inputs (State, District, Taluka, Village)
    public static String validateNonEmptyString(Scanner scanner, String prompt) {
        String input;
        while (true) {
            System.out.print(prompt);
            input = scanner.nextLine().trim();
            if (!input.isEmpty()) {
                return input;
            } else {
                System.out.println("Error: This field cannot be empty.");
            }
        }
    }

    // Validate date format (YYYY-MM-DD) and ensure end date is after start date
    public static String validateDate(Scanner scanner, String prompt) {
        String dateStr;
        while (true) {
            System.out.print(prompt);
            dateStr = scanner.nextLine().trim();
            try {
                LocalDate.parse(dateStr); // Try parsing to check format
                return dateStr;
            } catch (DateTimeParseException e) {
                System.out.println("Error: Invalid date format. Please enter in YYYY-MM-DD format.");
            }
        }
    }

    // Validate start date and end date (End date must be after start date)
    public static String validateEndDate(Scanner scanner, String startDate) {
        String endDate;
        while (true) {
            endDate = validateDate(scanner, "Enter End Date (YYYY-MM-DD): ");
            if (LocalDate.parse(endDate).isAfter(LocalDate.parse(startDate))) {
                return endDate;
            } else {
                System.out.println("Error: End date must be after start date.");
            }
        }
    }
}
