package com.jjf.thread;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
public class LockTest {  
    public static void main(String[] args) {
        final Outputter1 output1 = new Outputter1();
        final Outputter2 output2= new Outputter2();
        new Thread(() -> output1.output("zhangsan")).start();
        new Thread(() -> output1.output("lisi")).start();

        new Thread(() -> output2.output("wangwu")).start();
        new Thread(() -> output2.output("zhaoliu")).start();
    }  
}  
class  Outputter1 {  
    private Lock lock = new  ReentrantLock();// ������
    public  void output(String name)  {
        lock.lock();// �õ���
        try {  
            for(int i = 0; i < name.length(); i++) {  
                System.out.print(name.charAt(i));  
            }  
        } finally {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();// �ͷ���  
        }

    }
}

class  Outputter2 {
    private Lock lock = new  ReentrantLock();// ������
    public  void output(String name) {
        lock.lock();// �õ���
        try {
            for(int i = 0; i < name.length(); i++) {
                System.out.print(name.charAt(i));
            }
        } finally {
            try {
                Thread.sleep(10000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            lock.unlock();// �ͷ���
        }

    }
}