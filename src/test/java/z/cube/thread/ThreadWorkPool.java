package java.z.cube.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class ThreadWorkPool {
	private final Map<Thread,Boolean> threadPool;
	
	private Stack<Integer> workPool=new Stack<Integer>();
	
	public ThreadWorkPool(int size) throws Exception{
		threadPool=new HashMap<Thread,Boolean>(size);
		for(int i=0;i<size;i++){
			Thread n=new Thread(new Runnable() {
				
				@Override
				public void run() {
					while(true){
						Thread cur=Thread.currentThread();
						try {
							Thread.currentThread().sleep(500);
							work();
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						threadPool.put(cur, true);
					}
				}
			});
			threadPool.put(n,true);
			n.start();
		}
	}
	
	public void push(Integer work){
		workPool.push(work);
	}
	
	public synchronized void work(){
		if(!workPool.isEmpty()){
			Thread cur=Thread.currentThread();
			if(threadPool.get(cur)){
				System.out.println("开始工作"+cur.getName());
				threadPool.put(cur, false);
				//提取工作
				Integer doting=workPool.pop();
				System.out.println(cur.getName()+"doting"+doting);
				System.out.println("工作完成"+cur.getName());
			}
		}
	}
}
