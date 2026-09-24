package ru.fteam.examples.buggy;

public final class PrimitiveReturnNullExample {

    private static final double[] VALUES =
            {1.0, 2.0, 3.0};

    private PrimitiveReturnNullExample() {
    }

    public static void run() {
        System.out.println(
                "Значение: " + getValue(5));
    }

    public static double getValue(int index) {
        return index < 0 || index >= VALUES.length
                ? null
                : VALUES[index];
    }
}
