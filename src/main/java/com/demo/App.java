package com.demo;

import java.time.LocalDate;
import java.util.Scanner;

public class App 
{
    public static void main( String[] args )
    {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Welcome to the Tool Rental System");

        System.out.print("Enter tool code: ");
        String toolCode = scanner.nextLine();

        int rentalDays = Util.getValidatedInput(scanner, "Enter rental day count (1 or greater): ", 1, Integer.MAX_VALUE);
        int discountPercent = Util.getValidatedInput(scanner, "Enter discount percent (0-100): ", 0, 100);
        LocalDate checkoutDate = Util.getValidatedDate(scanner, "Enter checkout date (MM/dd/yy): ");

        try {
            RentalAgreement rentalAgreement = RentalService.checkout(toolCode, rentalDays, discountPercent, checkoutDate);
            rentalAgreement.printRentalAgreement();
        } catch (IllegalArgumentException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }


}
