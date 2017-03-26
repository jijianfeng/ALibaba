package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;

/**
 * Created by jjf_lenovo on 2017/3/22.
 */
public class RedisStringTest {
    Jedis jedis = null;
    static final String DATASOURCE_URL = "182.254.213.106";
    static final int DATASOURCE_SORT = 6379;
    static final String DATASOURCE_PASS = "123456";
    static final int DATASOURCE_SELECT = 1;
    public RedisStringTest(){
        //��������
        jedis = new Jedis(DATASOURCE_URL,DATASOURCE_SORT);
        jedis.auth(DATASOURCE_PASS);
        jedis.select(DATASOURCE_SELECT);
    }

    @Test
    public void testSetGet(){
        //String set get
        jedis.set("hello","world");
        Assert.assertTrue(jedis.get("hello").equals("world"));
        Assert.assertTrue(jedis.del("hello")==1);
    }

    @Test
    public void testAppend(){
        //��� key �Ѿ����ڲ�����һ���ַ����� APPEND ��� value ׷�ӵ� key ԭ����ֵ��ĩβ��
        //��� key �����ڣ� APPEND �ͼ򵥵ؽ����� key ��Ϊ value ������ִ�� SET key value һ����
        jedis.append("append","append");
        jedis.append("append","append");
        Assert.assertTrue(jedis.get("append").equals("appendappend"));
        Assert.assertTrue(jedis.del("append")==1);
    }

    @Test
    public void testDecrAndDecrBy(){
        //decr �� key �д��������ֵ��һ�� �����ڳ�ʼ��Ϊ0 ������ֵ�쳣
        //decrby �� key �������ֵ��ȥ���� decrement ��
        jedis.set("decr","10");
        jedis.set("decrerror","decrerror");
        Assert.assertTrue(jedis.decr("decr")==9);
        Assert.assertTrue(jedis.decrBy("decr",9)==0);
        jedis.decr("new");
        Assert.assertTrue(jedis.decr("new")==-2);
        Assert.assertTrue(jedis.decrBy("new",-2)==0);
//        try {
//            jedis.decr("decrerror");
//        }
//        catch(Exception exception){
//            System.out.println("decr��ʼ������ֵֵΪ���쳣");
//        }
        Assert.assertTrue(jedis.del("decr","decrerror","new")==3);
    }

    @Test
    public void testBitSetGet(){
        //�� key ��������ַ���ֵ�����û����ָ��ƫ�����ϵ�λ(bit)��
        //�ַ����������չ(grown)��ȷ�������Խ� value ������ָ����ƫ�����ϡ����ַ���ֵ������չʱ���հ�λ���� 0 ��䡣
        //offset ����������ڻ���� 0 ��С�� 2^32 (bit ӳ�䱻������ 512 MB ֮��)��
        jedis.setbit("bit",1,true);// ==jedis.setbit("bit",1,"1");
        jedis.setbit("bit",2,false);
        jedis.setbit("bit",123123,false);
        System.out.println(jedis.get("bit"));
        System.out.println(jedis.getbit("bit",1));
        System.out.println(jedis.getbit("bit",2));
        System.out.println(jedis.getbit("bit",123)); //�հ�λ���� 0 (false)��䡣
        Assert.assertTrue(!jedis.getbit("bit",123));
        Assert.assertTrue(jedis.getbit("bit",1));
        Assert.assertTrue(!jedis.getbit("bit",2));
        Assert.assertTrue(jedis.del("bit")==1);
    }

    @Test
    public void testBitcount(){
        //bitcount ��������ַ����У�������Ϊ 1 �ı���λ��������
        for(int i=0;i<10;i++){
            if(i%2==0){
                jedis.setbit("bit",i*8,true); //��λ
            }
            else{
                jedis.setbit("bit",i*8,false);
            }
        }
        Assert.assertTrue(jedis.bitcount("bit")==5);
        Assert.assertTrue(jedis.bitcount("bit",0,-1)==5);
//        System.out.println(jedis.bitcount("bit"));
//        System.out.println(jedis.bitcount("bit",0,4));
        Assert.assertTrue(jedis.bitcount("bit",0,4)==3);
        Assert.assertTrue(jedis.del("bit")==1);
    }
}
