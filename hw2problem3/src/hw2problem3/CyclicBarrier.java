package hw2problem3;

import java.util.concurrent.Semaphore;

public class CyclicBarrier {
    private Semaphore semaphore;
    private int counter;

    /**
     * Creates a new CyclicBarrier that will trip when
     * the given number of parties (threads) are waiting upon it
     *
     * @param parties the number of threads needed to be blocked
     *        before the last thread reaches the barrier
     * @throws InterruptedException if the current thread is interrupted
     */
    public CyclicBarrier(int parties) throws InterruptedException {
        semaphore = new Semaphore(1);
        semaphore.acquire(1);
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
    public synchronized int await() throws InterruptedException {
        int threadIndex = 0;
        System.out.println("thread waiting at barrier");
        if (counter == 0) {
            semaphore.release(1);
            notifyAll();
        } else {
            threadIndex = counter;
            counter--;
            wait();
        }
        return threadIndex;
    }
}