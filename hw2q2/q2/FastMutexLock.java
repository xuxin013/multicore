package q2;

import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.atomic.AtomicIntegerArray;

// TODO 
// Implement Fast Mutex Algorithm
public class FastMutexLock implements MyLock {
		public volatile int X = -1;
		public volatile int Y = -1;
		public int threadNums;
		private static AtomicBoolean[] flag  ;
		public FastMutexLock(int numThread) {
			threadNums=numThread;
			flag= new AtomicBoolean[threadNums] ;

			for(int i=0;i<threadNums;i++){
				flag[i]=new AtomicBoolean(false);}
			
		    }

    @Override
    public void lock(int myId) {
		while (true){
			flag[myId].set(true);
			X=myId;
			if(Y!=-1){
				flag[myId].set(false);
				while(Y!=-1)
				continue;
			}
			else{
				Y=myId;
				if(X==myId)
					return;
				else{
					flag[myId].set(false);
					for(int j=0;j<threadNums;j++){
						while(flag[j].get()!=false);
						}
				
					if(Y==myId)return;
					else{
						while(Y!=-1);
						continue;
					  }
					}
				}
			}
    }

    @Override
    public void unlock(int myId) {
		Y=-1;
		flag[myId].set(false);
    }
}
