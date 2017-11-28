package java.z.cube.utils;

import static org.junit.Assert.*;

import org.junit.Test;

public class FormatUtilsTest {

	@Test
	public void testZeroize() {
		System.out.println(String.format("%03d",2));
		System.out.println(String.format("%.3f",20202f));
	}

}
