package lockFree;

import java.util.concurrent.atomic.AtomicBoolean;

import baseModel.Node;
import baseModel.SortedLinkedList;

public class LockFreeSLL implements SortedLinkedList {
    public LockFreeNode head;

    // nodes[0] = predecessor, nodes[1] = current, nodes[2] = successor;
    public boolean add(int x) {
        while (true) {
            LockFreeNode[] nodes = find(x);
            if (nodes[1].getReference() != null) {
                return false;
            }
            LockFreeNode entry = new LockFreeNode(new Node(x), false);
            nodes[2].attemptMark(nodes[2].getReference(), false);
            entry.next = nodes[2];
            if (nodes[0].next.compareAndSet(nodes[2].getReference(), entry.getReference(), false, false)) {
                return true;
            }
        }
    }

    // nodes[0] = predecessor, nodes[1] = current, nodes[2] = successor;
    public boolean remove(int x) {
        while (true) {
            LockFreeNode[] nodes = find(x);
            if (nodes[1].getReference() == null) {
                return false;
            }
            if (!nodes[1].next.attemptMark(nodes[2].getReference(), true)) {
                continue;
            }
            nodes[0].next.compareAndSet(nodes[1].getReference(), nodes[2].getReference(), false, false);
            return true;
        }
    }

    // nodes[0] = predecessor, nodes[1] = current, nodes[2] = successor;
    public boolean contains(int x) {
        LockFreeNode[] nodes = find(x);
        return nodes[1].getReference() != null;
    }

    // nodes[0] = predecessor, nodes[1] = current, nodes[2] = successor;
    private LockFreeNode[] find(int x) {
        LockFreeNode[] nodes = new LockFreeNode[3];
        AtomicBoolean flag = new AtomicBoolean(false);
        while (true) {
            flag.compareAndSet(false, true);
            nodes[0] = this.head;
            nodes[1] = head.next.isMarked() ? new LockFreeNode(null, false) : head.next;
            while (flag.get()) {
                if (nodes[1].getReference() == null) {
                    return nodes;
                }
                LockFreeNode temp = nodes[0].next;//FIXME: probably not working
                nodes[2] = nodes[1].next.isMarked() ? new LockFreeNode(null, false) : nodes[1].next;
                if (nodes[0].next != temp) { //FIXME: probably not working, isChanged(nodes[0].next)
                    flag.compareAndSet(true, false);
                    continue;
                }
                if (!nodes[1].isMarked()) {
                    if (nodes[1].getReference().value == x) {
                        return nodes;
                    } else if (nodes[1].getReference().value <= x) {
                        nodes[0] = nodes[1];
                    } else {
                        nodes[1].compareAndSet(nodes[1].getReference(), null, nodes[1].isMarked(), nodes[1].isMarked());
                        return nodes;
                    }
                } else {
                    if (nodes[0].next.compareAndSet(nodes[1].getReference(), nodes[2].getReference(), false, false)) {
                        continue;
                    } else {
                        flag.compareAndSet(true, false);
                        continue;
                    }
                }
            }
        }
    }
}
