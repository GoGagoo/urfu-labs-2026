package ru.fteam.examples.buggy;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public final class DateFormatExample {

    private static final DateFormat FORMAT =
            new SimpleDateFormat(
                    "yyyy-MM-dd HH:mm:ss");

    private DateFormatExample() {
    }

    public static void run() {
        System.out.println(
                "Текущая дата: " + getDate());
    }

    public static String getDate() {
        return FORMAT.format(new Date());
    }
}
