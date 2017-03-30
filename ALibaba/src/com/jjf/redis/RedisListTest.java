package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

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
    public void testLPush(){
        /**
         * ��һ������ֵ value ���뵽�б� key �ı�ͷ

         ����ж�� value ֵ����ô���� value ֵ�������ҵ�˳�����β��뵽��ͷ�� ����˵���Կ��б� mylist ִ������ LPUSH mylist a b c ���б��ֵ���� c b a �����ͬ��ԭ���Ե�ִ�� LPUSH mylist a �� LPUSH mylist b �� LPUSH mylist c �������

         ��� key �����ڣ�һ�����б�ᱻ������ִ�� LPUSH ������

         �� key ���ڵ������б�����ʱ������һ������

         ��Redis 2.4�汾��ǰ�� LPUSH �����ֻ��
         */
        //jedis.lpushx("list","a","b","c") �ᱨ����Ϊlpushx����key����
        Long lpush = jedis.lpush("list","a","b","c"); //���ص����б�ĳ���!!���ǲ�����
        Long lpushx = jedis.lpushx("list","a","b","c");
//        System.out.println(lpush);
        Assert.assertTrue(lpush==3&&lpushx==6);
        Assert.assertTrue(jedis.del("list")==1);
    }


}
