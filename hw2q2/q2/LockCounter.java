package q2;

// TODO
// Use MyLock to protect the count

public class LockCounter extends Counter {
	MyLock mylock;
    public LockCounter(MyLock lock) {
    	mylock=lock;
    }

    @Override
    public void increment() {
    	count++;
    }

    @Override
    public int getCount() {
        return count;
    }
}
