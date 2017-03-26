package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.BitOP;
import redis.clients.jedis.Jedis;

import java.util.List;

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
    public void testSetAndGet(){
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
    public void testIncrAndIncrByAndIncrbyByFloat(){
        //incr �� key �д��������ֵ��һ�� �����ڳ�ʼ��Ϊ0 ������ֵ�쳣
        //incrby �� key �������ֵ���� decrement ��
        jedis.set("incr","10");
        Assert.assertTrue(jedis.incr("incr")==11);
        Assert.assertTrue(jedis.incrBy("incr",9)==20);
        jedis.incr("new");
        Assert.assertTrue(jedis.incr("new")==2);
        Assert.assertTrue(jedis.incrBy("new",-2)==0);
        jedis.incrByFloat("new",1.5);
        Assert.assertTrue(jedis.get("new").equals("1.5"));
        Assert.assertTrue(jedis.del("incr","new")==2);
    }

    @Test
    public void testGetRange(){
        //���� key ���ַ���ֵ�����ַ������ַ����Ľ�ȡ��Χ�� start �� end ����ƫ��������(���� start �� end ����)��
        jedis.set("range","123456789");
        Assert.assertTrue(jedis.getrange("range",0,2).equals("123"));
        Assert.assertTrue(jedis.getrange("range",0,-1).equals("123456789"));
        Assert.assertTrue(jedis.del("range")==1);
    }

    @Test
    public void testGetset(){
        //������ key ��ֵ��Ϊ value �������� key �ľ�ֵ(old value)��
        jedis.set("key","old");
        Assert.assertTrue(jedis.getSet("key","new").equals("old"));
        Assert.assertTrue(jedis.get("key").equals("new"));
        Assert.assertTrue(jedis.del("key")==1);
    }

    @Test
    public void testMget(){
        //MGET key [key ...] ��������(һ������)���� key ��ֵ��
        jedis.set("a","b");
        jedis.set("b","c");
        jedis.set("c","d");
        List<String> list = jedis.mget("a","b","c","d");
        System.out.println(list.toString());
        Assert.assertTrue(jedis.del("a","b","c")==3);
    }

    @Test
    public void testMSet(){
        //MSET key value [key value ...] ͬʱ����һ������ key-value �ԡ�
        jedis.mset("a","b","b","c");
        List<String> list = jedis.mget("a","b","c");
        System.out.println(list.toString());
        Assert.assertTrue(jedis.del("a","b")==2);
    }

    @Test
    public void testMsetNX(){
        //MSETNX key value [key value ...] ͬʱ����һ������ key-value �ԣ����ҽ������и��� key �������ڡ�
//        System.out.println(jedis.msetnx("a","b","b","c"));
        Assert.assertTrue(jedis.msetnx("a","b","b","c")==1);
        Assert.assertTrue(jedis.msetnx("a","b2","b","c2")==0);
        Assert.assertTrue(jedis.del("a","b")==2);
    }

    @Test
    public void testPsetEX() throws InterruptedException {
        //PSETEX key milliseconds value �������� SETEX �������ƣ������Ժ���Ϊ��λ���� key ������ʱ�䣬�������� SETEX ��������������Ϊ��λ��
        jedis.psetex("psetex",1000,"123");
        Thread.sleep(1005);
        Assert.assertTrue(jedis.get("pseten")==null);
    }

    @Test
    public void testSetEXAndSetNX() throws InterruptedException {
        //SETEX key seconds value  ��ֵ value ������ key ������ key ������ʱ����Ϊ seconds (����Ϊ��λ)��
        //SETNX key value  �� key ��ֵ��Ϊ value �����ҽ��� key �����ڡ�
        jedis.setnx("setex","234");
        jedis.setex("setex",1,"123");
        Thread.sleep(1005);
        Assert.assertTrue(jedis.get("sexex")==null);
    }

    @Test
    public void testSetRange(){
        //SETRANGE key offset value  �� value ������д(overwrite)���� key ��������ַ���ֵ����ƫ���� offset ��ʼ��
        jedis.set("setrange","hello world");
        jedis.setrange("setrange",6,"redis");
        Assert.assertTrue(jedis.get("setrange").equals("hello redis"));
        Assert.assertTrue(jedis.del("setrange")==1);
//        System.out.println(jedis.get("a"));
    }

    @Test
    public void testStrLen(){
        //STRLEN key  ���� key ��������ַ���ֵ�ĳ��ȡ�
        jedis.psetex("len",1000,"123456");
        Assert.assertTrue(jedis.strlen("len")==6);
    }

    //--------------------------------���¶��ǹ���bit--------------------------------
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

    @Test
    public void testBitOp(){
        /*
            BITOP AND destkey key [key ...] ����һ������ key ���߼���������������浽 destkey ��
            BITOP OR destkey key [key ...] ����һ������ key ���߼��򣬲���������浽 destkey ��
            BITOP XOR destkey key [key ...] ����һ������ key ���߼���򣬲���������浽 destkey ��
            BITOP NOT destkey key ���Ը��� key ���߼��ǣ�����������浽 destkey ��
         */
        jedis.setbit("bitop1",0,true);
        jedis.setbit("bitop1",1,true);
        jedis.setbit("bitop0",0,false);
        jedis.setbit("bitop0",1,false);
        jedis.bitop(BitOP.AND,"AND","bitop1","bitop0");
//        System.out.println(jedis.getbit("AND",0));
//        System.out.println(jedis.getbit("AND",1));
        Assert.assertTrue(!jedis.getbit("AND",0));
        Assert.assertTrue(!jedis.getbit("AND",1));
        jedis.bitop(BitOP.NOT,"NOT","bitop1");
//        System.out.println(jedis.getbit("NOT",0));
//        System.out.println(jedis.getbit("NOT",1));
        Assert.assertTrue(!jedis.getbit("NOT",0));
        Assert.assertTrue(!jedis.getbit("NOT",1));
        jedis.bitop(BitOP.OR,"OR","bitop1","bitop0");
//        System.out.println(jedis.getbit("OR",0));
//        System.out.println(jedis.getbit("OR",1));
        Assert.assertTrue(jedis.getbit("OR",0));
        Assert.assertTrue(jedis.getbit("OR",1));
        jedis.bitop(BitOP.XOR,"XOR","bitop1","bitop0");
//        System.out.println(jedis.getbit("XOR",0));
//        System.out.println(jedis.getbit("XOR",1));.Assert.assertTrue(!jedis.getbit("NOT",0));
        Assert.assertTrue(jedis.getbit("XOR",0));
        Assert.assertTrue(jedis.getbit("XOR",1));
        Assert.assertTrue(jedis.del("bitop1","bitop0","AND","NOT","OR","XOR")==6);

    }

    @Test
    public void testBitField(){
        /**
         * BITFIELD ������Խ�һ�� Redis �ַ���������һ���ɶ�����λ��ɵ����飬
         * ������������д���ĳ��Ȳ�ͬ���������з��� �������������������ж��룩��
         * ���仰˵�� ͨ�������� �û�����ִ������ ����ƫ���� 1234 �ϵ� 5 λ���з��������������á���
         * ����ȡƫ���� 4567 �ϵ� 31 λ���޷����������Ȳ�����
         * ���⣬ BITFIELD ������Զ�ָ��������ִ�мӷ������ͼ���������
         * ������Щ��������ͨ���������Ƶش������ʱ���ֵ���������
        */
        System.out.print("�����ˣ��ҵ�redis��");
    }
}
