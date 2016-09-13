package petersonTournament;

public class PIncrement {
    public int numThreads;

    public static final int l = 1200000;
    public static volatile int result = 0;

    public static int parallelIncrement(int c, int numThreads) throws InterruptedException {
        Thread[] thread = new Thread[numThreads];
        int n = l / numThreads;
        Tournament tournament = new Tournament();
        result = c;
        for (int i = 0; i < numThreads; i++) {
            thread[i] = new Pthread(i, n, tournament);
            thread[i].start();
        }
        for (int i = 0; i < numThreads; i++) {
            thread[i].join();
        }
        return result;
    }

    public static void add() {
        result++;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 1; i <= 8; i *= 2) {
            long startTime = System.currentTimeMillis();
            int res = parallelIncrement(0, i);
            long endTime = System.currentTimeMillis();
            System.out.println(String.format("%s, %s", res, endTime - startTime));
        }
    }
}
