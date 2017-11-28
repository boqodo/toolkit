package java.io.netty.example.discard;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class TimeServerThreadPool {
	
	private ExecutorService executor;
	public TimeServerThreadPool(int maximumPoolSize,int capacity){
		int corePoolSize = Runtime.getRuntime().availableProcessors();
		BlockingQueue<Runnable> workQueue= new ArrayBlockingQueue<Runnable>(capacity);
		executor = new ThreadPoolExecutor(corePoolSize , maximumPoolSize, 120L, TimeUnit.SECONDS, workQueue);
	}
	
	
}
