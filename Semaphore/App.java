import java.util.concurrent.Semaphore;

public class App {
    public static void main(String[] args) {
        Connection theConnection = Connection.getInstance();
        theConnection.connect();
    }
}