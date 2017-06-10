package com.jjf.lambda;

/**
 * Created by jijianfeng on 2017/6/10.
 */
public class ThreadTest {
    /**
     * ��1����lambda���ʽʵ��Runnable
     �ҿ�ʼʹ��Java 8ʱ���������ľ���ʹ��lambda���ʽ�滻�����࣬
     ��ʵ��Runnable�ӿ�������������ʾ������һ��Java 8֮ǰ��runnableʵ�ַ�����
     ��Ҫ4�д��룬��ʹ��lambda���ʽֻ��Ҫһ�д��롣��������������ʲô�أ��Ǿ�����() -> {}�������������������ࡣ
     * @param args
     */
    public static void main(String[] args){
        // Java 8֮ǰ��
        new Thread(new Runnable() {
            @Override
            public void run() {
                System.out.println("Before Java8, too much code for too little to do");
            }
        }).start();

        //Java 8��ʽ��
        new Thread( () -> System.out.println("In Java8, Lambda expression rocks !!") ).start();
    }

}
