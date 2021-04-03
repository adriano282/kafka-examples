package com.kafkaexample.converter;

public class NumberConverter {
    private static NumberConverter
            instance = new NumberConverter();

    private NumberConverter() {}

    public static NumberConverter instance() {
        if (instance == null) {
            instance = new NumberConverter();
        }
        return instance;
    }

    public Integer doubleToInt(Double doubleValue) {
        if (doubleValue == null) return null;

        String stringDoubleWithoutPoint = doubleValue.toString().replace(".", "");

        return Integer.parseInt(stringDoubleWithoutPoint);
    }
}
