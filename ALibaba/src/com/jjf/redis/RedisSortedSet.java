package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanResult;
import redis.clients.jedis.Tuple;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by jjf_lenovo on 2017/3/31.
 */
public class RedisSortedSet {
    Jedis jedis = null;
    static final String DATASOURCE_URL = "182.254.213.106";
    static final int DATASOURCE_SORT = 6379;
    static final String DATASOURCE_PASS = "123456";
    static final int DATASOURCE_SELECT = 1;
    public RedisSortedSet() {
        //��������
        jedis = new Jedis(DATASOURCE_URL, DATASOURCE_SORT);
        jedis.auth(DATASOURCE_PASS);
        jedis.select(DATASOURCE_SELECT);
    }

    @Test
    public void testZAddAndZCardAndZScore(){
        //zadd ��һ������ member Ԫ�ؼ��� score ֵ���뵽���� key ���С�
        //zcard �������� key �Ļ�����
        //zscore �������� key �У���Ա member �� score ֵ��
        jedis.zadd("sortedSet",1111,"value:10");
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<=10;i++){
            map.put("value:"+i,Double.valueOf(i)); //����������ĸ�ֵ
        }
        jedis.zadd("sortedSet",map);
        Assert.assertTrue(jedis.zscore("sortedSet","value:10")==10);
        Assert.assertTrue(jedis.zcard("sortedSet")==11);
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZCount(){
        //zcount �������� key �У� score ֵ�� min �� max ֮��(Ĭ�ϰ��� score ֵ���� min �� max )�ĳ�Ա��������
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i));
        }
        jedis.zadd("sortedSet",map);
        Long count = jedis.zcount("sortedSet",0,9);
        System.out.println(count);
        Assert.assertTrue(count==10);
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZincrby(){
        //zincrby Ϊ���� key �ĳ�Ա member �� score ֵ�������� increment ��
        jedis.zadd("sortedSet",1111,"value:10");
        jedis.zincrby("sortedSet",-111.1,"value:10");
        Assert.assertTrue(jedis.zscore("sortedSet","value:10")==999.9);
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZRangeAndZRevRange(){
        /** zrange
         * �������� key �У�ָ�������ڵĳ�Ա��

         ���г�Ա��λ�ð� score ֵ����(��С����)������ rev���෴

         ������ͬ score ֵ�ĳ�Ա���ֵ���(lexicographical order )�����С�
         */
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Math.random()*100);
        }
        jedis.zadd("sortedSet",map);
        Set<String> set = jedis.zrange("sortedSet",0,-1); //����
        Set<String> revSet = jedis.zrevrange("sortedSet",0,-1); //�ݼ�
        for(String ss:set){
            System.out.println(ss+":"+jedis.zscore("sortedSet",ss));
        }
        System.out.println("rev----------");
        for(String ss:revSet){
            System.out.println(ss+":"+jedis.zscore("sortedSet",ss));
        }
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZRangeByScoreAndZRevRangeByScore(){
        //�������� key �У����� score ֵ���� min �� max ֮��(�������� min �� max )�ĳ�Ա��
        //���򼯳�Ա�� score ֵ����(��С����)�������С�  rev�ݼ�
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Math.random()*100);
        }
        jedis.zadd("sortedSet",map);
        Set<String> set = jedis.zrangeByScore("sortedSet",25,75);
        Set<String> revSet = jedis.zrevrangeByScore("sortedSet",75,25);
        for(String ss:set){
            System.out.println(ss+":"+jedis.zscore("sortedSet",ss));
        }
        System.out.println("rev----------");
        for(String ss:revSet){
            System.out.println(ss+":"+jedis.zscore("sortedSet",ss));
        }
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZRankAndZRevRank(){
        //zrank �������� key �г�Ա member ���������������򼯳�Ա�� score ֵ����(��С����)˳�����С���0��ʼ��rev��ʾ�Ӵ�С
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i));
        }
        jedis.zadd("sortedSet",map);
        Assert.assertTrue(jedis.zrank("sortedSet","value:5")==5);
        Assert.assertTrue(jedis.zrevrank("sortedSet","value:5")==4);
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZRem(){
        //zrem �Ƴ����� key �е�һ��������Ա�������ڵĳ�Ա�������ԡ�
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i));
        }
        jedis.zadd("sortedSet",map);
        Assert.assertTrue(jedis.zrem("sortedSet","value:0","value:1","dertcfghvjbk")==2);
        Assert.assertTrue(jedis.zcard("sortedSet")==8);
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZRemRangeByRank(){
        //zremrangeByRank �Ƴ����� key �У�ָ������(rank)�����ڵ����г�Ա��
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i));
        }
        jedis.zadd("sortedSet",map);
        jedis.zremrangeByRank("sortedSet",1,3);
        Assert.assertTrue(jedis.zcard("sortedSet")==7);
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZRemRangeByScore(){
        //zremrangeByScore �Ƴ����� key �У����� score ֵ���� min �� max ֮��(�������� min �� max )�ĳ�Ա��
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i));
        }
        jedis.zadd("sortedSet",map);
        jedis.zremrangeByScore("sortedSet",1,3);
        Assert.assertTrue(jedis.zcard("sortedSet")==7);
        Assert.assertTrue(jedis.del("sortedSet")==1);
    }

    @Test
    public void testZUnionStore(){
        //���������һ���������򼯵Ĳ��������и��� key ������������ numkeys ����ָ���������ò���(�����)���浽 destination ��
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i));
        }
        jedis.zadd("keya",map);
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i+3));
        }
        map.put("adsad",123d);
        jedis.zadd("keyb",map);
        jedis.zunionstore("destination","keya","keyb");
        Assert.assertTrue(jedis.zcard("destination")==11);
        Assert.assertTrue(jedis.del("keya","keyb","destination")==3);
    }

    @Test
    public void testZInterStore(){
        //���������һ���������򼯵Ľ��������и��� key ������������ numkeys ����ָ���������ý���(�����)���浽 destination
        Map<String,Double> map = new HashMap<String,Double>();
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i)); //����������ĸ�ֵ
        }
        jedis.zadd("keya",map);
        for(int i=0;i<10;i++){
            map.put("value:"+i,Double.valueOf(i+3)); //����������ĸ�ֵ
        }
        map.put("adsad",123d);
        jedis.zadd("keyb",map);
        jedis.zinterstore("destination","keya","keyb");
        Assert.assertTrue(jedis.zcard("destination")==10);
        Assert.assertTrue(jedis.del("keya","keyb","destination")==3);
        Assert.assertTrue(jedis.del("keya","keyb","destination")==3);
    }

    @Test
    public void testZRangeByLex(){
        //�����򼯺ϵ����г�Ա��������ͬ�ķ�ֵʱ�� ���򼯺ϵ�Ԫ�ػ���ݳ�Ա���ֵ���lexicographical ordering������������
        // �������������Է��ظ��������򼯺ϼ� key �У� ֵ���� min �� max ֮��ĳ�Ա��
        jedis.zadd("����",123d,"a");
        jedis.zadd("����",123d,"b");
        jedis.zadd("����",123d,"c");
        jedis.zadd("����",123d,"d");
        jedis.zadd("����",123d,"e");
        Set<String> set = jedis.zrangeByLex("����","(b","[e");
//        Set<String> set = jedis.zrangeByLex("����","-","+");
//        System.out.println(set.toString());
        Assert.assertTrue(set.size()==3);
        Assert.assertTrue(jedis.del("����")==1);
    }

    @Test
    public void testZLexCount(){
        //����һ�����г�Ա�ķ�ֵ����ͬ�����򼯺ϼ� key ��˵�� �������᷵�ظü����У� ��Ա���� min �� max ��Χ�ڵ�Ԫ��������
        jedis.zadd("key",123d,"a");
        jedis.zadd("key",123d,"b");
        jedis.zadd("key",123d,"c");
        jedis.zadd("key",123d,"d");
        jedis.zadd("key",123d,"e");
        Assert.assertTrue(jedis.zlexcount("key","-","+")==5);
        Assert.assertTrue(jedis.zlexcount("key","(b","[e")==3);
        Assert.assertTrue(jedis.del("key")==1);
    }

    @Test
    public void testZRemRangeByLex(){
        //����һ�����г�Ա�ķ�ֵ����ͬ�����򼯺ϼ� key ��˵�� ���������Ƴ��ü����У� ��Ա���� min �� max ��Χ�ڵ�����Ԫ�ء�
        jedis.zadd("key",123d,"a");
        jedis.zadd("key",123d,"b");
        jedis.zadd("key",123d,"c");
        jedis.zadd("key",123d,"d");
        jedis.zadd("key",123d,"e");
        Assert.assertTrue(jedis.zremrangeByLex("key","(b","[e")==3);
        Assert.assertTrue(jedis.zlexcount("key","-","+")==2);
    }

    @Test
    public void testZScan(){
        Pipeline pipeline = jedis.pipelined();
        for(int i=0;i<1000;i++){
            pipeline.zadd("key",Math.random()*100,"value:"+i);
        }
        pipeline.sync();
        int cursor = 0;
        int count = 0;
        do{
            ScanResult<Tuple> result =  jedis.zscan("key",cursor);
            cursor = Integer.valueOf(result.getStringCursor());
            for(Tuple tuple :result.getResult()){
//                System.out.print(new String(tuple.getBinaryElement())+":"+tuple.getScore());
                count++;
            }
//            System.out.println();
        }
        while (cursor!=0);
        System.out.println(count);
        Assert.assertTrue(count==1000);
        Assert.assertTrue(jedis.del("key")==1);
    }
}
