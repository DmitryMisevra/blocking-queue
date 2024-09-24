import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class Main {
    public static void main(String[] args) {
        BlockingQueue<Integer> blockingQueue = new BlockingQueue<>(5);

        // Создаем пул из 2 потоков
        try (ExecutorService executorService = Executors.newFixedThreadPool(2)) {

            // Продюсер
            Runnable producer = () -> {
                for (int i = 1; i <= 10; i++) {
                    try {
                        System.out.println("Производитель добавляет: " + i);
                        blockingQueue.enqueue(i);
                        System.out.println("Текущий размер очереди после добавления: " + blockingQueue.getSize());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };

            // Консюмер
            Runnable consumer = () -> {
                for (int i = 1; i <= 10; i++) {
                    try {
                        Integer value = blockingQueue.dequeue();
                        System.out.println("Потребитель извлекает: " + value);
                        System.out.println("Текущий размер очереди после извлечения: " + blockingQueue.getSize());
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    }
                }
            };

            // Запускаем
            executorService.execute(producer);
            executorService.execute(consumer);

            // Завершаем
            executorService.shutdown();
        }
    }
}
