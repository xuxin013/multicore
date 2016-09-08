import java.util.concurrent.atomic.AtomicInteger;

public class AtomicIntegerPIncrement implements Runnable {
    public int max = 1200000;
    private AtomicInteger result;

    public AtomicIntegerPIncrement(AtomicInteger atomicC, int numThreads) {
        this.result = atomicC;
    }

    public static void main(String[] args) {
        System.out.println("number of threads, speed(ms), total number increment");
        for (int i = 0; i < 8; i++) {
            int c = 0;
            long beginTime = System.currentTimeMillis();
            c = AtomicIntegerPIncrement.parallelIncrement(0, i + 1);
            long endTime = System.currentTimeMillis();
            System.out.println(String.format("%s, %s, %s", i + 1,
                    endTime - beginTime, c));
        }
    }

    public void run() {
        while(this.result.get() < this.max) {
            this.result.compareAndSet(this.result.get(), this.result.get() + 1);
        }
    }

    public static int parallelIncrement(int c, int numThreads) {
        AtomicInteger atomicC = new AtomicInteger(0);
        try {
            for (int i = 0; i < numThreads; i++) {
                new AtomicIntegerPIncrement(atomicC, numThreads).run();
            }
        } catch (Exception e) {
            System.err.println(e);
        }
        c = atomicC.get();
        return c;
    }
}
