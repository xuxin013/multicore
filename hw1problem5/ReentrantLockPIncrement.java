import java.util.ArrayList;
import java.util.concurrent.locks.ReentrantLock;

public class ReentrantLockPIncrement implements Runnable {
    private int max = 1200000;
    private IntWrapper increment;
    private ReentrantLock lock;
    private int count;

    public ReentrantLockPIncrement(IntWrapper input, ReentrantLock lock, int numThreads) {
        this.increment = input;
        this.lock = lock;
        this.count = max / numThreads;
    }

    public static void main(String[] args) {
        System.out.println("number of threads, speed(ms), total number increment");
        ReentrantLock lock = new ReentrantLock();
        for (int i = 0; i < 8; i++) {
            int c = 0;
            long beginTime = System.currentTimeMillis();
            c = ReentrantLockPIncrement.parallelIncrement(0, lock, i + 1);
            long endTime = System.currentTimeMillis();
            System.out.println(String.format("%s, %s, %s", i + 1, endTime - beginTime, c));
        }
    }

    public void run() {
        while (count > 0) {
            lock.lock();
            increment.setInteger(increment.getInteger() + 1);
            count--;
            lock.unlock();
        }
    }

    public static Integer parallelIncrement(int c, ReentrantLock lock, int numThreads) {
        IntWrapper valueToBeIncremented = new IntWrapper(c);
        ArrayList<Thread> threads = new ArrayList<>();
        try {
            for (int i = 0; i < numThreads; i++) {
                threads.add(new Thread(new ReentrantLockPIncrement(valueToBeIncremented, lock, numThreads)));
                threads.get(i).start();
            }
            for (Thread thread : threads) {
                thread.join();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        return valueToBeIncremented.getInteger();
    }
}
