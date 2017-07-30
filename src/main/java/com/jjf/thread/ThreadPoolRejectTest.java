package com.jjf.thread;

import org.junit.*;
import org.junit.Test;
import org.springframework.core.task.TaskRejectedException;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.Random;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

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
        //Ĭ�ϵ�ֱ���ܳ�Reject�쳣
//        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //���̳߳��е�������������߳���ʱ������ִ�е�ǰ�����񣬽��ɵ������߳���ִ������,���������̡߳�
        pool.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //����DiscardOldestPolicy() �������һ�������DiscardPolicy()�������κβ���
        pool.initialize();
    }

    /**
     * ������TaskRejectedException������ʱ�ᶪʧ�߳�����
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
        System.out.println("i am ok");
    }

    /**
     * ���⻨��һ���̴߳���TaskRejectedException�����񲻻ᶪʧ,�������߳�
     */
    @Test
    public void safeUnBlockThreadPool(){
        UnBlockExecute(()->{
            for(int i=0;i<16;i++){  //�ֱ���Ϊ3 5 14
                pool.execute(new LowThread());
            }
        });
        System.out.println("i am ok");
    }

    @Test
    public void safeBlockThreadPool(){
        BlockExecute(()->{
            for(int i=0;i<16;i++){  //�ֱ���Ϊ3 5 14
                pool.execute(new LowThread());
            }
        });
        System.out.println("i am ok");
    }

    @After
    public void show() throws InterruptedException {
        Thread.sleep(1000000000);
    }

    /**
     * ������ִ�У��ʺ�����ִ࣬���ٶȿ�����
     * tip:�����������ָ���̳߳��л�ȡ�̱߳�����������ִ������
     */
    private void BlockExecute(Runnable runnable){
        pool.execute(runnable);
    }

    /**
     * ������ִ�У��ʺ�����ʱ�䳤������������������
     * tip:�����������ָ���̳߳��л�ȡ�̱߳�����������ִ������
     * @param runnable
     */
    private void UnBlockExecute(Runnable runnable){
        new Thread(runnable).start();
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
        System.out.println(Thread.currentThread().getId()+":end");
    }
}