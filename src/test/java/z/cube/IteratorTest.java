package java.z.cube;

import java.io.File;
import java.util.Iterator;

import org.apache.commons.collections.iterators.ArrayIterator;
import org.apache.commons.collections.iterators.ObjectArrayIterator;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.apache.commons.io.filefilter.FileFilterUtils;
import org.apache.commons.io.filefilter.IOFileFilter;
import org.apache.commons.lang3.StringUtils;
import org.junit.Test;

public class IteratorTest {

	@Test
	public final void test(){
		int[] i={23,2,2,3,4,6,9};
		Iterator it=new ArrayIterator(i);
		while(it.hasNext()){
			System.out.println(it.next());
		}
		String[] s={"x","y","z"};
		it=new ObjectArrayIterator(s);
		while(it.hasNext()){
			System.out.println(it.next());
		}
	}
	@Test
	public final void testReadFileName(){
		
		String dirPath="D:\\ZhouYun\\ainibaobei\\";

		File file=new File(dirPath);
		for(File f:file.listFiles()){
			if(f.isDirectory()){
				
				File[] files = f.listFiles();
				System.out.println(StringUtils.center(f.getName(), 50, "%")+"总计："+files.length);
				for(File t: files){
					System.out.println(t.getName());
				}
			}
		}
	}
}
