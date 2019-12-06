import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.Semaphore;
import java.util.Random;

class Order implements Runnable {
    private int orderNum;
    private static Semaphore prep = new Semaphore(2,true);
    private static Semaphore stove = new Semaphore(2,true);
    private static Semaphore plate = new Semaphore(2,true);

    public Order(int orderNum) { this.orderNum = orderNum; }

    public void run() {
        //Prep
        try {
            prep.acquire();
            System.out.printf("Order %d is starting to prep at %d\n", orderNum, System.currentTimeMillis());
            Thread.sleep(1500);
            System.out.printf("Order %d is finishing preping at %d\n", orderNum, System.currentTimeMillis());
        } catch(InterruptedException e) { } 
        finally {
            prep.release();
        }
        //Cook
        try {
            stove.acquire();
            System.out.printf("Order %d is starting to cook at %d\n", orderNum, System.currentTimeMillis());
            Thread.sleep(2000);
            System.out.printf("Order %d is finishing cooking at %d\n", orderNum, System.currentTimeMillis());
        } catch(InterruptedException e) { } 
        finally {
            stove.release();
        }
        //Plate
        try {
            plate.acquire();
            System.out.printf("Order %d is starting to plate at %d\n", orderNum, System.currentTimeMillis());
            Thread.sleep(1000);
            System.out.printf("Order %d is finishing plating at %d\nOrder up!\n", orderNum, System.currentTimeMillis());
        } catch(InterruptedException e) { } 
        finally {
            plate.release();
        }
    }
}

// class Puck {

//     int orderNum;

//     public Puck(int orderNum) { this.orderNum = orderNum; }

//     public void {
//         try{
//             lock.lock();
//             System.out.printf("Puck for order number %d has been given to a customer\n", orderNum);
//             condition.await();
//             System.out.printf("Sending the signal to puck for Order %d", orderNum);
//             System.out.printf("Puck for order number %d has started ringing", orderNum);
//         } catch(InterruptedException e) { }
//         finally{
//             lock.unlock();
//         }
//     }
// }

public class RadishDinerLab {
    public static void main(String[] args) {
        int orderNum = 0;
        Random random = new Random();
        ExecutorService executor = Executors.newFixedThreadPool(4);

        for(int customers = 10; customers > 0; customers--) {
            try {
                Thread.sleep(random.nextInt(1001));
            }catch(InterruptedException e) {}
            System.out.printf("Order %d recieved at %d\n", orderNum, System.currentTimeMillis());
            executor.submit(new Order(orderNum++));
        }
        executor.shutdown();
        System.out.printf("All orders recieved\n");
        try {
            executor.awaitTermination(1, TimeUnit.DAYS);
        } catch(InterruptedException e) {}
        System.out.printf("All orders completed.\n");
    }
}
