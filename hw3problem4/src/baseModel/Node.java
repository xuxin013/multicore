package baseModel;

import java.util.concurrent.locks.ReentrantLock;

public class Node {
    public int value;
    public Node next;
    public ReentrantLock lock;

    public Node(int value) {
        this.value = value;
        this.next = null;
        this.lock = null;
    }
    public Node(Node next, int value) {
        this.value = value;
        this.next = next;
        this.lock = new ReentrantLock();
    }
}
