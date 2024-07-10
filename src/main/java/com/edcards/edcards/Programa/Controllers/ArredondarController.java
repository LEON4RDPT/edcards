package com.edcards.edcards.Programa.Controllers;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class ArredondarController {
    public static double roundToTwoDecimalPlacesRetDouble(double value) {
        String formattedValue = String.format("%.2f", value);
        return Double.parseDouble(formattedValue);
    }


    private static final DecimalFormatSymbols symbols = new DecimalFormatSymbols(Locale.getDefault()); // or Locale.US, etc.
    private static final DecimalFormat df = new DecimalFormat("0.00", symbols); // Two decimal places format

    public static String roundToTwoDecimalPlaces(double value) {
        return df.format(value); // Formats directly to a string, no parsing needed
    }
}
