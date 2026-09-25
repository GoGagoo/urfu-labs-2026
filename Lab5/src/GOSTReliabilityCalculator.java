import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GOSTReliabilityCalculator {

    // 1) поля для исходных данных
    private double numRefuseQ;
    private double numExpN;
    private double tvMin, tvMax, tvDop;
    private double tpMin, tpMax, tpDop;
    private double basicPrincRel;
    private int sampleSizeTv;
    private int sampleSizeTp;

    // 2) загрузка данных из файла
    public void loadDataFromFile(String filePath) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(filePath));

        numRefuseQ    = Double.parseDouble(reader.readLine().trim());
        numExpN       = Double.parseDouble(reader.readLine().trim());
        tvMin         = Double.parseDouble(reader.readLine().trim());
        tvMax         = Double.parseDouble(reader.readLine().trim());
        tvDop         = Double.parseDouble(reader.readLine().trim());
        tpMin         = Double.parseDouble(reader.readLine().trim());
        tpMax         = Double.parseDouble(reader.readLine().trim());
        tpDop         = Double.parseDouble(reader.readLine().trim());
        basicPrincRel = Double.parseDouble(reader.readLine().trim());
        sampleSizeTv  = Integer.parseInt(reader.readLine().trim());
        sampleSizeTp  = Integer.parseInt(reader.readLine().trim());

        reader.close();

        System.out.println("Данные успешно загружены из файла: " + filePath);
        System.out.println();
    }

    // 3) генерация выборок
    private List<Double> generateSample(double min, double max, int size) {
        Random rand = new Random();
        List<Double> sample = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            double value = min + (max - min) * rand.nextDouble();
            sample.add(value);
        }
        return sample;
    }

    // вспомогательный метод - среднее по выборке
    private double average(List<Double> list) {
        double sum = 0.0;
        for (double v : list) {
            sum += v;
        }
        return sum / list.size();
    }

    // 4) сохранение выборок в файлы
    private void saveSampleToFile(List<Double> sample, String filePath,
                                  String title,
                                  String interval) throws IOException {
        PrintWriter writer = new PrintWriter(new FileWriter(filePath));

        writer.println("# " + title);
        writer.println("# Размер выборки: " + sample.size());
        writer.println("# Интервал: " + interval);

        // Данные
        for (double value : sample) {
            writer.printf("%.6f%n", value);
        }

        writer.close();
        System.out.println("Выборка сохранена в файл: " + filePath);
    }

    // 5) основной расчет
    public void calculateAndPrint() throws IOException {

        // Генерация выборок
        List<Double> tvSample = generateSample(tvMin, tvMax, sampleSizeTv);
        List<Double> tpSample = generateSample(tpMin, tpMax, sampleSizeTp);

        // Сохранение выборок в файлы
        saveSampleToFile(tvSample, "output_tv_recovery.txt",
                "Выборка времени восстановления (Тв)",
                "[" + tvMin + "; " + tvMax + "]");

        saveSampleToFile(tpSample, "output_tp_transform.txt",
                "Выборка времени преобразования (Тп)",
                 "[" + tpMin + "; " + tpMax + "]");

        System.out.println();

        // H0401: Вероятность безотказной работы
        double troubleFreeProbability = 1 - (numRefuseQ / numExpN);

        // Среднее время восстановления
        double avgTimeRecovery = average(tvSample);

        // H0501: Оценка по среднему времени восстановления
        double avgTimeEstimate;
        if (avgTimeRecovery <= tvDop) {
            avgTimeEstimate = 1.0;
        } else {
            avgTimeEstimate = tvDop / avgTimeRecovery;
        }

        // Средняя фактическая продолжительность преобразования
        double avgTimeTransform = average(tpSample);

        // H0502: Оценка по продолжительности преобразования
        List<Double> durationEstimates = new ArrayList<>();
        for (double tp : tpSample) {
            if (tp <= tpDop) {
                durationEstimates.add(1.0);
            } else {
                durationEstimates.add(tpDop / tp);
            }
        }
        double avgDurationEstimate = average(durationEstimates);

        // Итоговая оценка
        double finalEstimate = (avgTimeEstimate + avgDurationEstimate) / 2.0;

        // Абсолютный показатель
        double absoluteCriteria = (finalEstimate + troubleFreeProbability) / 2.0;

        // Относительный показатель
        double relatedFactor = absoluteCriteria / basicPrincRel;

        // Фактор надежности
        double qualityFactor = relatedFactor;

        // 6) вывод результатов
        System.out.println("Иходные данные");
        System.out.printf("Число отказов (Q):                       %.0f%n", numRefuseQ);
        System.out.printf("Число экспериментов (N):                 %.0f%n", numExpN);
        System.out.printf("Интервал времени восстановления Тв:      [%.2f; %.2f] с%n", tvMin, tvMax);
        System.out.printf("Допустимое время восстановления Тв_доп:  %.2f с%n", tvDop);
        System.out.printf("Интервал времени преобразования Тп:      [%.2f; %.2f] с%n", tpMin, tpMax);
        System.out.printf("Допустимое время преобразования Тп_доп:  %.2f с%n", tpDop);
        System.out.printf("Базовый критерий надежности:             %.2f%n", basicPrincRel);
        System.out.printf("Размер выборки Тв:                       %d%n", sampleSizeTv);
        System.out.printf("Размер выборки Тп:                       %d%n", sampleSizeTp);
        System.out.println();
        System.out.println("============================================");
        System.out.println("Результаты расчета фактора надежности ПО ГОСТ 28195-89");
        System.out.println();
        System.out.printf("Вероятность безотказной работы (P): %.6f%n", troubleFreeProbability);
        System.out.println();
        System.out.printf("Среднее время восстановления (Тв_ср): %.6f%n", avgTimeRecovery);
        System.out.printf("Оценка по среднему времени восстановления (Qв): %.6f%n", avgTimeEstimate);
        System.out.println();
        System.out.printf("Средняя фактическая продолжительность преобразования (Тп_ср): %.6f%n", avgTimeTransform);
        System.out.printf("Средняя оценка по продолжительности преобразования (Qп): %.6f%n", avgDurationEstimate);
        System.out.println();
        System.out.printf("Итоговая оценка по времени восстановления и преобразования: %.6f%n", finalEstimate);
        System.out.printf("Итоговая оценка по вероятности безотказной работы: %.6f%n", troubleFreeProbability);
        System.out.println();
        System.out.printf("Абсолютный показатель критериев: %.6f%n", absoluteCriteria);
        System.out.printf("Относительный показатель критериев: %.6f%n", relatedFactor);
        System.out.println();
        System.out.printf("ФАКТОР НАДЕЖНОСТИ: %.6f%n", qualityFactor);
    }
}