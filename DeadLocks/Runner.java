import java.util.Random;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Runner {

    Account firstAccount = new Account();
    Account secondAccount = new Account();
    private Lock lock1 = new ReentrantLock();
    private Lock lock2 = new ReentrantLock();

    private void acquireLocks(Lock firstLock, Lock secondLock) throws InterruptedException {
        while (true) {
            boolean gotFirstLock = false;
            boolean gotSecondLock = false;
            try {
                gotFirstLock = firstLock.tryLock();
                gotSecondLock = secondLock.tryLock();
            } finally {
                if (gotFirstLock && gotSecondLock)
                    return;
                if (gotFirstLock)
                    firstLock.unlock();
                if (gotSecondLock)
                    secondLock.unlock();
            }
            Thread.sleep(1);
        }
    }

    public void firstThread() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10000; ++i) {
            int amount = random.nextInt(100);
            acquireLocks(lock1, lock2);
            try {
                Account.transfer(firstAccount, secondAccount, amount);
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void secondThread() throws InterruptedException {
        Random random = new Random();
        for (int i = 0; i < 10000; ++i) {
            int amount = random.nextInt(100);
            acquireLocks(lock1, lock2);
            try {
                Account.transfer(secondAccount, firstAccount, amount);
            } finally {
                lock1.unlock();
                lock2.unlock();
            }
        }
    }

    public void finished() {
        System.out.println("Account 1 balance: " + firstAccount.getBalance());
        System.out.println("Account 2 balance: " + secondAccount.getBalance());
        System.out.println("Total balance: " + (firstAccount.getBalance() + secondAccount.getBalance()));
    }
}