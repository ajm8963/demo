package com.example.demo;

public class ISNBValidator {
    public static boolean isValidISBN(String isbn) {
        if (isbn == null || isbn.isEmpty()) return false;

        String cleanISBN = isbn.replaceAll("[\\s-]", "");


        if (cleanISBN.length() == 10) {
            return cleanISBN.matches("^\\d{9}[\\dXx]$");
        } else if (cleanISBN.length() == 13) {
            return cleanISBN.matches("^\\d{13}$");
        }

        return false;
    }
}
