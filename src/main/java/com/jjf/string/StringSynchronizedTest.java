package com.jjf.string;

/**
 * ����String,StringBuilder,StringBuffer �����Ƿ��̰߳�ȫ
 * 2017��2��20��22:03:41
 * @author jjf_lenovo
 * Edit:2017��2��20��23:10:15
 */
public class StringSynchronizedTest {
	static String string = "String" ;
	public static void main(String args[]){
		//TODO-1.String���̰߳�ȫ��(String��final���ˣ��������д������֤���̰߳�ȫ�أ���������ķ�����д�ģ�д�������Ǵ��) �Ҷ������õĸ�����е�ģ��
		for(int i = 0;i<10;i++){
			Thread thread = new Thread( new StringThread(string));
			thread.start();
		}
		
		//2TODO  StringBuilder���̲߳���ȫ��������ֻ�뿴�����ô�죬��future
//		StringBuilder stringBuilder = new StringBuilder();
//		for(int i = 0;i<100000;i++){
//			Thread thread = new Thread( new StringBuilderThread(stringBuilder));
//			thread.start();
//		}
		
		//3.StringBuffer�̲߳���ȫ
//		StringBuffer stringBuffer = new StringBuffer();
//		for(int i = 0;i<100000;i++){
//			Thread thread = new Thread( new StringBufferThread(stringBuffer));
//			thread.start();
//		}
	}
	
	public synchronized static void append(){
		string = string+"String";
	}
	
	public synchronized static String get(){
		return string;
	}
}

class StringThread implements Runnable{
	String str ;
	
	public StringThread(String str){
		this.str = str;
	}
	
	@Override
	public void run() {
		StringSynchronizedTest.append();
//		System.out.println(StringSynchronizedTest.get());
		System.out.println(StringSynchronizedTest.get().length()/(new String("String")).length());//���Ϊ100000���ǰ�ȫ��
	}
}

class StringBuilderThread implements Runnable{
	StringBuilder str ;
	
	public StringBuilderThread(StringBuilder str){
		this.str = str;
	}
	
	@Override
	public void run() {
		str.append("StringBuilderThread");
		System.out.println(str.length()/(new String("StringBuilderThread")).length());//���Ϊ100000���ǰ�ȫ��
	}
}

class StringBufferThread implements Runnable{
	StringBuffer str ;
	
	public StringBufferThread(StringBuffer str){
		this.str = str;
	}
	
	@Override
	public void run() {
		str.append("StringBufferThread");
		System.out.println(str.length()/(new String("StringBufferThread")).length());//���Ϊ100000���ǰ�ȫ��
	}
}