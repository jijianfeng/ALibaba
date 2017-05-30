package com.jjf.thread;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * Created by jjf_lenovo on 2017/5/22.
 */
public class ArrayBlockQueueTest {
    /** װ���������ӣ���СΪ5 */
    private  BlockingQueue<Integer> eggs = new ArrayBlockingQueue<Integer>(5); //���� �����ݣ������ޣ������������̣߳�ֱ����ȡ��

    /** �ż��� */
    public void putEgg(Integer egg) {
        try {
            Thread.sleep(1000);
            eggs.put(egg);// ������ĩβ��һ������������������ˣ���ǰ�߳�����
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ���������׼ȷ����Ϊ��put��������һ��ԭ�Ӳ���,���ǿ�������ţ��϶�û����
        System.out.println("���뼦��"+egg);
    }

    /** ȡ���� */
    public Integer getEgg() {
        Integer egg = null;
        try {
            egg = eggs.take();// �����ӿ�ʼȡһ��������������ӿ��ˣ���ǰ�߳�����
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        // ���������׼ȷ����Ϊ��take��������һ��ԭ�Ӳ���,���ǿ�������ţ��϶�û����
        System.out.println("�õ�����"+egg);
        return egg;
    }

    /** �ż����߳� */
    static class AddThread extends Thread {
        private ArrayBlockQueueTest plate;
        private Integer egg ;

        public AddThread(ArrayBlockQueueTest plate,Integer egg) {
            this.plate = plate;
            this.egg = egg;
        }

        public void run() {
            plate.putEgg(egg);
        }
    }

    /** ȡ�����߳� */
    static class GetThread extends Thread {
        private ArrayBlockQueueTest plate;

        public GetThread(ArrayBlockQueueTest plate) {
            this.plate = plate;
        }

        public void run() {
            plate.getEgg();
        }
    }

    public static void main(String[] args) {
        ArrayBlockQueueTest plate = new ArrayBlockQueueTest();
        // ������10���ż����߳�
        for(int i = 0; i < 9; i++) {
            new Thread(new AddThread(plate,i)).start();
        }
        // ������10��ȡ�����߳�
//        for(int i = 0; i < 10; i++) {
//            new Thread(new GetThread(plate)).start();
//        }
    }
}
