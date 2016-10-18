package q4;

import java.util.ArrayList;
import java.util.Random;

public class Hw3problem4Demo {
    public ArrayList<Integer> array;
    public CoarseGrainedListSet coarseGrainedListSet;
    public FineGrainedListSet fineGrainedListSet;
    public LockFreeListSet lockFreeListSet;

    public Hw3problem4Demo(ArrayList<Integer> init) {
        this.array = init;
    }

    public void setCoarseGrainedListSet(CoarseGrainedListSet coarseGrainedListSet) {
        this.coarseGrainedListSet = coarseGrainedListSet;
    }

    public void setFineGrainedListSet(FineGrainedListSet fineGrainedListSet) {
        this.fineGrainedListSet = fineGrainedListSet;
    }

    public void setLockFreeListSet(LockFreeListSet lockFreeListSet) {
        this.lockFreeListSet = lockFreeListSet;
    }

    private class TestThread implements Runnable {
        private int threadNumber;

        public TestThread(int number) {
            this.threadNumber = number;
        }

        public void run() {
            // TODO Auto-generated method stub

        }

    }

    public static void main(String[] args) {
        ArrayList<Integer> array = new ArrayList<>();
        Random random = new Random();
        for (int i = 0; i < 10; i++) {
            array.add(random.nextInt(10));
            System.out.print(array.get(i));
            System.out.print(", ");
        }
        Hw3problem4Demo demo = new Hw3problem4Demo(array);
        System.out.print("\n");
        coarseLockDemo(demo);
        fineGrainedLockDemo(demo);
        lockFreeDemo(demo);
    }

    public static void coarseLockDemo(Hw3problem4Demo demo) {
        Integer toRemove = demo.array.get(0);
        Integer contains = demo.array.get(1);
        long time = System.currentTimeMillis();
        demo.setCoarseGrainedListSet(new CoarseGrainedListSet());
        for(Integer x : demo.array) {
            System.out.print(demo.coarseGrainedListSet.add(x));
            System.out.print(", ");
        }
        System.out.print("\n");
        System.out.println(demo.coarseGrainedListSet.remove(toRemove));
        System.out.println(demo.coarseGrainedListSet.contains(toRemove));
        System.out.println(demo.coarseGrainedListSet.contains(contains));
        time = System.currentTimeMillis() - time;
        System.out.println("CoarseLockDemo runs in " + time + " ms");
    }

    public static void fineGrainedLockDemo(Hw3problem4Demo demo) {
        Integer toRemove = demo.array.get(0);
        Integer contains = demo.array.get(1);
        long time = System.currentTimeMillis();
        demo.setFineGrainedListSet(new FineGrainedListSet());
        for(Integer x : demo.array) {
            System.out.print(demo.fineGrainedListSet.add(x));
            System.out.print(", ");
        }
        System.out.print("\n");
        System.out.println(demo.fineGrainedListSet.remove(toRemove));
        System.out.println(demo.fineGrainedListSet.contains(toRemove));
        System.out.println(demo.fineGrainedListSet.contains(contains));
        time = System.currentTimeMillis() - time;
        System.out.println("fineGrainedLockDemo runs in " + time + " ms");
    }

    public static void lockFreeDemo(Hw3problem4Demo demo) {
        Integer toRemove = demo.array.get(0);
        Integer contains = demo.array.get(1);
        long time = System.currentTimeMillis();
        demo.setLockFreeListSet(new LockFreeListSet());
        for(Integer x : demo.array) {
            System.out.print(demo.lockFreeListSet.add(x));
            System.out.print(", ");
        }
        System.out.print("\n");
        System.out.println(demo.lockFreeListSet.contains(contains));
        System.out.println(demo.lockFreeListSet.remove(toRemove));
        System.out.println(demo.lockFreeListSet.contains(contains));
        time = System.currentTimeMillis() - time;
        System.out.println("lockFreeDemo runs in " + time + " ms");
    }
}
