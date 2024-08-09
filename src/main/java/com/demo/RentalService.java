package com.demo;

import java.time.LocalDate;

public class RentalService {
    public static final String ERROR_RENTAL_DAYS = "Rental day count must be 1 or greater.";
    public static final String ERROR_DISCOUNT_PERCENT = "Discount percent must be between 0 and 100.";

    public static RentalAgreement checkout(String toolCode, int rentalDays, int discountPercent, LocalDate checkoutDate) throws IllegalArgumentException {
        if (rentalDays < 1) {
            throw new IllegalArgumentException(ERROR_RENTAL_DAYS);
        }

        if (discountPercent < 0 || discountPercent > 100) {
            throw new IllegalArgumentException(ERROR_DISCOUNT_PERCENT);
        }

        Tool tool = Tool.valueOf(toolCode);

        return new RentalAgreement(tool, rentalDays, discountPercent, checkoutDate);
    }

}