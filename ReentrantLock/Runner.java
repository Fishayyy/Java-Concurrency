import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.Scanner;

public class Runner {

    private int count = 0;
    private Lock lock = new ReentrantLock();
    private Condition condition = lock.newCondition();

    private void increment() {
        for (int i = 0; i < 10000; ++i)
            count++;
    }

    public void firstThread() throws InterruptedException {
        lock.lock();
        System.out.println("First thread is starting to wait...");
        condition.await();
        System.out.println("First thread has woken up!");
        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void secondThread() throws InterruptedException {
        Thread.sleep(1000);
        lock.lock();
        Scanner scanner = new Scanner(System.in);
        System.out.println("waiting for enter");
        scanner.nextLine();
        System.out.println("Got enter key!");
        condition.signal();
        try {
            increment();
        } finally {
            lock.unlock();
        }
    }

    public void finished() {
        System.out.printf("Count is: %d\n", count);
    }
}