import java.util.concurrent.CountDownLatch;

class ProcessorTypeA implements Runnable {
    private int id;
    private CountDownLatch latch;

    public ProcessorTypeA(int id, CountDownLatch latch) {
        this.id = id;
        this.latch = latch;
    }

    public void run() {
        System.out.println("TypeA " + id + " is working on A");
        try {
            Thread.sleep(3000);
            System.out.println("TypeA " + id + " is finished on A");
            latch.countDown();
            System.out.println("TypeA " + id + " is finished on D");
            Thread.sleep(1000);
            System.out.println("TypeA " + id + " has finished");
        } catch(InterruptedException e) {}
    }
}

class ProcessorTypeB implements Runnable {
    private CountDownLatch latch;
    public ProcessorTypeB(CountDownLatch latch) {
        this.latch = latch;
    }

    public void run() {
        System.out.println("Type B is starting B");
        try {
            Thread.sleep(1000);
            System.out.println("Type B has finished B");
            latch.await();
            System.out.println("Type B is starting C, which rquires A to be done");
            Thread.sleep(1000);
            System.out.println("Type B has finished C");
        } catch(InterruptedException e) {}
    }
}

public class CountApp {
    public static void main(String[] args) {
        CountDownLatch latch = new CountDownLatch();
        Thread t1 = new Thread( new ProcessorTypeA(1, latch));
        Thread t2 = new Thread( new ProcessorTypeA(2, latch));
        Thread t3 = new Thread( new ProcessorTypeB(latch));
        t1.start();
        t2.start();
        t3.start();
        try {
            t1.join();
            t2.join();
            t3.join();
            System.out.println("All three threads have finished");
        } catch(InterruptedException e) {}
    }
}