package ru.fteam.examples.buggy;

public final class TernaryBoxingExample {

    private TernaryBoxingExample() {
    }

    public static void run() {
        Number number = ternary(true);
        System.out.println("Значение: " + number);
        System.out.println("Тип: "
                + number.getClass().getSimpleName());
    }

    public static Number ternary(boolean flag) {
        return flag
                ? new Integer(1)
                : new Double(2.0);
    }
}
