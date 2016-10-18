package q5.queue;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class LockQueue implements MyQueue {
	ReentrantLock enqLock, deqLock;
	Node head;
	Node tail;
	Condition notEmpty;
	AtomicInteger count;
  public LockQueue() {
	  head =new Node(null);
	  tail = head;
	  enqLock = new ReentrantLock();
	  deqLock = new ReentrantLock();
	  notEmpty = deqLock.newCondition();
	  count = new AtomicInteger(0);
  }
  
  public boolean enq(Integer value) {
	if (value == null) throw new NullPointerException();
	enqLock.lock();
	try{
		Node e = new Node(value);
		tail.next = e;
		tail = e;
		count.getAndIncrement();
		deqLock.lock();
        notEmpty.signal();
	}finally{
		deqLock.unlock();
		enqLock.unlock();
	}
	  
    return true;
  }
  
  public Integer deq()  {
	  Integer result;
	  deqLock.lock();
	  try{
		  while(head.next == null){
			  Thread.yield();
		  }
		  result = head.next.value;
		  head = head.next;
		  count.getAndDecrement();
	  }finally{
		  deqLock.unlock();
	  }
    return result;
  }
  
  protected class Node {
	  public Integer value;
	  public Node next;
		    
	  public Node(Integer x) {
		  value = x;
		  next = null;
	  }
  }
}
