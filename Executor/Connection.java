import java.util.concurrent.Semaphore;

public class Connection {

    private static Connection instance = new Connection();

    private int connections = 0;

    private Semaphore sem = new Semaphore(10);

    private Connection() {

    }

    public static Connection getInstance() {
        return instance;
    }

    public void connect() {
        try {
            sem.acquire();
        } catch(InterruptedException e) {}
        synchronized(this) {
            connections++;
            System.out.printf("Current Connections %d\n", connections);
        }
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {}
        synchronized(this) {
            connections--;
        }
        sem.release();
    }
}