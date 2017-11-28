package z.cube.utils;

public class FormatUtils {

	public static String zeroize(int size,Number number){
		return String.format("%0"+size+"d",number);
	}
}
