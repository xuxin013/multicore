package q2;

public class Main {
	
	static class Mythread extends Thread{
		public int n;
		public int myId;
		public MyLock lock;
		public Counter counter;
		public Mythread (int nnumber,int id,MyLock l, Counter c){
			n=nnumber;
			myId=id;
			lock=l;
			counter=c;
		}
		public void run(){
			for(int i=0;i<n;i++){
				lock.lock(myId);
				counter.increment();
				lock.unlock(myId);
				
			}
		}	
	}
	
    public static void main (String[] args) {
        Counter counter=null;
        MyLock lock=null;
        long executeTimeMS = 0;
        int numThread = 6;
        int numTotalInc = 1200000;

        if (args.length < 3) {
            System.err.println("Provide 3 arguments");
            System.err.println("\t(1) <algorithm>: fast/bakery/synchronized/"
                    + "reentrant");
            System.err.println("\t(2) <numThread>: the number of test thread");
            System.err.println("\t(3) <numTotalInc>: the total number of "
                    + "increment operations performed");
            System.exit(-1);
        }
        numThread = Integer.parseInt(args[1]);
        numTotalInc = Integer.parseInt(args[2]);

        if (args[0].equals("fast")) {
            lock = new FastMutexLock(numThread);
            counter = new LockCounter(lock);
        } else if (args[0].equals("bakery")) {
            lock = new BakeryLock(numThread);
            counter = new LockCounter(lock);
        } else {
            System.err.println("ERROR: no such algorithm implemented");
            System.exit(-1);
        }

        // TODO
        // Please create numThread threads to increment the counter
        // Each thread executes numTotalInc/numThread increments
        // Please calculate the total execute time in millisecond and store the
        // result in executeTimeMS
        long beginTime = System.currentTimeMillis();
        Thread[] thread=new Thread[numThread];
		int n =numTotalInc/numThread;
		for(int j=0;j<numThread;j++){
			thread[j]=new Mythread(n,j,lock,counter);
			thread[j].start();
			}
		for(int m=0;m<numThread;m++){
			try {
				thread[m].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
		int t=counter.getCount();
	   long endTime = System.currentTimeMillis();
	   executeTimeMS=endTime-beginTime;
          System.out.println(String.format("%s, %s, %s", numThread,
                  endTime - beginTime, t));}
		

       // System.out.println(executeTimeMS);
    }


