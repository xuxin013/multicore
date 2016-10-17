package q4;

import java.util.concurrent.atomic.AtomicMarkableReference;

public class LockFreeListSet implements ListSet {
    private AtomicMarkableReference<Node> head;

    public LockFreeListSet() {
        this.head = new AtomicMarkableReference<>(new Node(0), false);
    }

    public boolean add(int value) {
        while(true) {
            Node[] nodes = find(value);
            if (nodes[1] == null) {
                return false;
            }
            Node node = new Node(value);
            node.next = new AtomicMarkableReference<>(nodes[2], false);
            if (nodes[0].next.compareAndSet(nodes[2], node, false, false)) {
                return true;
            }
        }
    }

    public boolean remove(int value) {
        return false;
    }

    public boolean contains(int value) {
        Node[] nodes = find(value);
        return (nodes[1] != null);
    }

    private Node[] find(int x) {
        Node[] nodes = new Node[3];
        boolean[] predMark = new boolean[1];
        boolean[] currMark = new boolean[1];
        retry:
        while (true) {
            nodes[0] = this.head.getReference();
            nodes[1] = nodes[0].next.get(predMark);
            while (true) {
                if (nodes[1] == null) {
                    return nodes;
                }
                nodes[2] = nodes[1].next.get(currMark);
                if (nodes[0].next.getReference() != nodes[1]) { //FIXME:isChanged(nodes[0].next)
                    continue retry;
                }
                if (!currMark[0]) {
                    if (nodes[1].value == x) {
                        return nodes;
                    } else if (nodes[1].value < x) {
                        nodes[0] = nodes[1];
                    } else {
                        nodes[1] = null;
                        return nodes;
                    }
                } else {
                    if (!nodes[0].next.compareAndSet(nodes[1], nodes[2], false, false)) {
                        continue retry;
                    }
                }
            }
        }

    }

    private class Node {
        private Integer value;
        private AtomicMarkableReference<Node> next;

        private Node(Integer x) {
            value = x;
            this.next = null;
        }
    }
}
