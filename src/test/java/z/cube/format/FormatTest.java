package java.z.cube.format;

import java.text.ChoiceFormat;
import java.text.ParsePosition;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.junit.Test;

public class FormatTest {

	@Test
	public final void test(){
		 double[] limits = {1,2,3,4,5,6,7};
		 String[] dayOfWeekNames = {"Sun","Mon","Tue","Wed","Thur","Fri","Sat"};
		 ChoiceFormat form = new ChoiceFormat(limits, dayOfWeekNames);
		 ParsePosition status = new ParsePosition(0);
		 for (double i = 0.0; i <= 8.0; ++i) {
		     status.setIndex(0);
		     System.out.println(i + " -> " + form.format(i) + " -> "
		                              + form.parse(form.format(i),status));
		 }
	}
	@Test
	public final void test3() throws Exception{
		final BlockingQueue<Integer> queue=new ArrayBlockingQueue<Integer>(1);
		System.out.println("###");
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				try {
					System.out.println("%%%%%%%%%%");
					Thread.sleep(5000);
					queue.offer(99);
					System.out.println("%%%%%%%%%%");
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
		Integer i=queue.take();
		System.out.println("-----------"+i);
	}
}
