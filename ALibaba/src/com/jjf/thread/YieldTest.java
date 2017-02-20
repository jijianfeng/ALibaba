package com.jjf.thread;
public class YieldTest {  
    public static void main(String[] args) throws InterruptedException {  
        // �����̶߳���  
        YieldThread t1 = new YieldThread("t1");  
        YieldThread t2 = new YieldThread("t2");  
        // �����߳�  
        t1.start();  
        t2.start();  
        // ���߳�����100����  
        Thread.sleep(100);  
        // ��ֹ�߳�  
        t1.interrupt();  
        t2.interrupt();  
    }  
}  
class YieldThread extends Thread {  
    int i = 0;  
    public YieldThread(String name) {  
        super(name);  
    }  
    public void run() {  
        while(!isInterrupted()) {  
            System.out.println(getName() + "ִ����" + ++i + "��");  
            if(i % 10 == 0) {// ��i�ܶ�10����ʱ�����ò�  
                Thread.yield();  
            }  
        }  
    }  
}  