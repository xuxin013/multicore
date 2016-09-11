
/**
 * Frequency.java
 * Created by Xin Xu and Angzhi Li
 * EE382C HW1Problem4
 */
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Frequency implements Callable<Integer> {
    public static ExecutorService threadPool = Executors.newCachedThreadPool();
    public int[] A;
    public int x;

    Frequency(int x, int[] A) {
        this.A = A;
        this.x = x;
    }

    public Integer call() {
        int result = 0;
        try {
            for(int y : A) {
                if (y == x) {
                    result++;
                }
            }
        } catch (Exception e) {
            System.err.println(e); 
        }
        return result;
    }

    public static int parallelFreq(int x, int[] A, int numThreads) {
        int result = 0;
        int interval = A.length / numThreads;
        int index = 0;
        try {
            List<Future<Integer>> listOfFutureIntegers = new ArrayList<Future<Integer>>();
            for (int i = 0; i < numThreads - 1; i++) {
                int[] newA = Arrays.copyOfRange(A, index, index + interval + 1);
                index += interval + 1;
                Future<Integer> threadResult = threadPool.submit(new Frequency(x, newA));
                listOfFutureIntegers.add(threadResult);
            }
            //remainder case
            int[] newA = Arrays.copyOfRange(A, index, A.length);
            listOfFutureIntegers.add(threadPool.submit(new Frequency(x, newA)));
            //getting results
            Iterator<Future<Integer>> iterator = listOfFutureIntegers.iterator();
            while (iterator.hasNext()) {
                result += iterator.next().get();
            }
        } catch (Exception e) {
            System.err.println(e);
        } finally {
	    threadPool.shutdown();
	}
        return result;
    }

    public static void main(String[] args) {
        // some random number to be checked on frequency
        int x = 7;
        // some random numbers to check on frequency
        int[] A = { 1, 2, 7, 4, 7, 54, 7};
        int numThreads = 3; // number of threads to run in parallel
        System.out.println(
                String.format("the frequency of %s appeared in A is %s", x, Frequency.parallelFreq(x, A, numThreads)));
    }
}
