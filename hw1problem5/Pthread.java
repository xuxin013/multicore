package Peterson;

public class Pthread extends Thread {
	public int n;
	public int threadNums;
	public Tournament t1;
	public Pthread(int pnumber,int countnumber, Tournament tournament){
		threadNums=pnumber;
		n=countnumber;
		t1=tournament;
	}


	public void run(){
		for(int i=0;i<n;i++){
		t1.requestCS(threadNums);
		PIncrement.add();
		t1.releaseCS(threadNums);
		}
	}

}
