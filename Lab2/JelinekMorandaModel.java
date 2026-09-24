import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class JelinekMorandaModel {
    public static void main(String[] args) {
        // Исходные данные читаем из файла
        String fileName = "C:\\Users\\User\\AppData\\Local\\Temp\\vscodesws_d605a\\jdt_ws\\jdt.ls-java-project\\bin\\\\data.txt";
        double[] Xi = readDataFromFile(fileName);

        int n = Xi.length;

        // Вычисляем суммы
        double sumX = 0;
        double sumIX = 0;
        for (int i = 0; i < n; i++) {
            sumX += Xi[i];
            sumIX += (i + 1) * Xi[i];
        }

        System.out.println("=== Исходные данные ===");
        System.out.println("n = " + n);
        System.out.println("Сумма всех интервалов времени ΣXi = " + sumX);
        System.out.println("Взвешенная сумма интервалов Σ(i·Xi) = " + sumIX);
        System.out.println();

        // 1. Находим B
        double B = findB(n, sumX, sumIX);
        System.out.println("=== Результаты ===");
        System.out.println("Общее число ошибок B = " + B);

        // 2. Находим K
        double K = findK(n, B, sumX, sumIX);
        System.out.println("Коэффициент пропорциональности K = " + K);

        // 3. Находим X_{n+1}
        double X_next = findXNext(K, B, n);
        System.out.println("Время до следующей ошибки X_{" + (n + 1) + "} = " + X_next + " часов");

        // 4. Находим t_k
        double tk = findTk(K, B, n);
        System.out.println("Время до окончания тестирования t_k = " + tk + " часов");
    }

    // Чтение данных из файла
    public static double[] readDataFromFile(String fileName) {
        try {
            String content = Files.readString(Paths.get(fileName));
            String[] tokens = content.trim().split("[\\s,]+");
            List<Double> values = new ArrayList<>();
            for (String token : tokens) {
                if (!token.isEmpty()) {
                    values.add(Double.parseDouble(token));
                }
            }
            double[] result = new double[values.size()];
            for (int i = 0; i < values.size(); i++) {
                result[i] = values.get(i);
            }
            return result;
        } catch (IOException e) {
            throw new RuntimeException("Не удалось прочитать файл с данными: " + fileName, e);
        }
    }

    public static double findB(int n, double sumX, double sumIX) {
        double left = n + 0.1;
        double right = 100;
        double epsilon = 0.0001;
        int maxIterations = 1000;
        double fLeft = function(left, n, sumX, sumIX);
        double fRight = function(right, n, sumX, sumIX);

        if (fLeft * fRight > 0) {
            while (fLeft * fRight > 0 && right < 1000) {
                right *= 2;
                fRight = function(right, n, sumX, sumIX);
            }
        }

        double mid = 0;
        for (int i = 0; i < maxIterations; i++) {
            mid = (left + right) / 2;
            double fMid = function(mid, n, sumX, sumIX);

            if (Math.abs(fMid) < epsilon) {
                break;
            }
            if (fLeft * fMid < 0) {
                right = mid;
                fRight = fMid;
            } else {
                left = mid;
                fLeft = fMid;
            }
        }
        return mid;
    }

    public static double function(double B, int n, double sumX, double sumIX) {
        double leftPart = 0;
        for (int i = 1; i <= n; i++) {
            leftPart += 1.0 / (B - i + 1);
        }
        double denominator = (B + 1) * sumX - sumIX;
        double rightPart = (n * sumX) / denominator;
        return leftPart - rightPart;
    }

    public static double findK(int n, double B, double sumX, double sumIX) {
        double denominator = (B + 1) * sumX - sumIX;
        return n / denominator;
    }

    public static double findXNext(double K, double B, int n) {
        return 1.0 / (K * (B - n));
    }

    public static double findTk(double K, double B, int n) {
        int m = (int) Math.round(B - n);
        if (m < 0) m = 0;
        double sum = m * (m + 1) / 2.0;
        return sum / K;
    }
}