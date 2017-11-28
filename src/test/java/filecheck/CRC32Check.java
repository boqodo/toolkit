package java.filecheck;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.StringReader;
import java.util.Collection;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;

import javax.xml.bind.JAXB;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.w3c.dom.Document;
import org.xml.sax.SAXException;

public class CRC32Check {
	@Test
	public final void testConver() throws Exception{
		String dir="D:\\SyncDriver\\BaiduYun\\github\\CloudReader";
		Collection<File> fss=FileUtils.listFiles(new File(dir), new String[]{"xml"}, true);
		for(File fs :fss){
			String c=FileUtils.readFileToString(fs, "UTF-8");
			if(fs.delete()){
				System.out.println(fs.toString());
				FileUtils.write(fs, c, "UTF-8");
			}
		}
	}
	
	
	@Test
	public final void testByteArray() throws Exception{
		String fileName ="F:\\cn_windows_7_ultimate_with_sp1_x64_dvd_618537.iso";
		InputStream in = new FileInputStream(new File(fileName));

		OutputStream out = new ByteArrayOutputStream();
		
		IOUtils.copy(in, out);
		
		IOUtils.closeQuietly(in);
		IOUtils.closeQuietly(out);
	}
	
	
	@Test
	public final void testCrc32() throws IOException{
		//String fileName ="F:\\MOVIE\\MJ\\魔戒整合包.pdf";
//		String fileName ="F:\\windows_xp_professional_with_service_pack_3_x86.iso";
//		String fileName ="F:\\VM\\Andy.ova";
		String fileName ="F:\\cn_windows_7_ultimate_with_sp1_x64_dvd_618537.iso";
		InputStream in = new FileInputStream(new File(fileName));
//		CRC32 c = new CRC32();
//		byte[] buffer = new byte[in.available()];
//		IOUtils.readFully(in, buffer);
//		c.update(buffer);
//		String o = Long.toHexString(c.getValue());
//		System.out.println(o);
		//2715978741
		//4294967295
		//2560037007
		//3648674951
		CRC32 cksum = new CRC32();
		CheckedInputStream cis = new CheckedInputStream(in, cksum);
		byte[] buf = new byte[128];
		while (cis.read(buf) >= 0) {
		}
		System.out.println(cis.getChecksum().getValue());
	}
	
	@Test
	public void testFile() throws IOException{
		// 文件大小是否可以超过 ${fileSize}
		// 移动端是否缓存
		// 服务端是否缓存
		// 文件存放位置
		
		String fileName ="D:\\Ecma-262.pdf";
		InputStream in = new FileInputStream(new File(fileName));
		System.out.println(in.available());
		
		boolean isFrom = InputStream.class.isAssignableFrom(FileInputStream.class);
		System.out.println(isFrom);
		
		boolean isFrom2 = Readable.class.isAssignableFrom(FileReader.class);
		System.out.println(isFrom2);
	}
	

	@Test
	public void testName() throws Exception {
		StopWatch w = new StopWatch();
		w.start();
		try {
			String fileName ="D:\\appdb\\meituan_cities.db";
			CheckedInputStream cis = null;
			long fileSize = 0;
			try {
				// Computer CRC32 checksum
				cis = new CheckedInputStream(new FileInputStream(fileName),
						new CRC32());

				fileSize = new File(fileName).length();

			} catch (FileNotFoundException e) {
				
			}

			byte[] buf = new byte[128];
			while (cis.read(buf) >= 0) {
			}

			long checksum = cis.getChecksum().getValue();
			
			System.out.println(Long.toHexString(checksum) + " " + fileSize + " " + fileName);

		} catch (IOException e) {
			e.printStackTrace();
			System.exit(1);
		}
		w.stop();
		System.out.println(w.getNanoTime());
	}
}
