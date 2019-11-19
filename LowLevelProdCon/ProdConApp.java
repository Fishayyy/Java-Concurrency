public class ProdConApp {
    public static void main(String[] args) {
        final Processor processor = new Processor();
        Thread producer = new Thread(new Runnable() {
            public void run() {
                try { 
                    processor.produce();
                } catch(InterruptedException e) {}
            }
        });
        Thread consumer = new Thread(new Runnable() {
            public void run() {
                try { 
                    processor.consume();
                } catch(InterruptedException e) {}
            }
        });
     producer.start();
     consumer.start();
        try {
         producer.join();
         consumer.join();
        } catch(InterruptedException e) {}
    }
}