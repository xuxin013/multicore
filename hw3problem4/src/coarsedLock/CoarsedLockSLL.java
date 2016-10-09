package coarsedLock;

import java.util.concurrent.locks.ReentrantLock;

import baseModel.Node;
import baseModel.SortedLinkedList;

public class CoarsedLockSLL implements SortedLinkedList {
    public Node head;
    public ReentrantLock lock;

    public CoarsedLockSLL(Node init) {
        head = init;
        lock = new ReentrantLock();
    }

    public boolean add(int x) {
        lock.lock();
        Node current = head;
        while (current.next != null) {
            if (x <= current.next.value) {
                break;
            }
            current = current.next;
        }
        current.next = new Node(current.next, x);
        lock.unlock();
        return true;
    }

    public boolean remove(int x) {
        lock.lock();
        Node pred;
        Node current;
        pred = head;
        current = head.next;
        while (current.value <= x) {
            if (current.value == x) {
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

    public boolean contains(int x) {
        lock.lock();
        Node current = head;
        while (current.next != null) {
            if (current.value == x) {
                lock.unlock();
                return true;
            }
            current = current.next;
        }
        lock.unlock();
        return false;
    }

}
