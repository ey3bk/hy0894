package com.demo;

import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.time.LocalDate;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UtilTest {

    @Test
    public void testGetValidatedInputValid() {
        String input = "5\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        int result = Util.getValidatedInput(scanner, "Enter a number between 1 and 10: ", 1, 10);
        assertEquals(5, result);
    }

    @Test
    public void testGetValidatedInputInvalidThenValid() {
        String input = "abc\n15\n8\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        int result = Util.getValidatedInput(scanner, "Enter a number between 1 and 10: ", 1, 10);
        assertEquals(8, result);
    }

    @Test
    public void testGetValidatedDateValid() {
        String input = "08/15/24\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        LocalDate result = Util.getValidatedDate(scanner, "Enter a date (MM/dd/yy): ");
        assertEquals(LocalDate.of(2024, 8, 15), result);
    }

    @Test
    public void testGetValidatedDateInvalidThenValid() {
        String input = "2024-08-15\n08/15/24\n";
        InputStream in = new ByteArrayInputStream(input.getBytes());
        Scanner scanner = new Scanner(in);

        LocalDate result = Util.getValidatedDate(scanner, "Enter a date (MM/dd/yy): ");
        assertEquals(LocalDate.of(2024, 8, 15), result);
    }
}