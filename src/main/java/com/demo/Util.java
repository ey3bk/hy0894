package com.demo;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Util {
    public static int getValidatedInput(Scanner scanner, String prompt, int min, int max) {
        int value;
        while (true) {
            System.out.print(prompt);
            try {
                value = Integer.parseInt(scanner.nextLine());
                if (value >= min && value <= max) {
                    break;
                } else {
                    System.out.printf("Input must be between %d and %d. Please try again.%n", min, max);
                }
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Please enter a valid number.");
            }
        }
        return value;
    }

    public static LocalDate getValidatedDate(Scanner scanner, String prompt) {
        LocalDate date;
        while (true) {
            System.out.print(prompt);
            String dateInput = scanner.nextLine();
            try {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/dd/yy");
                date = LocalDate.parse(dateInput, formatter);
                break;
            } catch (DateTimeParseException e) {
                System.out.println("Invalid date format. Please use MM/dd/yy.");
            }
        }
        return date;
    }
}
