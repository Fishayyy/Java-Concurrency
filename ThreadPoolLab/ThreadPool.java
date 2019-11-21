import java.util.List;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ArrayBlockingQueue;

public class ThreadPool {
    private List<Thread> workers;
    private BlockingQueue<Runnable> tasks;
    private boolean isShutdown;

    public ThreadPool (int numberThreads) {
        this.workers = new LinkedList<>();
        this.tasks = new ArrayBlockingQueue<>(10000);
        this.isShutdown = false;

        for (int i = 0; i < numberThreads; ++i) {
            Thread worker = new Thread(new Runnable(){
                public void run() {
                    try {
                        while(!tasks.isEmpty() || !isShutdown)
                            tasks.take().run();
                    } catch(InterruptedException e) {}
                }
            });

            worker.start();
            this.workers.add(worker);
        }
    }

    public void submit(Runnable task) throws InterruptedException { 
        if(!this.isShutdown)
            this.tasks.put(task);
    }

    public void shutdown() { this.isShutdown = true; }

    public void await() throws InterruptedException { 
        for(Thread worker : this.workers)
            worker.join();
    }
}