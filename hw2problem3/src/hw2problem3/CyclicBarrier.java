package hw2problem3;

import java.util.concurrent.Semaphore;

public class CyclicBarrier {
    private Semaphore mutex;
    private Semaphore barrier;
    private int counter;
    private int parties;

    /**
     * Creates a new CyclicBarrier that will trip when
     * the given number of parties (threads) are waiting upon it
     *
     * @param parties the number of threads needed to be blocked
     *        before the last thread reaches the barrier
     * @throws InterruptedException if the current thread is interrupted
     */
    public CyclicBarrier(int parties) throws InterruptedException {
        barrier = new Semaphore(0);
        mutex = new Semaphore(1);
        this.parties = parties;
        counter = parties - 1;
    }

    /**
     * Waits until all parties have invoked await on this barrier.
     * If the current thread is not the last to arrive then it is
     * disabled for thread scheduling purposes and lies dormant until
     * the last thread arrives.
     *
     * @return the arrival index of the current thread, where index
     * (parties - 1) indicates the first to arrive and zero indicates
     * the last to arrive.
     */
    public int await() throws InterruptedException {
        int threadIndex = 0;
        mutex.acquire();
        threadIndex = counter;
        counter--;
        mutex.release();
        if (counter == 0) {
            threadIndex = 0;
            counter = parties - 1;
            barrier.release(parties);
        } else {
            barrier.acquire();
        }
        return threadIndex;
    }
}