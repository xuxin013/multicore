import java.util.ArrayList;

public class SynchronizedPIncrement implements Runnable {
    private int max = 1200000;
    private IntWrapper increment;

    public SynchronizedPIncrement(IntWrapper input) {
        this.increment = input;
    }

    public static void main(String[] args) {
        System.out.println("number of threads, speed(ms), total number increment");
        for (int i = 0; i < 8; i++) {
            int c = 0;
            long beginTime = System.currentTimeMillis();
            c = SynchronizedPIncrement.parallelIncrement(0, i + 1);
            long endTime = System.currentTimeMillis();
            System.out.println(String.format("%s, %s, %s", i + 1, endTime - beginTime, c));
        }
    }

    public synchronized Integer getResult() {
        return increment.getInteger();
    }

    public synchronized void increment() {
        increment.setInteger(increment.getInteger() + 1);
    }

    public void run() {
        synchronized (increment) {
            while (this.getResult() < this.max) {
                this.increment();
            }
        }
    }

    public static Integer parallelIncrement(int c, int numThreads) {
        IntWrapper valueToBeIncremented = new IntWrapper(c);
        ArrayList<Thread> threads = new ArrayList<>();
        try {
            for (int i = 0; i < numThreads; i++) {
                threads.add(new Thread(new SynchronizedPIncrement(valueToBeIncremented)));
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
