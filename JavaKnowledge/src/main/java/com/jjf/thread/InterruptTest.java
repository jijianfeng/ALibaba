package com.jjf.thread;

/**
 * Created by jjf_lenovo on 2017/5/21.
 */
public class InterruptTest {
    public static void main(String[] args) throws InterruptedException {
        MyThread t = new MyThread("MyThread");
        t.start();
        Thread.sleep(100);// ˯��100����
        t.interrupt();//  �����жϱ�ǣ������жϽ���
    }
}
class MyThread extends Thread {
    int i = 0;

    public MyThread(String name) {
        super(name);
    }

    public void run() {
        while (!isInterrupted()) {// ��ѭ�����ȴ����ж�
            System.out.println(getName() + getId() + "ִ����" + ++i + "��");
        }
    }
}