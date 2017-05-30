package com.jjf.thread;
public class SleepTest {  
    public static void main(String[] args) {  
        // �����������  
        Service service = new Service();  
        // �����߳�  
        SleepThread t1 = new SleepThread("t1", service);  
        SleepThread t2 = new SleepThread("t2", service);  
        // �����߳�  
        t1.start();  
        t2.start();  
    }  
      
}  
class SleepThread extends Thread {  
    private Service service;  
    public SleepThread(String name, Service service) {  
        super(name);  
        this.service = service;  
    }  
    public void run() {  
        service.calc();  
    }  
}  
class Service {  
    public synchronized void calc() {  
        System.out.println(Thread.currentThread().getName() + "׼������");  
        System.out.println(Thread.currentThread().getName() + "�о����ˣ���ʼ˯��");  
        try {  
            Thread.sleep(10000);// ˯10��  
        } catch (InterruptedException e) {  
            return;  
        }  
        System.out.println(Thread.currentThread().getName() + "˯���ˣ���ʼ����");  
        System.out.println(Thread.currentThread().getName() + "�������");  
    }  
}  