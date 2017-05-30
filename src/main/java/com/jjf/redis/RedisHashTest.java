package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanResult;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by jjf_lenovo on 2017/3/27.
 */
public class RedisHashTest {
    Jedis jedis = null;
    static final String DATASOURCE_URL = "182.254.213.106";
    static final int DATASOURCE_SORT = 6379;
    static final String DATASOURCE_PASS = "123456";
    static final int DATASOURCE_SELECT = 1;
    public RedisHashTest(){
        //��������
        jedis = new Jedis(DATASOURCE_URL,DATASOURCE_SORT);
        jedis.auth(DATASOURCE_PASS);
        jedis.select(DATASOURCE_SELECT);
    }

    @Test
    public void testHSetAndHGetAndHDel(){
        //hset ����ϣ�� key �е��� field ��ֵ��Ϊ value ��
        //hget ���ع�ϣ�� key �и����� field ��ֵ��
        //hdel ɾ����ϣ�� key �е�һ������ָ���򣬲����ڵ��򽫱����ԡ�
        Assert.assertTrue(jedis.hset("hash","key","value")==1);
        Assert.assertTrue(jedis.hget("hash","key").equals("value"));
        Assert.assertTrue(jedis.hdel("hash","key")==1);
        Assert.assertTrue(jedis.hget("hash","key")==null);
    }

    @Test
    public void testHExists(){
        //hexists �鿴��ϣ�� key �У������� field �Ƿ���ڡ�
        jedis.hset("hash","key","value");
        Assert.assertTrue(jedis.hexists("hash","key"));
        jedis.hdel("hash","key");
        Assert.assertTrue(!jedis.hexists("hash","key"));
    }

    @Test
    public void testHGetAll(){
        //���ع�ϣ�� key �У����е����ֵ��
        Pipeline pipeline = jedis.pipelined();//��ˮ��һ�����ύ
        for(int i=0;i<10;i++){
            pipeline.hset("hash","key"+i,String.valueOf(i));
        }
        pipeline.sync();
        Map<String, String> map =  jedis.hgetAll("hash");
        System.out.println(map.toString());
        Assert.assertTrue(map.size()==10);  //����һ��ס��
        Assert.assertTrue(jedis.del("hash")==1);
    }

    @Test
    public void testHIncrbyAndHincrFloat(){
        //hincrBy Ϊ��ϣ�� key �е��� field ��ֵ�������� increment������� field �����ڣ����ֵ�ȱ���ʼ��Ϊ 0 ��
        //hincrByFloat ͬ�ϣ�֧�ָ�����
        jedis.hincrBy("hash","key",5);
        Assert.assertTrue(jedis.hincrBy("hash","key",5)==10);
        Assert.assertTrue(jedis.hincrByFloat("hash","key",2.5)==12.5);
        Assert.assertTrue(jedis.del("hash")==1);
    }

    @Test
    public void testHKeys(){
        //hkeys ���ع�ϣ�� key �е�������
        //hlen ���ع�ϣ�� key �����������
        Pipeline pipeline = jedis.pipelined();//��ˮ��һ�����ύ
        for(int i=0;i<10;i++){
            pipeline.hset("hash","key"+i,String.valueOf(i));
        }
        pipeline.sync();
        Set<String> keys = jedis.hkeys("hash");
        for(String s:keys){
            System.out.println(s);//�����
        }
        Assert.assertTrue(jedis.hlen("hash")==10);
        Assert.assertTrue(keys.size()==10);
        Assert.assertTrue(jedis.del("hash")==1);
    }

    @Test
    public void testHMSetAndHMGet(){
        Map<String,String> map = new HashMap<>();
        for(int i=0;i<10;i++){
            map.put("key"+i,String.valueOf(i));
        }
        jedis.hmset("hash",map);
        Assert.assertTrue(jedis.hlen("hash")==10);
        List<String> list = jedis.hmget("hash","key1","key2","key0");
        System.out.println(list.toString());
        Assert.assertTrue(list.size()==3);
        Assert.assertTrue(jedis.del("hash")==1);
    }

    @Test
    public void testHSetNX(){
        //hsetnx ����ϣ�� key �е��� field ��ֵ����Ϊ value �����ҽ����� field �����ڡ�
        Assert.assertTrue(jedis.hsetnx("hash","key","value")==1);
        Assert.assertTrue(jedis.hsetnx("hash","key","value")==0);
        Assert.assertTrue(jedis.del("hash")==1);
    }

    @Test
    public void testHVals(){
        //hvals ���ع�ϣ�� key ���������ֵ��
        Map<String,String> map = new HashMap<>();
        for(int i=0;i<10;i++){
            map.put("key"+i,String.valueOf(i));
        }
        jedis.hmset("hash",map);
        List<String> list = jedis.hvals("hash");
        System.out.println(list.toString());
        Assert.assertTrue(list.size()==10);
        Assert.assertTrue(jedis.del("hash")==1);
    }

    @Test
    public void testHScan(){
        //HSCAN �������ڵ�����ϣ���еļ�ֵ�ԡ�
        Map<String,String> data = new HashMap<>();
        for(int i=0;i<1000;i++){
            data.put("key"+i,String.valueOf(i));
        }
        jedis.hmset("hash",data);
        ScanResult<Map.Entry<String, String>> result;// =  jedis.hscan("hash",DATASOURCE_SELECT);
        int count = 0;
        int cursor = 0;
        do {
            result = jedis.hscan("hash",cursor);
            cursor = Integer.valueOf(result.getStringCursor());
            for (Map.Entry<String, String> map : result.getResult()) {
                System.out.println(map.getKey() + ":" + map.getValue());
                count++;
            }
        }
        while(cursor!=0);
        Assert.assertTrue(count==1000);
        Assert.assertTrue(jedis.del("hash")==1);
    }

    @Test
    public void testHStrLen(){
        //���ع�ϣ�� key �У� ������� field �������ֵ���ַ������ȣ�string length����
        System.out.println("jedisû��HSTRLEN���");
    }
}
