package java.z.cube.compress;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.Key;
import java.util.zip.CRC32;
import java.util.zip.Deflater;

import javax.crypto.Cipher;
import javax.crypto.CipherInputStream;
import javax.crypto.CipherOutputStream;

import org.apache.commons.compress.compressors.deflate.DeflateCompressorInputStream;
import org.apache.commons.compress.compressors.deflate.DeflateCompressorOutputStream;
import org.apache.commons.compress.compressors.deflate.DeflateParameters;
import org.apache.commons.io.Charsets;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;

public class CompressTest {
	@Test
	public final void testOut() throws Exception{
		InputStream fileIS = new FileInputStream(new File("D:\\odoc\\text.doc"));
		
		OutputStream fileOS = new FileOutputStream(new File("D:\\odoc\\text_cc.tar"));
		
		Key keyTmp = getKey(crc32SecretKey("1234567890"));
		Cipher c = Cipher.getInstance("DES");
		c.init(Cipher.ENCRYPT_MODE, keyTmp);
		fileOS = new CipherOutputStream(fileOS, c);
		
		DeflateParameters dp = new DeflateParameters();
		dp.setCompressionLevel(Deflater.BEST_COMPRESSION);
		
		// 压缩
		fileOS = new DeflateCompressorOutputStream(fileOS,dp);
		
		
		IOUtils.copy(fileIS, fileOS);
		
		IOUtils.closeQuietly(fileIS);
		IOUtils.closeQuietly(fileOS);
	}
	
	
	
	private Key getKey(byte[] bytes) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];

		// 将原始字节数组转换为8位
		for (int i = 0; i < bytes.length && i < arrB.length; i++) {
			arrB[i] = bytes[i];
		}

		// 生成密钥
		Key key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");

		return key;
	}
	
	/**
	 * 将密钥CRC32，取得8位的数组
	 * 
	 * @param sk
	 * @return
	 */
	private byte[] crc32SecretKey(String sk) {
		byte[] bs = sk.getBytes(Charsets.UTF_8);
		CRC32 c = new CRC32();
		c.update(bs);
		String hex = Long.toHexString(c.getValue());
		return hex.getBytes(Charsets.UTF_8);
	}

	
	@Test
	public final void testIn() throws Exception{
		InputStream fileIS = new FileInputStream(new File("D:\\odoc\\3c3ff2fd-fe53-4285-9b20-a0514688c136"));
		
		Key keyTmp = getKey(crc32SecretKey("1234567890"));
		Cipher c = Cipher.getInstance("DES");
		c.init(Cipher.DECRYPT_MODE, keyTmp);
		fileIS = new CipherInputStream(fileIS, c);
		
		DeflateParameters dp = new DeflateParameters();
		dp.setCompressionLevel(Deflater.BEST_COMPRESSION);
		fileIS = new DeflateCompressorInputStream(fileIS,dp);
		
		
		FileUtils.copyInputStreamToFile(fileIS, new File("D:\\odoc\\3c3ff2fd-fe53-4285-9b20-a0514688c136.txt"));
	}
}
