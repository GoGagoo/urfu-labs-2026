package ru.fteam;

import java.util.LinkedHashMap;
import java.util.Map;
import ru.fteam.examples.buggy.BigDecimalFromDoubleExample;
import ru.fteam.examples.buggy.BigDecimalEqualsExample;
import ru.fteam.examples.buggy.DateFormatExample;
import ru.fteam.examples.buggy.NestedTernaryNullExample;
import ru.fteam.examples.buggy.PlatformNewlineExample;
import ru.fteam.examples.buggy.PrimitiveReturnNullExample;
import ru.fteam.examples.buggy.SimpleTernaryNullExample;
import ru.fteam.examples.buggy.TernaryBoxingExample;

public final class Lab4Application {

    private Lab4Application() {
    }

    public static void main(String[] args) {
        Map<String, Runnable> examples = new LinkedHashMap<String, Runnable>();
        examples.put("1", TernaryBoxingExample::run);
        examples.put("2", SimpleTernaryNullExample::run);
        examples.put("3", NestedTernaryNullExample::run);
        examples.put("4", PrimitiveReturnNullExample::run);
        examples.put("5", DateFormatExample::run);
        examples.put("6", BigDecimalFromDoubleExample::run);
        examples.put("7", BigDecimalEqualsExample::run);
        examples.put("8", PlatformNewlineExample::run);

        Map<String, String> titles = new LinkedHashMap<String, String>();
        titles.put("1", "Тернарный оператор и упаковка");
        titles.put("2", "Присваивание null объекту Integer");
        titles.put("3", "Вложенный тернарный оператор");
        titles.put("4", "null как результат для double");
        titles.put("5", "Статический SimpleDateFormat");
        titles.put("6", "BigDecimal из double");
        titles.put("7", "BigDecimal.equals и scale");
        titles.put("8", "Перенос строки в printf");

        if (args.length == 0 || "all".equalsIgnoreCase(args[0])) {
            for (String number : examples.keySet()) {
                runExample(number, titles.get(number), examples.get(number));
            }
        } else {
            for (String number : args) {
                Runnable example = examples.get(number);
                if (example == null) {
                    System.err.println("Неизвестный номер примера: " + number);
                    System.err.println("Допустимые значения: 1, 2, 3, 4, 5, 6, 7, 8 или all");
                    System.exit(2);
                }
                runExample(number, titles.get(number), example);
            }
        }

    }

    private static void runExample(String number, String title, Runnable example) {
        System.out.println("--- " + number + ". " + title + " ---");

        try {
            example.run();
        } catch (RuntimeException exception) {
            System.out.println("Ожидаемое исключение: "
                    + exception.getClass().getSimpleName()
                    + " — " + exception.getMessage());
        }

        System.out.println();
    }
}
