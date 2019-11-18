import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.Scanner;

class Order implements Runnable {
    private int order;
    private static Object prepLock = new Object();
    private static Object cookLock = new Object();
    private static Object plateLock = new Object();

    public Order(int order) {this.order = order;}

    public void prep() {
        synchronized(prepLock) {
            try {
                System.out.printf("Order %d is starting to prep at %d\n", this.order, System.currentTimeMillis());
                Thread.sleep(1500);
                System.out.printf("Order %d is finishing preping at %d\n", this.order, System.currentTimeMillis());
            } catch(InterruptedException e) {}
        }
    }

    public void cook() {
        synchronized(cookLock) {
            try {
                System.out.printf("Order %d is starting to cook at %d\n", this.order, System.currentTimeMillis());
                Thread.sleep(2000);
                System.out.printf("Order %d is finishing cooking at %d\n", this.order, System.currentTimeMillis());
            } catch(InterruptedException e) {}
        }
    }

    public void plate() {
        synchronized(plateLock) {
            try {
                System.out.printf("Order %d is starting to plate at %d\n", this.order, System.currentTimeMillis());
                Thread.sleep(1000);
                System.out.printf("Order %d is finishing plating at %d\nOrder up!\n", this.order, System.currentTimeMillis());
            } catch(InterruptedException e) {}
        }
    }

    public void run() {
        prep();
        cook();
        plate();
    }
}

public class FoodTruckApp {
    public static void main(String[] args) {
        int orderNum = 0;

        ExecutorService executor = Executors.newFixedThreadPool(3);
        Scanner input = new Scanner(System.in);
        while(input.hasNextInt()) {
            try {
                Thread.sleep(input.nextInt() * 1000);
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
