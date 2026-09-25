package ru.fteam.examples.buggy;

public final class PlatformNewlineExample {

    private PlatformNewlineExample() {
    }

    public static void run() {
        System.out.printf("%s\n", "str#1");
        System.out.println("str#2");
    }
}
