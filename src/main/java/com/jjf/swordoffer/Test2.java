package com.jjf.swordoffer;

/**
 * ��ʵ��һ����������һ���ַ����еĿո��滻�ɡ�%20����
 * ���磬���ַ���ΪWe Are Happy.�򾭹��滻֮����ַ���ΪWe%20Are%20Happy��
 * @author jjf_lenovo
 * ʱ�����ƣ�1�� �ռ����ƣ�32768K
 * 2017��2��24��19:04:01
 * ����ʱ�䣺36ms ռ���ڴ棺636k
 */
public class Test2 {
	public static void main(String args[]){
		System.out.println(replaceSpace2(new StringBuffer("We Are Happy")));
	}
	public static String replaceSpace(StringBuffer str) {
    	return str.toString().replace(" ", "%20");//java��  ����ʱ�䣺36ms ռ���ڴ棺636k
    }
	
	public static String replaceSpace2(StringBuffer str) {
    	char chs[] = str.toString().toCharArray(); //��������˼��  ����ʱ�䣺����ʱ�䣺31ms ռ���ڴ棺503k
    	StringBuilder sb = new StringBuilder();
    	for(char ch :chs){
    		if(ch==' '){
//    			sb.append("%20");
				sb.append('%');
				sb.append('2');
				sb.append('0');
    		}
    		else{
    			sb.append(ch);
    		}
    	}
    	return sb.toString();
    }
}
