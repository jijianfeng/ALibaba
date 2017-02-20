package com.jjf.thread;

import java.util.HashMap;
import java.util.Map;

public class Test {
	
    public  static int count = 0;
    
    public static void inc(String lock) {
        //�����ӳ�1���룬ʹ�ý������
    	synchronized(lock){
	        try {
	            Thread.sleep(1);
	        }
	        catch (InterruptedException e) {
	        	
	        }
	        count++;
    	}
    	//����ÿ�����е�ֵ���п��ܲ�ͬ,����Ϊ1000
    	System.out.println("���н��:Counter.count=" + Test.count);
    }
 
    public static void main(String[] args) {
    	Map<String, String> map = new HashMap<String, String>();
        //ͬʱ����1000���̣߳�ȥ����i++���㣬����ʵ�ʽ��
    	final String lock = "lock";
        for (int i = 0; i < 1000; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Test.inc(lock);
                }
            }).start();
        }
    }
}