package queue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.atomic.AtomicStampedReference;



public class LockFreeQueue implements MyQueue {
// you are free to add members
	AtomicStampedReference<Node> head;
	AtomicStampedReference<Node> tail;
	AtomicInteger count;
	
  public LockFreeQueue() {
	  Node dummy = new Node(null,null);
	  head = new AtomicStampedReference<Node>(dummy, 0);
	  tail = new AtomicStampedReference<Node>(dummy, 0);
	  count = new AtomicInteger(0);
}

  public boolean enq(Integer value) {
	  Node newNode = new Node(value,null);
	  while(true){
		  Node tmpTail = tail.getReference();
		  int tailCount = tail.getStamp();
		  Node tmpNext = tmpTail.next.get();
		  if(tmpTail == tail.getReference()){
			  if(tmpNext == null){
				  if(tmpTail.next.compareAndSet(null, newNode)){
					  tail.compareAndSet(tmpTail, newNode, tailCount, tailCount+1);
					  count.getAndIncrement();
					  return true;
				  }
			  }
			  else{
				  tail.compareAndSet(tmpTail, tmpNext, tailCount, tailCount+1);
				  }
			  }
		  }
	  }
  
  public Integer deq() {
	// implement your deq method here
  //  return null;
    while(true){
    	Node tmpHead = head.getReference();
       	int headCount = head.getStamp();
    	Node tmpTail = tail.getReference();
    	int tailCount = tail.getStamp();
    	Node next = tmpHead.next.get();
    	if(tmpHead == head.getReference()){
    		if(tmpHead == tmpTail){
    			while(next == null){ Thread.yield();}
    		tail.compareAndSet(tmpTail, next, tailCount, tailCount+1);
    		}
    		else{
    			Integer val = next.value;
    			if(head.compareAndSet(tmpHead, next, headCount, headCount+1));
    			count.getAndDecrement();
    			return val;
    		}
    	}
    }
  }

  protected class Node {
	  public Integer value;
	  public AtomicReference<Node> next;
		    
	  public Node(Integer x, Node next) {
		  this.value = x;
		  this.next = new AtomicReference<Node>(next) ;
	  }
  }
}
