package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.BinaryClient;
import redis.clients.jedis.Jedis;

import java.util.List;

/**
 * Created by jjf_lenovo on 2017/3/28.
 */
public class RedisListTest {
    Jedis jedis = null;
    static final String DATASOURCE_URL = "182.254.213.106";
    static final int DATASOURCE_SORT = 6379;
    static final String DATASOURCE_PASS = "123456";
    static final int DATASOURCE_SELECT = 1;
    public RedisListTest(){
        //��������
        jedis = new Jedis(DATASOURCE_URL,DATASOURCE_SORT);
        jedis.auth(DATASOURCE_PASS);
        jedis.select(DATASOURCE_SELECT);
    }

    @Test
    public void testLPushAndLPushX(){
        /**
         * ��һ������ֵ value ���뵽�б� key �ı�ͷ

         ����ж�� value ֵ����ô���� value ֵ�������ҵ�˳�����β��뵽��ͷ�� ����˵���Կ��б� mylist ִ������ LPUSH mylist a b c ���б��ֵ���� c b a �����ͬ��ԭ���Ե�ִ�� LPUSH mylist a �� LPUSH mylist b �� LPUSH mylist c �������

         ��� key �����ڣ�һ�����б�ᱻ������ִ�� LPUSH ������

         �� key ���ڵ������б�����ʱ������һ������

         ��Redis 2.4�汾��ǰ�� LPUSH �����ֻ��
         */
        //jedis.lpushx("list","a","b","c") �ᱨ����Ϊlpushx����key����
        Long lpush = jedis.lpush("list","a","b","c"); //���ص����б�ĳ���!!���ǲ�����
        Assert.assertTrue(lpush==3);
        /**
         * ���ֵlpushx�ᱨ�� redis.clients.jedis.exceptions.JedisDataException: ERR wrong number of arguments for 'lpushx' command
         * lpushx��֧�ֶ������������jedis���з�����rpushxҲ�ǣ������ࡣ����
        */
        Long lpushx = jedis.lpushx("list","a");
        Assert.assertTrue(lpushx==4);
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testLLen(){
        //�����б� key �ĳ��ȡ�
        Assert.assertTrue(jedis.llen("list")==0);
        Assert.assertTrue(jedis.lpush("list","a","b","c")==3);
        Assert.assertTrue(jedis.llen("list")==3);
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testLIndex(){
        //�����б� key �У��±�Ϊ index ��Ԫ�ء�
        Assert.assertTrue(jedis.lpush("list","a","b","c")==3);
        Assert.assertTrue(jedis.lpush("list","d")==4);
//        System.out.println(jedis.lindex("list",0));
        Assert.assertTrue(jedis.lindex("list",0).equals("d"));
        Assert.assertTrue(jedis.lindex("list",-1).equals("a"));
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testLInsert(){
        //��ֵ value ���뵽�б� key ���У�λ��ֵ pivot ֮ǰ��֮��
        Assert.assertTrue(jedis.lpush("list","a","b","c")==3);
        Assert.assertTrue(jedis.lpush("list","d")==4);
        //��"insert"���뵽"list"ֵ"d"�ĺ���,"c"��ǰ�棬��������Ч��һ��
        Assert.assertTrue(jedis.linsert("list", BinaryClient.LIST_POSITION.BEFORE,"c","insert")==5);
//        Assert.assertTrue(jedis.linsert("list", BinaryClient.LIST_POSITION.AFTER,"d","insert")==5);
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testLRange(){
        //lrange �����б� key ��ָ�������ڵ�Ԫ�أ�������ƫ���� start �� stop ָ����
        Assert.assertTrue(jedis.lpush("list","a","b","c")==3);
        Assert.assertTrue(jedis.lpush("list","d")==4);
        List<String> list = jedis.lrange("list",0,-1);
        Assert.assertTrue(list.size()==4);
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testLrem(){
        /**lrem
         * ���ݲ��� count ��ֵ���Ƴ��б�������� value ��ȵ�Ԫ�ء�

         count ��ֵ���������¼��֣�

         count > 0 : �ӱ�ͷ��ʼ���β�������Ƴ��� value ��ȵ�Ԫ�أ�����Ϊ count ��
         count < 0 : �ӱ�β��ʼ���ͷ�������Ƴ��� value ��ȵ�Ԫ�أ�����Ϊ count �ľ���ֵ��
         count = 0 : �Ƴ����������� value ��ȵ�ֵ��
         */
        Assert.assertTrue(jedis.lpush("list","rem")==1);
        Assert.assertTrue(jedis.llen("list")==1);
        jedis.lrem("list",0,"rem");
        Assert.assertTrue(jedis.llen("list")==0);
    }

    @Test
    public void testLSet(){
        //lset ���б� key �±�Ϊ index ��Ԫ�ص�ֵ����Ϊ value
        Assert.assertTrue(jedis.lpush("list","a","b","c")==3);
        Assert.assertTrue(jedis.lpush("list","d")==4);
        Assert.assertTrue(jedis.lindex("list",1).equals("c"));
        Assert.assertTrue(jedis.lset("list",1,"e").equals("OK"));
        Assert.assertTrue(jedis.lindex("list",1).equals("e"));
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testLTrim(){
        //ltrim ��һ���б�����޼�(trim)������˵�����б�ֻ����ָ�������ڵ�Ԫ�أ�����ָ������֮�ڵ�Ԫ�ض�����ɾ����
        Assert.assertTrue(jedis.lpush("list","a","b","c","d","e")==5);
        Assert.assertTrue(jedis.ltrim("list",0,1).equals("OK"));
        Assert.assertTrue(jedis.lrange("list",0,1).size()==2);
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testLPopAndRPop(){
        //lpop �Ƴ��������б� key ��ͷԪ�ء�
        //rpop �Ƴ��������б� key ��βԪ�ء�
        Assert.assertTrue(jedis.lpush("list","a","b","c","d","e")==5);
        Assert.assertTrue(jedis.lpop("list").equals("e"));
        Assert.assertTrue(jedis.rpop("list").equals("a"));
        Assert.assertTrue(jedis.llen("list")==3);
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testRPopLPush(){
        /**
         * ԭ�Ӳ���
         * ���б� source �е����һ��Ԫ��(βԪ��)�����������ظ��ͻ��ˡ�
         * �� source ������Ԫ�ز��뵽�б� destination ����Ϊ destination �б�ĵ�ͷԪ�ء�
         */
        Assert.assertTrue(jedis.lpush("pop","a","b","c","d","e")==5);
        Assert.assertTrue(jedis.lpush("push","A","B","C","D","E")==5);
        jedis.rpoplpush("pop","push");
        jedis.rpoplpush("pop","push");
        jedis.rpoplpush("pop","push");
        Assert.assertTrue(jedis.rpoplpush("pop","push").equals("d"));
        Assert.assertTrue(jedis.llen("push")==9);
        Assert.assertTrue(jedis.del("pop","push")==2);
    }

    @Test
    public void testRPushAndRPushX(){
        Assert.assertTrue(jedis.rpush("list","a","b","c","d","e")==5);//��lpush˳���෴
        Assert.assertTrue(jedis.rpushx("list","12333")==6);
        Assert.assertTrue(jedis.lindex("list",0).equals("a"));//���Ȳ������ǰ��
        Assert.assertTrue(jedis.del("list")==1);
    }

    @Test
    public void testBLPopAndBRPop(){
        //���� LPOP ����������汾���������б���û���κ�Ԫ�ؿɹ�������ʱ�����ӽ��� BLPOP ����������ֱ���ȴ���ʱ���ֿɵ���Ԫ��Ϊֹ��
        /**
         * ��ͬ��key������ͻ���ͬʱ����

         ��ͬ�� key ���Ա�����ͻ���ͬʱ������

         ��ͬ�Ŀͻ��˱��Ž�һ�������У������������ȷ���(first-BLPOP��first-served)��˳��Ϊ key ִ�� BLPOP ���

         ��MULTI/EXEC�����е�BLPOP

         BLPOP ����������ˮ��(pipline,�����ط��Ͷ������������ظ�)������������ MULTI / EXEC �鵱��û�����塣��Ϊ��Ҫ�������������������Ա�֤��ִ��ʱ��ԭ���ԣ�����Ϊ��ֹ�������ͻ���ִ�� LPUSH �� RPUSH ���

         ��ˣ�һ���������� MULTI / EXEC ���ڵ� BLPOP �����Ϊ���ֵþ��� LPOP һ�����Կ��б��� nil ���Էǿ��б����б�Ԫ�أ��������κ�����������
         */
//        jedis.blpop(100,"list");//��ʵ����lpop�������汾�������ֶ�������ԣ���λ����
//
//        jedis.brpop(100,"list");//��ʵ����rpop�������汾�������ֶ�������ԣ���λ����
//
//        jedis.brpoplpush("pop","push",100);//rpoplpush�������ֶ�������ԣ���λ����
    }
}
