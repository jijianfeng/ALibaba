package com.jjf.thread;

import org.junit.*;
import org.junit.Test;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Random;
import java.util.concurrent.RejectedExecutionException;

/**
 * Created by jijianfeng on 2017/6/22.
 */
public class ThreadPoolRejectTest {

    private static ThreadPoolTaskExecutor pool = new ThreadPoolTaskExecutor();

    @Before
    public void init(){
        pool.setCorePoolSize(3);
        pool.setMaxPoolSize(5);
        pool.setQueueCapacity(10);
        pool.initialize();
        long start = System.currentTimeMillis();
        System.out.println(System.currentTimeMillis()-start);
    }

    /**
     * ������TaskRejectedException���ᶪʧ�߳�����
     */
    @Test
    public void normal(){
        //  һ�����Ƚ� CorePoolSize -> CorePoolSize+QueueCapacity -> MaxPoolSize+QueueCapacity
        //���1�����߳���Ϊ3��ʱ��С�ڵ���CorePoolSize��ֱ�����С�
        //���1���������3��"end"

        //���2�����߳���Ϊ5��ʱ��С��CorePoolSize+QueueCapacity��3���߳�ֱ�����С�2���ŵ������С����п����̺߳������С�
        //���2�������3��"end"����һ�������2��"end"

        //���3�����߳���Ϊ14��ʱ��С��MaxPoolSize+QueueCapacity��3���߳�ֱ�����С�10���ŵ����У����Ҷ��⿪��1���̣߳�ֱ������Ϊ�ա�
        //���3�������4��"end"����һ�������4��"end"����һ�������4��"end"����һ�������2��"end"

        //���4�����߳���Ϊ16��ʱ�򣬴�����MaxPoolSize+QueueCapacity��3���߳�ֱ�����С�10���ŵ����У����Ҷ��⿪��2���̣߳�����1���̵߳�ִ�б��ܾ�
        //���4��rejectһ���̣߳������ٱ�ִ�У��������5��"end"����һ�������5��"end"����һ�������5��"end"��
        for(int i=0;i<16;i++){  //�ֱ���Ϊ3 5 14
            try{
                pool.execute(new LowThread());
            }
            catch(RejectedExecutionException e){
                e.printStackTrace();
            }
        }
    }

    /**
     * ���⻨��һ���̴߳���TaskRejectedException�����񲻻ᶪʧ
     */
    @Test
    public void sacrificeOneThread(){
        for(int i=0;i<16;i++){  //�ֱ���Ϊ3 5 14
            new Thread(() ->{
                for(;;){//��while true ���ܸ�
                    try{
                        pool.execute(new LowThread());
                        break;
                    }
                    catch (TaskRejectedException exception){
                        try {
                            Thread.sleep(1000);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                    continue;
                }
            }).start();
        }
    }

    @After
    public void show() throws InterruptedException {
        Thread.sleep(1000000000);
    }
}

/**
 * ���߳�
 */
class LowThread implements Runnable{
    @Override
    public void run() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("end");
    }
}