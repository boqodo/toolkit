package java.snappy;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.xerial.snappy.Snappy;

public class SnappyTest {

	@Test
	public final void test() throws UnsupportedEncodingException, IOException {
		String input = "Hello snappy-java! Snappy-java is a JNI-based wrapper of "
				+ "Snappy, a fast compresser/decompresser.";
		byte[] compressed = Snappy.compress(input.getBytes("UTF-8"));
		byte[] uncompressed = Snappy.uncompress(compressed);

		String result = new String(uncompressed, "UTF-8");
		
		byte[] fileArray=FileUtils.readFileToByteArray(new File("D:\\20150420111956_0059.xls"));
		StopWatch watch=new StopWatch();
		watch.start();
		byte[] fileCompressed = Snappy.compress(fileArray);
		watch.stop();
		System.out.println(watch.getNanoTime());
		FileUtils.writeByteArrayToFile(new File("D:\\20150420111956_0059.sy"), fileCompressed);
		System.out.println(result);
	}
}
