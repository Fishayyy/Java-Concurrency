class Account {
    private int balance = 10000;

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        balance -= amount;
    }

    public int getBalance() {
        return balance;
    }

    public static void transfer(Account withdrawn, Account deposited, int amount) {
        withdrawn.withdraw(amount);
        deposited.deposit(amount);
    }
}