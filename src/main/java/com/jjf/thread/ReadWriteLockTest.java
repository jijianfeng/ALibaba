package com.jjf.thread;
import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
public class ReadWriteLockTest {  
    public static void main(String[] args) {  
        final Data data = new Data();  
        for (int i = 0; i < 3; i++) {  
            new Thread(new Runnable() {  
                public void run() {  
                    for (int j = 0; j < 5; j++) {  
                        data.set(new Random().nextInt(30));  
                    }  
                }  
            }).start();  
        }         
        for (int i = 0; i < 3; i++) {  
            new Thread(new Runnable() {  
                public void run() {  
                    for (int j = 0; j < 5; j++) {  
                        data.get();  
                    }  
                }  
            }).start();  
        }  
    }  
}  
class Data {      
    private int data;// ��������      
    private ReadWriteLock rwl = new ReentrantReadWriteLock();
    public void set(int data) {  
    	rwl.writeLock().lock();
        try {  
        	System.out.println("��ʼд��");
            Thread.sleep(20);  
            this.data = data;  
            System.out.println(Thread.currentThread().getName() + "д��" + this.data);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        finally {  
            rwl.writeLock().unlock();// �ͷ�д��  
        }  
    }     
    public void  get() {  
    	rwl.readLock().lock();
        try {  
        	System.out.println("��ʼ��ȡ");
            Thread.sleep(20);  
            System.out.println(Thread.currentThread().getName() + "��ȡ" + this.data);  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        }  
        finally {  
            rwl.readLock().unlock();// �ͷ�д��  
        }  
    }  
}  