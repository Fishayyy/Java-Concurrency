import java.util.Random;

class WorkerA implements Runnable {
    public void run() {
        try {
            Thread.sleep(100);
            System.out.println("Worker Type A done");
        } catch(InterruptedException e) {} 
    }
}

class WorkerB implements Runnable {
    public void run() {
        try {
            Thread.sleep(200);
            System.out.println("Worker Type B done");
        } catch(InterruptedException e) {}
    }
}

class WorkerC implements Runnable {
    public void run() {
        try {
            Thread.sleep(150);
            System.out.println("Worker Type C done");
        } catch(InterruptedException e) {} 
    }
}

public class BlockingStackMain {
    public static void main(String[] args) {
        long startTime = System.currentTimeMillis();
        ThreadPoolPart1 pool = new ThreadPoolPart1(5);
        Random random = new Random();

        for (int i = 0; i < 100; i++) {
            int value = random.nextInt(3);
            try {
                switch(value) {
                    case 0:
                        pool.submit(new WorkerA());
                        break;
                    case 1:
                        pool.submit(new WorkerB());
                        break;
                    case 2:
                        pool.submit(new WorkerC());
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