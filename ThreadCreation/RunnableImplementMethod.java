class MyRunnable implements Runnable {
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

public class RunnableImplementMethod {
    public static void main(String[] args) {
        Thread t1 = new Thread(new MyRunnable());
        Thread t2 = new Thread(new MyRunnable());
        Thread t3 = new Thread(new MyRunnable());
        t1.start();
        t2.start();
        t3.start();
    }
}