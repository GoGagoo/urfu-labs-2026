package ru.fteam.examples.fixed;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class FixedExamples {

    private static final double[] VALUES = {1.0, 2.0, 3.0};
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    private FixedExamples() {
    }

    public static Number explicitTernary(boolean flag) {
        if (flag) {
            return Integer.valueOf(1);
        }
        return Double.valueOf(2.0);
    }

    public static Integer nullableTernary(boolean firstFlag, boolean secondFlag) {
        if (firstFlag) {
            return Integer.valueOf(1);
        }
        return secondFlag ? Integer.valueOf(2) : null;
    }

    public static Double getValue(int index) {
        return index < 0 || index >= VALUES.length ? null : Double.valueOf(VALUES[index]);
    }

    public static String getDate() {
        return FORMATTER.format(LocalDateTime.now());
    }

    public static BigDecimal decimal() {
        return new BigDecimal("1.1");
    }

    public static boolean numericallyEqual(BigDecimal first, BigDecimal second) {
        return first.compareTo(second) == 0;
    }

    public static void printPortableNewline(String value) {
        System.out.printf("%s%n", value);
    }
}
