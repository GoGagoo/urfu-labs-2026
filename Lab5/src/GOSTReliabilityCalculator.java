import java.util.Random;
import java.util.ArrayList;
import java.util.List;

public class GOSTReliabilityCalculator {

    // ============================================================
    // 1. ИСХОДНЫЕ ДАННЫЕ (Вариант 3)
    // ============================================================
    private static final double NUM_REFUSE_Q = 7.0;
    private static final double NUM_EXP_N = 1300.0;
    private static final double TV_MIN = 0.6;
    private static final double TV_MAX = 1.0;
    private static final double TV_DOP = 0.65;
    private static final double TP_MIN = 10.0;
    private static final double TP_MAX = 16.0;
    private static final double TP_DOP = 10.0;
    private static final double BASIC_PRINC_REL = 0.90;

    private static final int SAMPLE_SIZE_TV = 100;
    private static final int SAMPLE_SIZE_TP = 200;

    // ============================================================
    // 2. МЕТОДЫ ДЛЯ РАСЧЕТОВ
    // ============================================================

    /**
     * Генерирует список случайных чисел в заданном диапазоне
     */
    public List<Double> generateSample(double min, double max, int size) {
        Random rand = new Random();
        List<Double> sample = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            double value = min + (max - min) * rand.nextDouble();
            sample.add(value);
        }
        return sample;
    }

    /**
     * Вычисляет среднее значение списка
     */
    public double average(List<Double> list) {
        double sum = 0.0;
        for (double v : list) {
            sum += v;
        }
        return sum / list.size();
    }

    /**
     * Основной метод расчета
     */
    public void calculateAndPrint() {

        // Генерация выборок
        List<Double> tvSample = generateSample(TV_MIN, TV_MAX, SAMPLE_SIZE_TV);
        List<Double> tpSample = generateSample(TP_MIN, TP_MAX, SAMPLE_SIZE_TP);

        // H0401: Вероятность безотказной работы: P = 1 - Q/N
        double troubleFreeProbability = 1 - (NUM_REFUSE_Q / NUM_EXP_N);

        // Среднее время восстановления (Тв_ср)
        double avgTimeRecovery = average(tvSample);

        // H0501: Оценка по среднему времени восстановления
        double avgTimeEstimate;
        if (avgTimeRecovery <= TV_DOP) {
            avgTimeEstimate = 1.0;
        } else {
            avgTimeEstimate = TV_DOP / avgTimeRecovery;
        }

        // Средняя фактическая продолжительность преобразования (Тп_ср)
        double avgTimeTransform = average(tpSample);

        // H0502: Оценка по продолжительности преобразования
        List<Double> durationEstimates = new ArrayList<>();
        for (double tp : tpSample) {
            if (tp <= TP_DOP) {
                durationEstimates.add(1.0);
            } else {
                durationEstimates.add(TP_DOP / tp);
            }
        }
        double avgDurationEstimate = average(durationEstimates);

        // Итоговая оценка
        double finalEstimate = (avgTimeEstimate + avgDurationEstimate) / 2.0;

        // Абсолютный показатель критериев
        double absoluteCriteria = (finalEstimate + troubleFreeProbability) / 2.0;

        // Относительный показатель критериев
        double relatedFactor = absoluteCriteria / BASIC_PRINC_REL;

        // Фактор надежности
        double qualityFactor = relatedFactor;

        // ============================================================
        // 3. ВЫВОД РЕЗУЛЬТАТОВ
        // ============================================================
        System.out.println("============================================");
        System.out.println("  РАСЧЕТ ФАКТОРА НАДЕЖНОСТИ ПО ГОСТ 28195-89");
        System.out.println("  Вариант 3");
        System.out.println("============================================");
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
        System.out.println("============================================");
    }
}