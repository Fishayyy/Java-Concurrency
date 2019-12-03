import java.util.concurrent.Executors;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.Callable;
import java.util.concurrent.Future;
import java.util.concurrent.ExecutionException;
import java.io.IOException;
import java.util.Random;

public class App {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool();
        // executor.submit(new Runnable() {
        //     public void run() {
        //         Random random = new Random();
        //         int duration = random.nextInt(4000);
        //         System.out.print("Starting");
        //         try {
        //             Thread.sleep(duration/3);
        //             System.out.print(".");
        //             Thread.sleep(duration/3);
        //             System.out.print(".");
        //             Thread.sleep(duration/3);
        //             System.out.println(".");
        //         } catch(InterruptedException e) {}
        //         System.out.println("Finished.");
        //     }
        // });
        Future<Integer> future = executor.submit(new Callable<Integer>(){
            public Integer call() throws Exception {
                Random random = new Random();
                int duration = random.nextInt(4000);
                if(duration > 2000) {
                    throw new IOException("Sleeping for too long");
                }
                System.out.println("Starting...");
                try {
                    Thread.sleep(duration);
                } catch(InterruptedException e) {}
                System.out.println("Finished.");
                return duration;
            }
        });
        executor.shutdown();
        try {
            System.out.println("Result is: " + future.get());
        } catch(InterruptedException e) {

        } catch(ExecutionException e) {
            IOException ex = (IOException) e.getCause();
            System.out.println(ex.getMessage());
        }
    }
}