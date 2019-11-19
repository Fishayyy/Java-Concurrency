import java.util.Scanner;

public class Processor {
    public void write() throws InterruptedException {
        synchronized(this) {
            System.out.println("Writer thread has started running...");
            wait();
            System.out.println("Writer thread has resumed");
        }
    }

    public void read() throws InterruptedException {
        Scanner scanner = new Scanner(System.in);
        Thread.sleep(2000);
        synchronized(this) {
            System.out.println("Waiting for return key");
            scanner.nextLine();
            System.out.println("Return key pressed");
            notify();
            System.out.println("Reader just called notify");
            System.out.println("Waiting for return key");
            scanner.nextLine();
            System.out.println("Return key pressed again");
        }
    }
}