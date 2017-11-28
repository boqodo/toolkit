package java.filecheck;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.zip.CRC32;
import java.util.zip.CheckedInputStream;



public class FileToCRCUtil {

	public static String getCRC32(String fileUri) {
		CRC32 crc32 = new CRC32();
		FileInputStream fileinputstream = null;
		CheckedInputStream checkedinputstream = null;
		String crc = null;
		try {
			fileinputstream = new FileInputStream(new File(fileUri));
			checkedinputstream = new CheckedInputStream(fileinputstream, crc32);
			while (checkedinputstream.read() != -1) {
			}
			crc = Long.toHexString(crc32.getValue()).toUpperCase();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (fileinputstream != null) {
				try {
					fileinputstream.close();
				} catch (IOException e2) {
					e2.printStackTrace();
				}
			}
			if (checkedinputstream != null) {
				try {
					checkedinputstream.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return crc;
	}


	public static void main(String[] args) {
		String uri = "D:\\Ecma-262.pdf";
		System.out.println(getCRC32(uri));
	}

}