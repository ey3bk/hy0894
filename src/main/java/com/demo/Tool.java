package com.demo;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public enum Tool {
    LADW("LADW", "Ladder", "Werner", new BigDecimal("1.99"), true, true, false),
    CHNS("CHNS", "Chainsaw", "Stihl", new BigDecimal("1.49"), true, false, true),
    JAKD("JAKD", "Jackhammer", "DeWalt", new BigDecimal("2.99"), true, false, false),
    JAKR("JAKR", "Jackhammer", "Ridgid", new BigDecimal("2.99"), true, false, false);

    private String toolCode;
    private String toolType;
    private String brand;
    private BigDecimal dailyCharge;
    private boolean isWeekdayCharge;
    private boolean isWeekendCharge;
    private boolean isHolidayCharge;

    Tool(String toolCode, String toolType, String brand, BigDecimal dailyCharge,
         boolean isWeekdayCharge, boolean isWeekendCharge, boolean isHolidayCharge) {
        this.toolCode = toolCode;
        this.toolType = toolType;
        this.brand = brand;
        this.dailyCharge = dailyCharge;
        this.isWeekdayCharge = isWeekdayCharge;
        this.isWeekendCharge = isWeekendCharge;
        this.isHolidayCharge = isHolidayCharge;
    }
}