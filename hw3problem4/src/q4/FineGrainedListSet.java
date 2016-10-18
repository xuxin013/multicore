package q4;

import java.util.concurrent.locks.ReentrantLock;

public class FineGrainedListSet implements ListSet {
    private Node head;

    public FineGrainedListSet() {
        this.head = new Node(Integer.MIN_VALUE);
        head.next = new Node(Integer.MAX_VALUE);
    }

    public boolean add(int value) {
        Node pred, curr;
        head.lock.lock();
        pred = head;
        try {
            curr = pred.next;
            curr.lock.lock();
            try {
                while (curr.value < value) {
                    pred.lock.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock.lock();
                }
                if (curr.value == value) {
                    return false;
                }
                Node node = new Node(value);
                node.isRemoved = false;
                node.next = curr;
                pred.next = node;
                return true;
            } finally {
                curr.lock.unlock();
            }
        } finally {
            pred.lock.unlock();
        }
    }

    public boolean remove(int value) {
        Node pred, curr;
        head.lock.lock();
        pred = head;
        try {
            curr = pred.next;
            curr.lock.lock();
            try {
                while (curr.value < value) {
                    pred.lock.unlock();
                    pred = curr;
                    curr = curr.next;
                    curr.lock.lock();
                }
                if (curr.value == value) {
                    curr.isRemoved = true;
                    pred.next = curr.next;
                    return true;
                }
                return false;
            } finally {
                curr.lock.unlock();
            }
        } finally {
            pred.lock.unlock();
        }
    }

    public boolean contains(int value) {
        Node curr;
        head.lock.lock();
        curr = head;
        try {
            while (curr.value < value) {
                curr.lock.unlock();
                curr = curr.next;
                curr.lock.lock();
            }
            return curr.value == value && !curr.isRemoved;
        } finally {
            curr.lock.unlock();
        }
    }

    private class Node {
        private Integer value;
        private Node next = null;
        private boolean isRemoved = false;
        private ReentrantLock lock = new ReentrantLock();

        private Node(Integer x) {
            value = x;
        }
    }
}
