package fineGrainedLock;

import baseModel.Node;
import baseModel.SortedLinkedList;

public class FineGrainedLockSLL implements SortedLinkedList {
    public Node head;

    public FineGrainedLockSLL(Node head) {
        this.head = head;
    }

    public boolean add(int x) {
        Node current = head;
        current.lock.lock();
        while (current.next != null) {
            current.next.lock.lock();
            if (x <= current.next.value) {
                break;
            }
            Node temp = current.next;
            current.lock.unlock();
            current = temp;
        }
        current.next = new Node(current.next, x);
        if (current.next.next != null) {
            current.next.next.lock.unlock();
        }
        current.lock.unlock();
        return true;
    }

    public boolean remove(int x) {
        Node pred;
        Node current;
        pred = head;
        pred.lock.lock();
        current = pred.next;
        current.lock.lock();
        while (current.value <= x) {
            if (current.value == x) {
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

    public boolean contains(int x) {
        Node current = head;
        current.lock.lock();
        while (current.next != null) {
            current.next.lock.lock();
            if (current.value == x) {
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

}
