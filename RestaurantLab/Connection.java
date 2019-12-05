import java.util.concurrent.TimeUnit;
import java.util.concurrent.Semaphore;

public class Connection {

    private static Connection instance = new Connection();

    private Semaphore prep = new Semaphore(2,true);
    private Semaphore stove = new Semaphore(2,true);
    private Semaphore plate = new Semaphore(2,true);

    private Connection() { }

    public static Connection getInstance() { return instance; }

    public void connectPrep(int order) {
        try {
            prep.acquire();
            System.out.printf("Order %d is starting to prep at %d\n", order, System.currentTimeMillis());
            Thread.sleep(1500);
            System.out.printf("Order %d is finishing preping at %d\n", order, System.currentTimeMillis());
        } catch(InterruptedException e) { } 
        finally {
            prep.release();
        }
    }

    public void connectCook(int order) {
        try {
            stove.acquire();
            System.out.printf("Order %d is starting to cook at %d\n", order, System.currentTimeMillis());
            Thread.sleep(2000);
            System.out.printf("Order %d is finishing cooking at %d\n", order, System.currentTimeMillis());
        } catch(InterruptedException e) { } 
        finally {
            stove.release();
        }
    }

    public void connectPlate(int order) {
        try {
            plate.acquire();
            System.out.printf("Order %d is starting to plate at %d\n", order, System.currentTimeMillis());
            Thread.sleep(1000);
            System.out.printf("Order %d is finishing plating at %d\nOrder up!\n", order, System.currentTimeMillis());
        } catch(InterruptedException e) { } 
        finally {
            plate.release();
        }
    }
}