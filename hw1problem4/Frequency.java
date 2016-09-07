
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Frequency implements Callable<Integer>{
	public static ExecutorService threadPool=Executors.newCachedThreadPool();
	public int begin;
	public int end;
	public int[] A;
    public int x;

	Frequency(int x, int begin, int end){
		this.begin=begin;
		this.end=end;		
		this.x=x;
	}

	public Integer call(){
		int answer=0;
		for(int i=this.begin;i<this.end;i++){
			if(A[i]==this.x){
				answer+=1;
			}
		}
		return answer;
	}
	public static int parallelFreq(int x, int[] A, int numThreads){

		int result=0;

			int interval=A.length/numThreads;
			int f;
			List<Future<Integer>> l=new ArrayList<>(); 
		for(int i=0;i<numThreads;i++){
			Future<Integer> future=threadPool.submit(new Frequency(x,interval*i,interval*i+1));
			l.add(future);
		}
		Iterator<Future<Integer>> it1 =l.iterator();
		while(it1.hasNext()){
			result+=it1.next().get();
			
		}
		return result;
	}
}
