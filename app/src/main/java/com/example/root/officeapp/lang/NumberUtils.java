package com.example.root.officeapp.lang;

import android.util.Log;

public final class NumberUtils {
    public static final String TAG = NumberUtils.class.getSimpleName();

    private NumberUtils() {
        throw new AssertionError();
    }

    public static Long tryParseLong(String number) {
        return tryParseLong(number, Long.valueOf(0));
    }

    public static Long tryParseLong(String number, Long fallback) {
        try {
            return Long.valueOf(Long.parseLong(number));
        } catch (NumberFormatException e) {
            Log.i(TAG, "cannot convert the string to the number");
            return fallback;
        }
    }

    public static Integer tryParseInt(String number) {
        return tryParseInt(number, Integer.valueOf(0));
    }

    public static Integer tryParseInt(String number, Integer fallback) {
        try {
            return Integer.valueOf(Integer.parseInt(number));
        } catch (NumberFormatException e) {
            Log.i(TAG, "cannot convert the string to the number");
            return fallback;
        }
    }

    public static Float tryParseFloat(String number) {
        return tryParseFloat(number, Float.valueOf(0.0f));
    }

    public static Float tryParseFloat(String number, Float fallback) {
        try {
            return Float.valueOf(Float.parseFloat(number));
        } catch (NumberFormatException e) {
            Log.i(TAG, "cannot convert the string to the number");
            return fallback;
        }
    }

    public static Double tryParseDouble(String number) {
        return tryParseDouble(number, Double.valueOf(0.0d));
    }

    public static Double tryParseDouble(String number, Double fallback) {
        try {
            return Double.valueOf(Double.parseDouble(number));
        } catch (NumberFormatException e) {
            Log.i(TAG, "cannot convert the string to the number");
            return fallback;
        }
    }

    public static boolean isNumber(String number) {
        return (tryParseInt(number, null) == null && tryParseLong(number, null) == null && tryParseFloat(number, null) == null && tryParseDouble(number, null) == null) ? false : true;
    }
}
