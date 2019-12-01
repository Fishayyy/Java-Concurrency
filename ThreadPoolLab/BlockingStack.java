import java.util.List;
import java.util.LinkedList;

public class BlockingStack<T> {
    private int capacity;
    private List<T> stack;

    public BlockingStack(int capacity) {
        assert capacity > 0 : "Capacity must be above 0."; 
        this.capacity = capacity;
        this.stack = new LinkedList<T>();
    }

    public T peek() {
        return this.stack.get(0);
    }

    public int size() {
        return this.stack.size();
    }

    public boolean isEmpty() {
        return this.stack.isEmpty();
    }

    public boolean isFull() {
        return this.stack.size() >= this.capacity;
    }

    public synchronized void push(T entry) throws InterruptedException {
        while(isFull())
            wait();
        
        this.stack.add(0, entry);
        notify();
    }

    public synchronized T pop() throws InterruptedException{
        while(isEmpty()) 
            wait();

        notify();
        return this.stack.remove(0);
    }
}


