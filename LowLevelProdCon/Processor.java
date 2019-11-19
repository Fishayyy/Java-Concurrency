import java.util.LinkedList;

public class Processor {
    private LinkedList<Integer> list = new LinkedList<Intger>();
    private final int LIMIT = 10;

    public void produe() throws InterruptedException {
        int value = 0;
        while (true) {
            synchronized(lock) {
                while(list.size() >= LIMIT) {
                    lock.wait();
                }
                list.add(value);
                lock.notify();
            }
            value++;
        }
    }

    public void consume() throws InterruptedException {
        while (true) {
            synchronized(lock) {
                while(list.size() == 0) {
                    lock.wait();
                }
                int value = list.removeFirst();
                lock.notify();
                System.out.println("Removed: " + value + " Size: " + list.size());
            }
            Thread.sleep(100);
        }
    }
}