import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
        GOSTReliabilityCalculator calculator = new GOSTReliabilityCalculator();

        // Загрузка данных
        calculator.loadDataFromFile("input.txt");

        // Расчет и сохранение выборок
        calculator.calculateAndPrint();
    }
}