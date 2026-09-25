package ru.fteam.examples.buggy;

public final class SimpleTernaryNullExample {

    private SimpleTernaryNullExample() {
    }

    public static void run() {
        Integer number = ternary(false);
        System.out.println("Значение: " + number);
    }

    public static Integer ternary(boolean flag) {
        return flag ? 1 : null;
    }
}
