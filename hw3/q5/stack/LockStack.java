package q5.stack;

public class LockStack implements MyStack {
	Node top = null;
	
  public LockStack() {
	  top = null;
  }
  
  public synchronized boolean push(Integer value) {
	  // implement your push method here
	  Node node = new Node(value);
	  node.next = top;
	  top = node;
	  return true;
  }
  
  public synchronized Integer pop() throws EmptyStack {
	  // implement your pop method here
	  if(top == null){
		  throw new EmptyStack();
	  }else{
		  Node oldTop = top;
		  top = top.next;
		  return oldTop.value;
		  
	  }
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
