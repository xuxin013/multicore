package q4;

import java.util.concurrent.locks.ReentrantLock;

public class CoarseGrainedListSet implements ListSet {
    private Node head;
    private ReentrantLock lock;

    public CoarseGrainedListSet() {
        head = new Node(0);
        lock = new ReentrantLock();
    }

    public boolean add(int value) {
        lock.lock();
        Node current = head;
        while (current.next != null) {
            if (value < current.next.value) {
                break;
            } else if (value == current.next.value) {
                return false;
            }
            current = current.next;
        }
        Node temp = new Node(value);
        temp.next = current.next;
        current.next = temp;
        lock.unlock();
        return true;
    }

    public boolean remove(int value) {
        lock.lock();
        Node pred;
        Node current;
        pred = head;
        current = head.next;
        while (current.value <= value) {
            if (current.value == value) {
                pred.next = current.next;
                lock.unlock();
                return true;
            }
            pred = current;
            current = current.next;
        }
        lock.unlock();
        return false;
    }

    public boolean contains(int value) {
        lock.lock();
        Node current = head;
        while (current.next != null) {
            if (current.value == value) {
                lock.unlock();
                return true;
            }
            current = current.next;
        }
        lock.unlock();
        return false;
    }

    private class Node {
        private Integer value;
        private Node next;

        private Node(Integer x) {
            value = x;
            next = null;
        }
    }
}
