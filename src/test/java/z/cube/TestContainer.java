package java.z.cube;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayDeque;
import java.util.Deque;
import java.util.LinkedList;
import java.util.zip.CRC32;

import org.junit.Test;

public class TestContainer {
	@Test
	public final void test() {
		LinkedList<String>  queue=new LinkedList<String>();
		for(int i=0;i<20;i++){
			if(queue.size()<10){
				queue.addFirst(i+"");
			}else{
				queue.removeLast();
				queue.addFirst(i+"");
			}
			//System.out.println("A:"+queue);
		}
		for(String q:queue){
			System.out.println(q);
		}
		Deque<Integer> dq=new ArrayDeque<Integer>();
		
	}
	
	@Test
	public final void testReplace() throws IOException{
		String x= "abcde322222222222fg";
		CRC32 c = new CRC32();
		c.update(x.getBytes("UTF-8"));
		Long v = c.getValue();
		System.out.println(Long.toHexString(v));
	}

}
