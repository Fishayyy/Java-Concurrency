import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.Random;
import java.util.Scanner;

class Order implements Runnable {
    private int order;

    Connection theConnection = Connection.getInstance();

    public Order(int order) {this.order = order;}

    public void run() {
        theConnection.connectPrep(order);
        theConnection.connectCook(order);
        theConnection.connectPlate(order);
    }
}

class Puck implements Runnable {

    private Lock puckManager = new ReentrantLock();
    private Condition puck = puck.newCondition();


}

public class RadishDinerLab {
    public static void main(String[] args) {
        int orderNum = 0;

        Random random = new Random();

        ExecutorService executor = Executors.newFixedThreadPool(4);
        Thread puckSig
        for(int customers = 10; customers > 0; customers--) {
            try {
                Thread.sleep(random.nextInt(1001));
            }catch(InterruptedException e) {}
            System.out.printf("Order %d recieved at %d\n", orderNum, System.currentTimeMillis());
            System.out.printf("Puck for order number %d has been given to a customer\n", orderNum);
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
