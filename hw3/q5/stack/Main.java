package stack;

public class Main {
	static class Mythread extends Thread{
		public LockFreeStack sta;
		int t = 0;
		public Mythread(LockFreeStack l){
			sta = l;
		}
		public void run(){
			for(int i =1;i<=9;i++){
				sta.push(1);
			}
			for(int i =1; i<=10; i++){
				int j = 0;
				try {
					j = sta.pop();
				} catch (EmptyStack e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				t += j;
				System.out.println(t);
			}
		}
	}
	public static void main(String[] args){
		LockFreeStack locks = new LockFreeStack();
	      Thread[] thread=new Thread[10];
	      for(int j=0;j<10;j++){
				thread[j]=new Mythread(locks);
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
	      //System.out.println(lockq.count);
	      
	      
	}
	

}

