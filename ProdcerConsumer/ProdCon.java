import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Random;

public class ProdCon {
    private static BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(10);
    // Creates a blocking queue that holds up to 10 integers.

    private static void producer() throws InterruptedException {
        Random random = new Random();
        while(true) {
            queue.put(random.nextInt(100));
        }
    }

    private static void consumer() throws InterruptedException {
        while(true) {
            Integer value = queue.take(); // Throws the exception
            Thread.sleep(value);
            System.out.println("Processed: " + value + " Queue size: " + queue.size());
        }
    }

    public static void main(String[] args) {
        Thread producingThread = new Thread(new Runnable() {
            public void run() {
                try {
                    producer();
                } catch(InterruptedException e) {}
            }
        });
        Thread consumingThread = new Thread(new Runnable() {
            public void run() {
                try {
                    consumer();
                } catch(InterruptedException e) {}
            }
        });
        producingThread.start();
        consumingThread.start();
        try {
            producingThread.join();
        } catch (InterruptedException e) {}
    }
}