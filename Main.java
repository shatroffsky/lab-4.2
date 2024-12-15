import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ThreadLocalRandom;

public class Main {
    public static void main(String[] args) {
        // Записуємо час початку виконання програми
        long startTime = System.currentTimeMillis();

        // Генерація послідовності чисел асинхронно
        CompletableFuture<List<Double>> generateSequence = CompletableFuture.supplyAsync(() -> {
            List<Double> sequence = new ArrayList<>();
            // Генеруємо 20 випадкових чисел у діапазоні [1.0, 100.0]
            for (int i = 0; i < 20; i++) {
                sequence.add(ThreadLocalRandom.current().nextDouble(1.0, 100.0));
            }
            // Виводимо згенеровану послідовність
            System.out.println("Generated sequence: " + sequence);
            return sequence;
        });

        // Обчислення добутку різниць між сусідніми елементами послідовності
        CompletableFuture<Double> calculateProduct = generateSequence.thenApplyAsync(sequence -> {
            double product = 1.0;
            // Розраховуємо (a2 - a1) * (a3 - a2) * ... * (an - an-1)
            for (int i = 1; i < sequence.size(); i++) {
                product *= (sequence.get(i) - sequence.get(i - 1));
            }
            return product;
        });

        // Вивід результату обчислень асинхронно
        calculateProduct.thenAcceptAsync(result -> {
                    // Виводимо фінальний результат обчислень
                    System.out.println("Result of (a2 - a1) * (a3 - a2) * ... * (an - an-1): " + result);
                })
                // Виконуємо фінальну задачу для виводу загального часу виконання
                .thenRunAsync(() -> {
                    // Записуємо час завершення виконання програми
                    long endTime = System.currentTimeMillis();
                    // Обчислюємо і виводимо загальний час виконання
                    System.out.println("Total execution time: " + (endTime - startTime) + " ms");
                });

        // Очікуємо завершення всіх задач, щоб програма не завершилася передчасно
        try {
            Thread.sleep(2000); // 2 секунди очікування для демонстрації
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
