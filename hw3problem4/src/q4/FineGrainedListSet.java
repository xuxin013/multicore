package q4;

import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedListSet implements ListSet {
    private Node head;

    public FineGrainedListSet() {
        this.head = new Node(0);
    }

    public boolean add(int value) {
        Node current = head;
        current.lock.lock();
        try {
            while (current.next != null) {
                current.next.lock.lock();
                if (value < current.next.value) {
                    break;
                } else if (value == current.next.value) {
                    return false;
                }
                Node temp = current.next;
                current.lock.unlock();
                current = temp;
            }
            current.next = new Node(current.next, value);
            if (current.next.next != null) {
                current.next.next.lock.unlock();
            }
        } finally {
            current.lock.unlock();
        }
        return true;
    }

    public boolean remove(int value) {
        Node pred;
        Node current;
        pred = head;
        pred.lock.lock();
        current = pred.next;
        current.lock.lock();
        while (current.value <= value) {
            if (current.value == value) {
                pred.next = current.next;
                return true;
            }
            pred.lock.unlock();
            pred = current;
            current = current.next;
            current.lock.lock();
        }
        return false;
    }

    public boolean contains(int value) {
        Node current = head;
        current.lock.lock();
        while (current.next != null) {
            current.next.lock.lock();
            if (current.value == value) {
                current.lock.unlock();
                current.next.lock.unlock();
                return true;
            }
            Node temp = current.next;
            current.lock.unlock();
            current = temp;
        }
        return false;
    }

    private class Node {
        private Integer value;
        private  Node next;
        private ReentrantLock lock = new ReentrantLock();

        private Node(Integer x) {
            value = x;
            next = null;
        }

        private Node(Node next, Integer x) {
            this.value = x;
            this.next = next;
        }
    }
}
