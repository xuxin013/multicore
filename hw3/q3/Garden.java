package q3;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class Garden {
	int Max ;  
	long Newtoncount=0;
	long Bencount=0;
	long Marycount=0;
	boolean shovel = true;
	final ReentrantLock lock = new ReentrantLock();
	final Condition newton = lock.newCondition();
	final Condition benjamin = lock.newCondition();
	final Condition mary = lock.newCondition();
	public Garden(int i){
		Newtoncount=0;
		Bencount=0;
		Marycount=0;
		shovel = true;
		Max = i;
	}
	public void startDigging()throws InterruptedException{
		lock.lock();
		try{
			while(shovel==false||Newtoncount-Marycount>=Max)
				newton.await();
			shovel=false;
		} finally{
			lock.unlock();
		}
	}
	public void doneDigging(){
		lock.lock();
		Newtoncount++;
		shovel = true;
		benjamin.signal();
		mary.signal(); 
		lock.unlock();
	}

	public void startSeeding()throws InterruptedException{
		lock.lock();
		try{
			while(Newtoncount<=Bencount)
				benjamin.await();
			} finally{
		lock.unlock();}
	}
	public void doneSeeding(){
		lock.lock();
		Bencount++;
		mary.signal();
		lock.unlock();
	}

	public void startFilling()throws InterruptedException{
		lock.lock();
		try{
			while(shovel==false||Bencount<=Marycount)
				mary.await();
			shovel=false;
		} finally{
			lock.unlock();
		}
	}
	public void doneFilling(){
		lock.lock();
		Marycount++;
		shovel = true;
		newton.signal();
		lock.unlock();
	}
	
	protected static class Newton implements Runnable {
		Garden garden;
		public Newton(Garden garden){
			this.garden = garden;
		}
		@Override
		public void run() {
		    while (true) {
                try {
					garden.startDigging();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    dig();
				garden.doneDigging();
				System.out.println(1);
			}
		} 
		
		private void dig(){
		}
	}
	
	protected static class Benjamin implements Runnable {
		Garden garden;
		public Benjamin(Garden garden){
			this.garden = garden;
		}
		@Override
		public void run() {
		    while (true) {
                try {
					garden.startSeeding();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				plantSeed();
				garden.doneSeeding();
				System.out.println(2);
			}
		} 
		
		private void plantSeed(){
		}
	}
	
	protected static class Mary implements Runnable {
		Garden garden;
		public Mary(Garden garden){
            this.garden = garden;
		}
		@Override
		public void run() {
		    while (true) {
                try {
					garden.startFilling();
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			 	Fill();
			 	garden.doneFilling();
			 	System.out.println(3);
			}
		} 
		
		private void Fill(){
		}
	}
}

