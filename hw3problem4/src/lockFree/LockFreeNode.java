package lockFree;

import java.util.concurrent.atomic.AtomicMarkableReference;

import baseModel.Node;

public class LockFreeNode extends AtomicMarkableReference<Node> {
    public LockFreeNode next;

    public LockFreeNode(Node initialRef, boolean initialMark) {
        super(initialRef, initialMark);
        this.next = null;
    }

}
