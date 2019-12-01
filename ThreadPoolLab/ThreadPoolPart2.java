import java.util.List;
import java.util.LinkedList;

public class ThreadPoolPart2 {
    private List<Thread> workers;
    private BlockingStack<Runnable> tasks;
    private boolean isShutdown;

    public ThreadPoolPart2 (int numberThreads) {
        this.workers = new LinkedList<>();
        this.tasks = new BlockingStack<Runnable>(10);
        this.isShutdown = false;

        for (int i = 0; i < numberThreads; ++i) {
            Thread worker = new Thread(new Runnable(){
                public void run() {
                    try {
                        while(!tasks.isEmpty() || !isShutdown)
                            tasks.pop().run();
                    } catch(InterruptedException e) {}
                }
            });

            worker.start();
            this.workers.add(worker);
        }
    }

    public void submit(Runnable task) throws InterruptedException { 
        if(!this.isShutdown)
            this.tasks.push(task);
    }

    public void shutdown() { this.isShutdown = true; }

    public void await() throws InterruptedException { 
        for(Thread worker : this.workers)
            worker.join();
    }
}