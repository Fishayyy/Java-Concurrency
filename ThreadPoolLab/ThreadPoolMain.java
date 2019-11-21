import java.util.Random;

class ThreadA implements Runnable {
    public void run() {
        try {
            Thread.sleep(100);
            System.out.println("Thread Type A done");
        } catch(InterruptedException e) {} 
    }
}

class ThreadB implements Runnable {
    public void run() {
        try {
            Thread.sleep(200);
            System.out.println("Thread Type B done");
        } catch(InterruptedException e) {}
    }
}

class ThreadC implements Runnable {
    public void run() {
        try {
            Thread.sleep(150);
            System.out.println("Thread Type C done");
        } catch(InterruptedException e) {} 
    }
}

public class ThreadPoolMain {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ThreadPool pool = new ThreadPool(5);
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int value = random.nextInt(3);
            try {
                switch(value) {
                    case 0:
                        pool.submit(new ThreadA());
                        break;
                    case 1:
                        pool.submit(new ThreadB());
                        break;
                    case 2:
                        pool.submit(new ThreadC());
                        break; 
                }
            } catch(InterruptedException e) {} 
        }
        pool.shutdown();
        System.out.println("Pool is shutdown");
        try {
            pool.await();
            System.out.println("Pool is finished");
        } catch(InterruptedException e) {}
        long endTime = System.currentTimeMillis();
        long duration = endTime - startTime;
        System.out.println("The total duration was: " + duration);
    } 
}