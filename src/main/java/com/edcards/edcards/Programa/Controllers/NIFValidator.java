package com.edcards.edcards.Programa.Controllers;

public class NIFValidator {

    public static boolean isValidNIF(String nif) {
        return nif.length() == 9 && calculateVerificationDigit(Integer.parseInt(nif.substring(0, 8))) == Integer.parseInt(nif.substring(8));
    }

    private static int calculateVerificationDigit(int digits) {
        int sum = 0;
        for (int i = 0; i < 8; i++) {
            int digit = digits % 10;
            sum += digit * (i % 2 == 0 ? 1 : 2);
            digits /= 10;
        }
        return (sum % 11 == 0) ? 0 : 11 - sum % 11;
    }
}
