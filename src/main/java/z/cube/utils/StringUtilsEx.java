package z.cube.utils;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.Validate;

public class StringUtilsEx {
	
	/**
	 * 查找字符串中所有匹配正则表达式的内容并放到集合中返回
	 * @param s			被查找的字符串
	 * @param regex		需要匹配的正则表达式
	 * @param group		正则表达式中的分组值(正则表达式中的括号组)
	 * @return	
	 */
	public static List<String> findAll(String s,String regex,int group){
		Matcher m=Pattern.compile(regex).matcher(s);
		int groupCount=m.groupCount();
		Validate.isTrue(groupCount>=group,"%s超过最大分组数(%s)!",group,groupCount);
		List<String> rs=new ArrayList<String>(8);
		while(m.find()){
			rs.add(m.group(group));
		}
		return rs;
	}
	
	public static List<String> findAll(String s,String regex){
		return findAll(s,regex,0);
	}

	/**
	 * 计算某个字符在字符串中出现的次数
	 * 
	 * 注： 针对split最后分隔符为空的情况，split后获取的length有问题
	 * @param str	字符串
	 * @param c		字符
	 * @return	出现的次数
	 */
	public static int charTimes(String str,char c){
		char[] cs=str.toCharArray();
		int count=0;
		for(int i=0;i<cs.length;i++){
			if(cs[i]==c){
				count++;
			}
		}
		return count;
	}
	
	  /*由于Java是基于Unicode编码的，因此，一个汉字的长度为1，而不是2。 
     * 但有时需要以字节单位获得字符串的长度。例如，“123abc长城”按字节长度计算是10，而按Unicode计算长度是8。 
     * 为了获得10，需要从头扫描根据字符的Ascii来获得具体的长度。如果是标准的字符，Ascii的范围是0至255，如果是汉字或其他全角字符，Ascii会大于255。 
     * 因此，可以编写如下的方法来获得以字节为单位的字符串长度。*/  
    public static int getWordCount(String s)  
    {  
        int length = 0;  
        for(int i = 0; i < s.length(); i++)  
        {  
            int ascii = Character.codePointAt(s, i);  
            if(ascii >= 0 && ascii <=255)  
                length++;  
            else  
                length += 2;  
                  
        }  
        return length;  
          
    }  
      
    /*基本原理是将字符串中所有的非标准字符（双字节字符）替换成两个标准字符（**，或其他的也可以）。这样就可以直接例用length方法获得字符串的字节长度了*/  
    public static  int getWordCountRegex(String s)  
    {  
  
        s = s.replaceAll("[^\\x00-\\xff]", "**");  
        int length = s.length();  
        return length;  
    }  
      
    /*按特定的编码格式获取长度*/  
    public static int getWordCountCode(String str, String code) throws UnsupportedEncodingException{  
    	return str.getBytes(code).length;  
    }  
}
