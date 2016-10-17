package q3;

import q3.Garden.Benjamin;
import q3.Garden.Mary;
import q3.Garden.Newton;

public class Main {
//	static class Newton extends Thread{
//		public Garden garden;
//		public Newton (Garden g){
//			garden = g;
//		}
//		public void run(){
//			while(true){
//			garden.startDigging();
//			System.out.println("Newton");
//			garden.doneDigging();
//			}
//		}	
//	}
//	static class Benjamin extends Thread{
//		public Garden garden;
//		public Benjamin (Garden g){
//			garden = g;
//		}
//		public void run(){
//			while(true){
//			garden.startSeeding();
//			System.out.println("Benjamin");
//			garden.doneSeeding();
//			}
//		}
//	}
//	static class Mary extends Thread{
//		public Garden garden;
//		public Mary (Garden g){
//			garden = g;
//		}
//		public void run(){
//			while(true){
//			garden.startFilling();
//			System.out.println("Mary");
//			garden.doneFilling();
//			
//			}
//		}
//	}
	public static void main(String[] args){
		Garden gar = new Garden();

		Newton n1 = new Newton(gar);
		Benjamin n2 = new Benjamin(gar);
		Mary n3 = new Mary(gar);
		Thread t1= new Thread(n1);
		Thread t2 = new Thread(n2);
		Thread t3 = new Thread(n3);
		t1.start();
		t2.start();
		t3.start();
		
	}
	
	

}
