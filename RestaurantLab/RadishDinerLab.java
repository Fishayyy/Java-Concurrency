import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.Semaphore;
import java.util.Random;

class Puck implements Runnable{

    int orderNum;
    Lock lock;
    Condition cond;

    public Puck(int orderNum, Lock lock, Condition cond) { 
        this.orderNum = orderNum; 
        this.lock = lock;
        this.cond = cond;
    }

    public void run(){
        try{
            System.out.printf("Puck for order number %d has been given to a customer\n", orderNum);
            lock.lock();
            cond.await();
            System.out.printf("Sending the signal to puck for Order %d\n", orderNum);
            System.out.printf("Puck for order number %d has started ringing\n", orderNum);
        } catch(InterruptedException e) { }
        finally{
            lock.unlock();
        }
    }
}

class Order implements Runnable {

    private int orderNum;

    private final int prepTime = 1500;
    private final int cookTime = 2000;
    private final int plateTime = 1000;


    private static Semaphore prep = new Semaphore(2,true);
    private static Semaphore stove = new Semaphore(2,true);
    private static Semaphore plate = new Semaphore(2,true);
    
    private Lock lock;
    private Condition cond;

    public Order(int orderNum, Lock lock, Condition cond) { 
        this.orderNum = orderNum; 
        this.lock = lock;
        this.cond = cond;
    }

    public void run() {
            prep();
            cook();
            plate();
            lock.lock();
            cond.signal();
            lock.unlock();
    }

    public void prep() {
        try {
            prep.acquire();
            System.out.printf("Order %d is starting to prep at %d\n", orderNum, System.currentTimeMillis());
            Thread.sleep(prepTime);
            System.out.printf("Order %d is finishing preping at %d\n", orderNum, System.currentTimeMillis());
        } catch(InterruptedException e) { } 
        finally {
            prep.release();
        }
    }

    public void cook() {
        try {
            stove.acquire();
            System.out.printf("Order %d is starting to cook at %d\n", orderNum, System.currentTimeMillis());
            Thread.sleep(cookTime);
            System.out.printf("Order %d is finishing cooking at %d\n", orderNum, System.currentTimeMillis());
        } catch(InterruptedException e) { } 
        finally {
            stove.release();
        }
    }

    public void plate() {
        try {    
            plate.acquire();
            System.out.printf("Order %d is starting to plate at %d\n", orderNum, System.currentTimeMillis());
            Thread.sleep(plateTime);
            System.out.printf("Order %d is finishing plating at %d\nOrder up!\n", orderNum, System.currentTimeMillis());
        } catch(InterruptedException e) { } 
        finally {
            plate.release();
        }
    }
}

public class RadishDinerLab {
    public static void main(String[] args) {
        int orderNum = 0;
        int waitInterval = 1001;
        Random random = new Random();
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for(int customers = 10; customers > 0; customers--) {
            try {
                Thread.sleep(random.nextInt(waitInterval));
            }catch(InterruptedException e) {}
            Lock lock = new ReentrantLock();
            Condition cond = lock.newCondition();
            Thread puck = new Thread(new Puck(orderNum, lock, cond));
            System.out.printf("Order %d recieved at %d\n", orderNum, System.currentTimeMillis());
            puck.start();
            executor.submit(new Order(orderNum++, lock, cond));
        }
        executor.shutdown();
        System.out.printf("All orders recieved\n");
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch(InterruptedException e) {}
        System.out.printf("All orders completed.\n");
    }
}
