package z.cube.utils;

public class StringUtilsEx {

	/**
	 * 计算某个字符在字符串中出现的次数
	 * 
	 * 注： 针对split最后分隔符为空的情况，split后获取的length有问题
	 * @param str	字符串
	 * @param c		字符
	 * @return	出现的次数
	 */
	@SuppressWarnings("unused")
	private int charTimes(String str,char c){
		char[] cs=str.toCharArray();
		int count=0;
		for(int i=0;i<cs.length;i++){
			if(cs[i]==c){
				count++;
			}
		}
		return count;
	}
}
