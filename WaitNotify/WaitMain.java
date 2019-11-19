public class WaitMain {
    public static void main(String[] args) {
        final Processor processor = new Processor();
        Thread reader = new Thread(new Runnable() {
            public void run() {
                try { 
                    processor.read();
                } catch(InterruptedException e) {}
            }
        });
        Thread writer = new Thread(new Runnable() {
            public void run() {
                try { 
                    processor.write();
                } catch(InterruptedException e) {}
            }
        });
        reader.start();
        writer.start();
        try {
            reader.join();
            writer.join();
        } catch(InterruptedException e) {}
    }
}