package com.demo;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RentalAgreementTest {

    @Test
    public void testPrintRentalAgreement() {
        Tool tool = Tool.LADW;
        int rentalDays = 3;
        int discountPercent = 10;
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);

        RentalAgreement agreement = new RentalAgreement(tool, rentalDays, discountPercent, checkoutDate);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream originalOut = System.out;
        System.setOut(new PrintStream(outputStream));

        agreement.printRentalAgreement();

        System.setOut(originalOut);

        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yy");
        DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");

        String expectedOutput = "Tool code: " + tool.getToolCode() + "\n" +
                "Tool type: " + tool.getToolType() + "\n" +
                "Tool brand: " + tool.getBrand() + "\n" +
                "Rental days: " + rentalDays + "\n" +
                "Check out date: " + checkoutDate.format(dateFormat) + "\n" +
                "Due date: " + agreement.getDueDate().format(dateFormat) + "\n" +
                "Daily rental charge: " + currencyFormat.format(tool.getDailyCharge()) + "\n" +
                "Chargeable days: " + agreement.getChargeableDays() + "\n" +
                "Pre-discount charge: " + currencyFormat.format(agreement.getPreDiscountCharge()) + "\n" +
                "Discount percent: " + discountPercent + "%" + "\n" +
                "Discount amount: " + currencyFormat.format(agreement.getDiscountAmount()) + "\n" +
                "Final charge: " + currencyFormat.format(agreement.getFinalCharge()) + "\n";

        assertEquals(expectedOutput, outputStream.toString());
    }
}