import java.util.concurrent.atomic.AtomicInteger;

public class GoodAtomicCounter {
    private AtomicInteger count = new AtomicInteger(0);

    public void doWork() {
        Thread t1 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++)
                    count.set(4);
            }
        });
        Thread t2 = new Thread(new Runnable() {
            public void run() {
                for (int i = 0; i < 10000; i++)
                    count.set(5);
            }
        });
        t1.start();
        t2.start();
        
        try{
            t1.join();
            t2.join();
        }catch(InterruptedException e)
        {
            System.out.println("Oops");
        }

        System.out.println("Count is: " + count);
    }

    public static void main(String[] args) {
        GoodAtomicCounter good = new GoodAtomicCounter();
        good.doWork();
    }
}