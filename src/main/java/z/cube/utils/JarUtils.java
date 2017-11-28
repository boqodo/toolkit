package z.cube.utils;

import java.io.IOException;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

public class JarUtils {
	
	private final static String JARTMPL="jar: file:%s!/";
	
	/**
	 * 统计jar包中的class个数
	 * @param jarPath	具体的jar包路径地址
	 * @return	总个数
	 * @throws IOException
	 */
	public static int countJarClass(String jarPath) throws IOException{
		URL url = new URL( String.format(JARTMPL,jarPath));
		JarURLConnection jarURLConnection = (JarURLConnection) url.openConnection();
		JarFile jarFile = jarURLConnection.getJarFile();
		Enumeration<JarEntry> jarEntries = jarFile.entries();
		int i = 0;
		while (jarEntries.hasMoreElements()) {
			JarEntry jarEntry = jarEntries.nextElement();
			if (jarEntry.getName().endsWith(".class")) {
				System.out.println(jarEntry.getName());
				i++;
			}
		}
		return i;
	}
	
	public static void main(String[] args) throws Exception {
		System.out.println(countJarClass("D:/scMajorProject/zy_common-3.1.jar"));
	}
}
