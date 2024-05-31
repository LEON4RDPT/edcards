package com.edcards.edcards.Programa.Controllers;

public class ArredondarController {
    public static double roundToTwoDecimalPlaces(double value) {
        String formattedValue = String.format("%.2f", value);
        return Double.parseDouble(formattedValue);
    }
}
