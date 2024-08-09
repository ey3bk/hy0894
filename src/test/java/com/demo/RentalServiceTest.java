package com.demo;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;


public class RentalServiceTest {

    @Test
    public void testJAKRWith5DaysInvalidDiscount() {
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        int rentalDays = 5;
        int discountPercent = 101;

        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            RentalService.checkout(toolCode, rentalDays, discountPercent, checkoutDate);
        });
        assertEquals(RentalService.ERROR_DISCOUNT_PERCENT, exception.getMessage());
    }

    @Test
    public void testLADWWith3DaysAnd10PercentDiscount() {
        String toolCode = "LADW";
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
        int rentalDays = 3;
        int discountPercent = 10;

        RentalAgreement agreement = RentalService.checkout(toolCode, rentalDays, discountPercent, checkoutDate);
        assertEquals(LocalDate.of(2020, 7, 5), agreement.getDueDate());

        // July 3 (Friday): Observed holiday, not chargeable.
        // July 4 (Saturday): Actual holiday, not chargeable
        // July 5 (Sunday): Chargeable
        assertEquals(1, agreement.getChargeableDays());
        assertEquals(new BigDecimal("1.99"), agreement.getPreDiscountCharge());
        assertEquals(new BigDecimal("0.20"), agreement.getDiscountAmount());
        assertEquals(new BigDecimal("1.79"), agreement.getFinalCharge());
    }

    @Test
    public void testCHNSWith5DaysAnd25PercentDiscount() {
        String toolCode = "CHNS";
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        int rentalDays = 5;
        int discountPercent = 25;

        RentalAgreement agreement = RentalService.checkout(toolCode, rentalDays, discountPercent, checkoutDate);

        assertEquals(LocalDate.of(2015, 7, 7), agreement.getDueDate());

        // July 3, 2015 (Friday): Observed holiday, chargeable.
        // July 4, 2015 (Saturday): Actual holiday, chargeable.
        // July 5, 2015 (Sunday): Not chargeable.
        // July 6, 2015 (Monday): Regular weekday, chargeable.
        // July 7, 2015 (Tuesday): Regular weekday, chargeable.
        assertEquals(4, agreement.getChargeableDays());
        assertEquals(new BigDecimal("5.96"), agreement.getPreDiscountCharge());
        assertEquals(new BigDecimal("1.49"), agreement.getDiscountAmount());
        assertEquals(new BigDecimal("4.47"), agreement.getFinalCharge());
    }

    @Test
    public void testJAKDWith6DaysAndNoDiscount() {
        String toolCode = "JAKD";
        LocalDate checkoutDate = LocalDate.of(2015, 9, 3);
        int rentalDays = 6;
        int discountPercent = 0;

        RentalAgreement agreement = RentalService.checkout(toolCode, rentalDays, discountPercent, checkoutDate);

        assertEquals(LocalDate.of(2015, 9, 9), agreement.getDueDate());

        // September 4th (Friday): Chargeable
        // September 5th (Sat): No chargeable
        // September 6th (Sun): No chargeable
        // September 7th (Mon): No chargeable
        // September 8th (Tuesday): Chargeable
        // September 9th (Wednesday): Chargeable
        assertEquals(3, agreement.getChargeableDays());
        assertEquals(new BigDecimal("8.97"), agreement.getPreDiscountCharge());
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), agreement.getDiscountAmount());
        assertEquals(new BigDecimal("8.97"), agreement.getFinalCharge());
    }

    @Test
    public void testJAKRWith9DaysAndNoDiscount() {
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.of(2015, 7, 2);
        int rentalDays = 9;
        int discountPercent = 0;

        RentalAgreement agreement = RentalService.checkout(toolCode, rentalDays, discountPercent, checkoutDate);

        assertEquals(LocalDate.of(2015, 7, 11), agreement.getDueDate());

        // September 3 (Friday): Chargeable
        // September 4 (Sat): Not chargeable
        // September 5 (Sun): Not chargeable
        // September 6 (Mon): Not chargeable
        // September 7 (Tuesday): Chargeable
        // September 8 (Wed): Chargeable
        // September 9 (Thurs): Chargeable
        // September 10 (Fri): Chargeable
        // September 11 (Sat): Not chargeable
        assertEquals(5, agreement.getChargeableDays());
        assertEquals(new BigDecimal("14.95"), agreement.getPreDiscountCharge());
        assertEquals(BigDecimal.ZERO.setScale(2, RoundingMode.HALF_UP), agreement.getDiscountAmount());
        assertEquals(new BigDecimal("14.95"), agreement.getFinalCharge());
    }

    @Test
    public void testJAKRWith4DaysAnd50PercentDiscount() {
        String toolCode = "JAKR";
        LocalDate checkoutDate = LocalDate.of(2020, 7, 2);
        int rentalDays = 4;
        int discountPercent = 50;

        RentalAgreement agreement = RentalService.checkout(toolCode, rentalDays, discountPercent, checkoutDate);

        assertEquals(LocalDate.of(2020, 7, 6), agreement.getDueDate());

        // September 3 (Friday): Chargeable
        // September 4 (Sat): Not chargeable
        // September 5 (Sun): Not chargeable
        // September 6 (Mon): Not chargeable
        assertEquals(1, agreement.getChargeableDays());
        assertEquals(new BigDecimal("2.99"), agreement.getPreDiscountCharge());
        assertEquals(new BigDecimal("1.50"), agreement.getDiscountAmount());
        assertEquals(new BigDecimal("1.50"), agreement.getFinalCharge());
    }
}