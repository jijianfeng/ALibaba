package com.jjf.redis;

/**
 * Created by jjf_lenovo on 2017/3/27.
 * ת�� http://zhaoshijie.iteye.com/blog/2073406
 */
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;
import redis.clients.jedis.*;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;
import java.util.Arrays;
import java.util.List;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class TestJedis {

    private static Jedis jedis;
    private static ShardedJedis sharding;
    private static ShardedJedisPool pool;

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
        JedisShardInfo infoa = new JedisShardInfo("182.254.213.106",6379);
        infoa.setPassword("123456");
        JedisShardInfo infob = new JedisShardInfo("182.254.213.106",6379);
        infob.setPassword("123456");
        List<JedisShardInfo> shards = Arrays.asList(
                infoa, infob); //ʹ����ͬ��ip:port,��������
        jedis = new Jedis("182.254.213.106",6379);
        jedis.auth("123456");
        sharding = new ShardedJedis(shards);
        pool = new ShardedJedisPool(new GenericObjectPoolConfig(), shards);
    }



    @AfterClass
    public static void tearDownAfterClass() throws Exception {
        jedis.disconnect();
        sharding.disconnect();
        pool.destroy();
    }

    //��ͨ���ӷ�ʽ 1000����  11.424s
    @Test
    public void test1Normal() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String result = jedis.set("n" + i, "n" + i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Simple SET: " + ((end - start)/1000.0) + " seconds");
    }

    //�����ύ 10W�� 11.38 seconds
    @Test
    public void test2Trans() {
        long start = System.currentTimeMillis();
        Transaction tx = jedis.multi();
//        tx.watch("a");
        for (int i = 0; i < 100000; i++) {
            tx.set("t" + i, "t" + i);
        }
//        System.out.println(tx.get("t1000").get()); redis�ύǰ����������
        System.out.println("����׼������");
        List<Object> results = tx.exec();
        long end = System.currentTimeMillis();
        System.out.println("Transaction SET: " + ((end - start)/1000.0) + " seconds");
    }

    //Pipeline�ܵ��첽�ύ����ͬ��֮ǰ���Ѿ������ݵ�redis�� 10W 4.824 seconds
    @Test
    public void test3Pipelined() {
        Pipeline pipeline = jedis.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("p" + i, "p" + i);
        }
        //System.out.println(pipeline.get("p1000").get());
        pipeline.sync(); //��ͬ�������һ��
//        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined SET: " + ((end - start)/1000.0) + " seconds");
    }

    //�ܵ��е������� 11.154 seconds
    @Test
    public void test4combPipelineTrans() throws InterruptedException {
        long start = System.currentTimeMillis();
        Pipeline pipeline = jedis.pipelined();
        pipeline.multi();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("" + i, "" + i);
        }
        pipeline.exec();
        List<Object> results = pipeline.syncAndReturnAll();//��ʵ�����ͬ��Ҳ���ڹرջỰ��ʱ��ͬ��3.436 seconds��ʵ����Ŀ������
        long end = System.currentTimeMillis();
        System.out.println("Pipelined transaction: " + ((end - start)/1000.0) + " seconds");
//        Thread.sleep(100000);
    }

    //�ֲ�ʽֱ��ͬ������ 11.154 seconds
    @Test
    public void test5shardNormal() {
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            String result = sharding.set("sn" + i, "n" + i);
        }
        long end = System.currentTimeMillis();
        System.out.println("Simple@Sharing SET: " + ((end - start)/1000.0) + " seconds");
    }

    //�ֲ�ʽֱ���첽���� 4.88 seconds
    @Test
    public void test6shardpipelined() {
        ShardedJedisPipeline pipeline = sharding.pipelined();
        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("sp" + i, "p" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        System.out.println("Pipelined@Sharing SET: " + ((end - start)/1000.0) + " seconds");
    }

    //�ֲ�ʽ���ӳ�ͬ������
    @Test
    public void test7shardSimplePool() {
        ShardedJedis one = pool.getResource();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 1000; i++) {
            String result = one.set("spn" + i, "n" + i);
        }
        long end = System.currentTimeMillis();
        pool.returnResource(one);
        System.out.println("Simple@Pool SET: " + ((end - start)/1000.0) + " seconds");
    }

    //�ֲ�ʽ���ӳ��첽����3.719 seconds
    @Test
    public void test8shardPipelinedPool() {
        ShardedJedis one = pool.getResource();

        ShardedJedisPipeline pipeline = one.pipelined();

        long start = System.currentTimeMillis();
        for (int i = 0; i < 100000; i++) {
            pipeline.set("sppn" + i, "n" + i);
        }
        List<Object> results = pipeline.syncAndReturnAll();
        long end = System.currentTimeMillis();
        pool.returnResource(one);
        System.out.println("Pipelined@Pool SET: " + ((end - start)/1000.0) + " seconds");
    }
}