import java.util.LinkedList;
import java.util.Queue;

public class BlockingQueue<T> {
    private int maxSize; // ограничили размер
    private Queue<T> queue;

    public BlockingQueue(int maxSize) { // в констукторе можем задать размер очереди
        this.maxSize = maxSize;
        this.queue = new LinkedList<>();
    }

    public synchronized void enqueue(T value) throws InterruptedException {
        while (queue.size() == maxSize) {
            wait(); // если очередь полная, блокируем поток
        }
        queue.add(value);
        notifyAll();
    }

    public synchronized T dequeue() throws InterruptedException {
        while (queue.isEmpty()) {
            wait(); // если очередь пуста, поток блокируется
        }
        T value = queue.poll();
        notifyAll();
        return value;
    }

    public synchronized int getSize() {
        return queue.size();
    }
}
