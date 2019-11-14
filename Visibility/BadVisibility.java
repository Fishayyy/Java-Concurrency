public class BadVisibility {
    private static boolean ready;
    private static int number;

    public static class ReaderThread extends Thread {
        public void run() {
            while(!ready) {
                Thread.yield();
                //Tells the scheduler that the current thread is willing to yield execution and relinquish its
                // current use of the processor, but it would like to put back on the processor as soon as
                // possible.
            }
            System.out.println(number);
        }
    }

    public static void main(String[] args) {
        new ReaderThread().start();
        synchronized(lock) {
            number = 42;
            ready = true;
        }
    }
}
