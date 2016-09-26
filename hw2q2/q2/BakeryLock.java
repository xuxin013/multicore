package q2;


import java.util.concurrent.atomic.AtomicInteger;



// TODO
// Implement the bakery algorithm

public class BakeryLock implements MyLock {
	  	int N;
	    private static AtomicInteger[] choosing; // inside doorway
	    private static AtomicInteger[] number;
	    
	    public BakeryLock(int numThread) {
	    	N = numThread;
	    	choosing = new AtomicInteger[N];
	    	number = new AtomicInteger[N];
	    	for(int j=0;j<N;j++){
	    		choosing[j]=new AtomicInteger(0);
	    		number[j]=new AtomicInteger(0);
	    	}
	    }
    @Override
    public void lock(int myId) {
    	choosing[myId].set(1);
    	for(int j=0;j<N;j++){
    		if(number[j].get() > number[myId].get()){
    			number[myId].set(number[j].get());
    		}
    	}
    	number[myId].addAndGet(1);
    	choosing[myId].set(0);
    	
    	for(int j=0;j<N;j++){
    		while(choosing[j].get()==1);
    		while((number[j].get()!=0)&&((number[j].get()<number[myId].get()))||
    				((number[j].get()==number[myId].get())&&j<myId));
    	}
    }

    @Override
    public void unlock(int myId) {
    	number[myId].set(0);
    }
}
