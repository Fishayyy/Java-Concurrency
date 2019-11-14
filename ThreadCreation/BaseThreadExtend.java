class Runner extends Thread {
    public void run() {
        System.out.println("Hello");
        try {
        Thread.sleep(100);
        } catch(InterruptedException e) {
            System.out.println("Oops");
        }
        System.out.println("World");
    }
}

public class BaseThreadExtend {
    public static void main(String[] args) {
        Runner runner1 = new Runner();
        Runner runner2 = new Runner();
        Runner runner3 = new Runner();
        runner1.start();
        runner2.start();
        runner3.start();
    }
}