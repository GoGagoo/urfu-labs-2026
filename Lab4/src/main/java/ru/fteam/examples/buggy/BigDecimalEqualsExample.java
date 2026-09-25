package ru.fteam.examples.buggy;

import java.math.BigDecimal;

public final class BigDecimalEqualsExample {

    private BigDecimalEqualsExample() {
    }

    public static void run() {
        BigDecimal d1 = new BigDecimal("1.1");
        BigDecimal d2 = new BigDecimal("1.10");
        System.out.println(d1.equals(d2));
    }
}
