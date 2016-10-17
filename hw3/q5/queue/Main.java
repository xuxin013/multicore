package queue;
public class Main {
	
	static class Mythread extends Thread{
		public LockQueue que;
		int t = 0;
		public Mythread(LockQueue l){
			que = l;
		}
		public void run(){
			for(int i =1;i<=9;i++){
				que.enq(1);
			}
			for(int i =1; i<=10; i++){
				int j = que.deq();
				t += j;
				System.out.println(t);
			}
		}
	}
	
	
	public static void main(String[] args){
		LockQueue lockq = new LockQueue();
	      Thread[] thread=new Thread[10];
	      for(int j=0;j<10;j++){
				thread[j]=new Mythread(lockq);
				thread[j].start();
				}
			for(int m=0;m<10;m++){
				try {
					thread[m].join();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		}
	      System.out.println(lockq.count);
	      
	      
	}

}

