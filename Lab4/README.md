# Лабораторная работа №4 — статический анализ Java-кода

Самодостаточная прикладная часть лабораторной работы по теме «Статический
анализ кода». Проект собирается Maven Wrapper и использует SpotBugs 4.10.4 с
расширением `sb-contrib`.

## Структура лабораторной

В работе **три основных задания**, а не восемь:

1. Запустить и проанализировать восемь небольших Java-примеров.
2. Проанализировать учебный проект `library` с аннотациями JSR-305.
3. Проанализировать готовую библиотеку `colt.jar`.

## Требования

- JDK 17 или новее;
- интернет при первом запуске для загрузки Maven и SpotBugs;
- macOS/Linux либо Git Bash/WSL на Windows.

Maven устанавливать отдельно не нужно.

## Последовательный запуск

```bash
cd lab4-static-analysis
./run-task1.sh
./run-task2.sh
./run-task3.sh
```

Или выполнить всё одной командой:

```bash
./run-all.sh
```

После выполнения откройте:

- `reports/task1-examples.html` — восемь примеров задания 1;
- `reports/task2-library.html` — проект `library`;
- `reports/colt-spotbugs.html` — десять типов проблем Colt из отчёта;
- `reports/colt-all-warnings.xml` — полный результат анализа Colt.

## Запуск отдельного примера задания 1

```bash
./run-example.sh 1
./run-example.sh 2
./run-example.sh 3
./run-example.sh 4
./run-example.sh 5
./run-example.sh 6
./run-example.sh 7
./run-example.sh 8
```

Можно передать несколько номеров или запустить сразу все:

```bash
./run-example.sh 1 3 6
./run-example.sh all
```

Исключения в примерах 3 и 4 возникают намеренно и перехватываются
демонстрационным приложением.

## Что показывает каждый пример

| № | Класс | Проблема | Правильный подход |
|---|---|---|---|
| 1 | `TernaryBoxingExample` | Приведение `Integer` и `Double` к `double` | Явное ветвление или одинаковые типы |
| 2 | `SimpleTernaryNullExample` | Возможный `null` в `Integer` | Явный nullable-контракт |
| 3 | `NestedTernaryNullExample` | Распаковка `null` | Обычный `if` без неявной распаковки |
| 4 | `PrimitiveReturnNullExample` | Возврат `null` как `double` | `Double`, `OptionalDouble` или исключение |
| 5 | `DateFormatExample` | Общий `SimpleDateFormat` не потокобезопасен | `DateTimeFormatter` |
| 6 | `BigDecimalFromDoubleExample` | Перенос погрешности `double` | Строковый конструктор или `valueOf` |
| 7 | `BigDecimalEqualsExample` | `equals` учитывает scale | Численное сравнение через `compareTo` |
| 8 | `PlatformNewlineExample` | `\n` в `printf` зависит от платформы | `%n` |

Проблемные реализации находятся в `ru.fteam.examples.buggy`, исправленные — в
`ru.fteam.examples.fixed`. Исходный проект второго задания находится в
`bookstore`, исправленная версия — в `bookstore.fixed`.

## Проверка перед отправкой

```bash
./run-tests.sh
./run-all.sh
```

Библиотеки задания 3 лежат в `lib/`, поэтому проект не зависит от файлов за
пределами репозитория. Каталоги сборки, загруженные инструменты, IDE-настройки и
сгенерированные отчёты исключены из Git.

Число предупреждений может отличаться от скриншотов готового отчёта, потому что
в проекте закреплена конкретная современная версия SpotBugs. Все типы проблем,
разобранные в отчёте, воспроизводятся.
