public class AnonMethod {
    public static void main(String[] args) {
        Thread t1 = new Thread(new Runnable(){
            public void run() {
                System.out.println("Hello");
                try {
                    Thread.sleep(100);
                } catch(InterruptedException e) {
                    System.out.println("Oops");
                }
                System.out.println("World");
            }
        });
        Thread t2 = new Thread(new Runnable(){
            public void run() {
                System.out.println("Potato");
                try {
                    Thread.sleep(100);
                } catch(InterruptedException e) {
                    System.out.println("Oops");
                }
                System.out.println("Goblin");
            }
        });
        Thread t3 = new Thread(new Runnable() { 
            public void run() {
                System.out.println("Batman");
                try {
                    Thread.sleep(100);
                } catch(InterruptedException e) {
                    System.out.println("Oops");
                
                }
                System.out.println("Alfred");
            }
        });
        t1.start();
        t2.start();
        t3.start();
    }
}