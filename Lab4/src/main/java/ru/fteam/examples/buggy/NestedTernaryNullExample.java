package ru.fteam.examples.buggy;

public final class NestedTernaryNullExample {

    private NestedTernaryNullExample() {
    }

    public static void run() {
        Integer number = ternary(false, false);
        System.out.println("Значение: " + number);
    }

    public static Integer ternary(
            boolean firstFlag,
            boolean secondFlag) {
        return firstFlag
                ? 1
                : secondFlag ? 2 : null;
    }
}
