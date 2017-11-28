package java.z.cube.thread;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.FutureTask;

import org.junit.Test;

public class ExecutorTest {
	
	@Test
	public void test2() throws Exception{
		ThreadWorkPool twp=new ThreadWorkPool(5);
		for(int i=0;i<10;i++){
			Thread.currentThread().sleep(5000);
			twp.push(i);
		}
		Thread.currentThread().sleep(2000000);
	}

	@Test
	public void test() throws Exception{
		Executor executor=Executors.newCachedThreadPool();
		executor.execute(new Runnable() {
			@Override
			public void run() {
				System.out.println("111111111111111");
			}
		});
		System.out.println("2222222222222");
		FutureTask<Integer> future=new FutureTask<Integer>(new Callable<Integer>() {
			@Override
			public Integer call() throws Exception {
				System.out.println("333333333333");
				Thread.currentThread().sleep(5000);
				System.out.println("4444444444444");
				return 2;
			}
		});
		System.out.println("55555555555");
		executor.execute(future);
		System.out.println("66666666666");
		System.out.println(future.get());
		System.out.println("77777777777777");
	}
}
