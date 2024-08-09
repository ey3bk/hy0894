package com.demo;

import lombok.Data;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

@Data
public class RentalAgreement {
    private Tool tool;
    private int rentalDays;
    private LocalDate checkoutDate;
    private LocalDate dueDate;
    private int chargeableDays;
    private BigDecimal preDiscountCharge;
    private int discountPercent;
    private BigDecimal discountAmount;
    private BigDecimal finalCharge;

    public RentalAgreement(Tool tool, int rentalDays, int discountPercent, LocalDate checkoutDate) {
        this.tool = tool;
        this.rentalDays = rentalDays;
        this.discountPercent = discountPercent;
        this.checkoutDate = checkoutDate;
        this.calculateDueDate();
        this.calculateCharges();
    }

    private void calculateDueDate() {
        this.dueDate = this.checkoutDate.plusDays(this.rentalDays);
    }

    private void calculateCharges() {
        this.chargeableDays = calculateChargeableDays();

        BigDecimal dailyCharge = this.tool.getDailyCharge();
        this.preDiscountCharge = dailyCharge.multiply(new BigDecimal(this.chargeableDays));

        BigDecimal discountRate = new BigDecimal(this.discountPercent).divide(new BigDecimal("100"));
        this.discountAmount = this.preDiscountCharge.multiply(discountRate);
        this.finalCharge = this.preDiscountCharge.subtract(this.discountAmount);

        this.preDiscountCharge = this.preDiscountCharge.setScale(2, RoundingMode.HALF_UP);
        this.discountAmount = this.discountAmount.setScale(2, RoundingMode.HALF_UP);
        this.finalCharge = this.finalCharge.setScale(2, RoundingMode.HALF_UP);
    }

    private int calculateChargeableDays() {
        int chargeableDays = 0;
        LocalDate currentDate = this.checkoutDate.plusDays(1);

        for (int i = 0; i < this.rentalDays; i++) {
            boolean isWeekend = currentDate.getDayOfWeek().getValue() >= 6;
            boolean isHoliday = isIndependenceDay(currentDate) || isLaborDay(currentDate);

            if ((this.tool.isWeekdayCharge() && !isWeekend && !isHoliday) ||
                    (this.tool.isWeekendCharge() && isWeekend && !isHoliday) ||
                    (this.tool.isHolidayCharge() && isHoliday)) {
                chargeableDays++;
            }

            currentDate = currentDate.plusDays(1);
        }

        return chargeableDays;
    }

    private boolean isIndependenceDay(LocalDate date) {
        LocalDate independenceDay = LocalDate.of(date.getYear(), 7, 4);
        LocalDate observedIndependenceDay = independenceDay;

        if (independenceDay.getDayOfWeek().getValue() == 6) { // Saturday
            observedIndependenceDay = independenceDay.minusDays(1);
        } else if (independenceDay.getDayOfWeek().getValue() == 7) { // Sunday
            observedIndependenceDay = independenceDay.plusDays(1);
        }
        return date.equals(independenceDay) || date.equals(observedIndependenceDay);
    }

    private boolean isLaborDay(LocalDate date) {
        LocalDate laborDay = LocalDate.of(date.getYear(), 9, 1);
        while (laborDay.getDayOfWeek().getValue() != 1) { // 1 = Monday
            laborDay = laborDay.plusDays(1);
        }
        return date.equals(laborDay);
    }

    public void printRentalAgreement() {
        DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("MM/dd/yy");
        DecimalFormat currencyFormat = new DecimalFormat("$#,##0.00");

        System.out.println("Tool code: " + this.tool.getToolCode());
        System.out.println("Tool type: " + this.tool.getToolType());
        System.out.println("Tool brand: " + this.tool.getBrand());
        System.out.println("Rental days: " + this.rentalDays);
        System.out.println("Check out date: " + this.checkoutDate.format(dateFormat));
        System.out.println("Due date: " + this.dueDate.format(dateFormat));
        System.out.println("Daily rental charge: " + currencyFormat.format(this.tool.getDailyCharge()));
        System.out.println("Chargeable days: " + this.chargeableDays);
        System.out.println("Pre-discount charge: " + currencyFormat.format(this.preDiscountCharge));
        System.out.println("Discount percent: " + this.discountPercent + "%");
        System.out.println("Discount amount: " + currencyFormat.format(this.discountAmount));
        System.out.println("Final charge: " + currencyFormat.format(this.finalCharge));
    }
}