package hw2problem3;

public class CyclicBarrierDemo {
    public CyclicBarrier barrier;

    class DemoThread implements Runnable {
        public int threadNumber;

        public DemoThread(int number) {
            this.threadNumber = number;
        }

        public void run() {
            try {
                System.out.println("thread " + threadNumber + " passed barrier with arrival index of " + barrier.await());
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void createThreads(int numOfThreads) throws InterruptedException {
        barrier = new CyclicBarrier(numOfThreads);
        for (int i = 0; i < numOfThreads; i++) {
            new Thread(new DemoThread(i)).start();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        CyclicBarrierDemo cyclicBarrierDemo = new CyclicBarrierDemo();
        int numOfThreads = 10;
            cyclicBarrierDemo.createThreads(numOfThreads);
    }
}
