package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.Pipeline;
import redis.clients.jedis.ScanResult;

import java.util.List;
import java.util.Set;

/**
 * Created by jjf_lenovo on 2017/3/31.
 */
public class RedisSetTest {
    Jedis jedis = null;
    static final String DATASOURCE_URL = "182.254.213.106";
    static final int DATASOURCE_SORT = 6379;
    static final String DATASOURCE_PASS = "123456";
    static final int DATASOURCE_SELECT = 1;

    public RedisSetTest(){
        //��������
        jedis = new Jedis(DATASOURCE_URL,DATASOURCE_SORT);
        jedis.auth(DATASOURCE_PASS);
        jedis.select(DATASOURCE_SELECT);
    }

    @Test
    public void testSAdd(){
        //sadd ��һ������ member Ԫ�ؼ��뵽���� key ���У��Ѿ������ڼ��ϵ� member Ԫ�ؽ������ԡ�
        Assert.assertTrue(jedis.sadd("set","a")==1);
        Assert.assertTrue(jedis.sadd("set","a","b","c")==2);
        Assert.assertTrue(jedis.del("set")==1);
    }

    @Test
    public void testSCard(){
        //scard ���ؼ��� key �Ļ���(������Ԫ�ص�����)��
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.scard("set".getBytes())==3);
        Assert.assertTrue(jedis.del("set")==1);
    }

    @Test
    public void testSDiff(){
        //sdiff ����һ�����ϵ�ȫ����Ա���ü��������и�������֮��Ĳ��
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sadd("SET","A","B","C","a","b","c")==6);
        Set<String> a = jedis.sdiff("set","SET");//���ҳ�set�У�SETû�е�Ԫ�أ��༶�Ļ���һ������
        Set<String> b = jedis.sdiff("SET","set");//���ҳ�SET�У�setû�е�Ԫ�أ��༶�Ļ���һ������
        System.out.println(a.toString()+":::"+b.toString());
        Assert.assertTrue(jedis.del("set","SET")==2);
    }

    @Test
    public void testSDiffStore(){
        //sdiffstore �����������ú� SDIFF ���ƣ�������������浽 destination ���ϣ������Ǽ򵥵ط��ؽ������
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sadd("SET","A","B","C","a","b","c")==6);
        Assert.assertTrue(jedis.sdiffstore("store","SET","set")==3);
        Assert.assertTrue(jedis.sdiffstore("store","set","SET")==0);
        Assert.assertTrue(jedis.del("set","SET")==2);
    }

    @Test
    public void testSInter(){
        //sinter ����һ�����ϵ�ȫ����Ա���ü��������и������ϵĽ�����
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sadd("SET","A","B","C","a","b","c")==6);
        Set<String> set = jedis.sinter("set","SET");
        System.out.println(set.toString());
        Assert.assertTrue(set.size()==3);
        Assert.assertTrue(jedis.del("set","SET")==2);
    }

    @Test
    public void testSInterStore(){
        //sinterstore ������������� SINTER ���������������浽 destination ���ϣ������Ǽ򵥵ط��ؽ������
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sadd("SET","A","B","C","a","b","c")==6);
        Assert.assertTrue(jedis.sinterstore("store","set","SET")==3);
        Assert.assertTrue(jedis.del("set","SET","store")==3);
    }

    @Test
    public void testSIsMember(){
        //sismember �ж� member Ԫ���Ƿ񼯺� key �ĳ�Ա��
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sismember("set","b"));
        Assert.assertTrue(jedis.del("set")==1);
    }

    @Test
    public void testSMembers(){
        //smembers ���ؼ��� key �е����г�Ա�� �����ڵ� key ����Ϊ�ռ��ϡ�
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Set<String> set = jedis.smembers("set");
        System.out.println(set.toString());
        Assert.assertTrue(set.size()==3);
        Assert.assertTrue(jedis.del("set")==1);
    }

    @Test
    public void testSMove(){
        //�� member Ԫ�ش� source �����ƶ��� destination ���ϡ� (ԭ�Ӳ���)
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sadd("SET","A","B","C","a","b","c")==6);
        Assert.assertTrue(jedis.smove("set","SET","a")==1);
        Set<String> a = jedis.smembers("set");
        Set<String> b = jedis.smembers("SET");
        System.out.println(a.toString());
        System.out.println(b.toString());
        Assert.assertTrue(a.size()==2);
        Assert.assertTrue(b.size()==6);//��Ϊԭ���ʹ���a �����Ի���6��
        Assert.assertTrue(jedis.del("set","SET")==2);
    }

    @Test
    public void testSPop(){
        //spop �Ƴ������ؼ����е�һ�����Ԫ�ء�
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        System.out.println(jedis.spop("set"));
        Set<String> a = jedis.smembers("set");
        Assert.assertTrue(a.size()==2);
        Assert.assertTrue(jedis.del("set")==1);

    }

    @Test
    public void testSRandMember(){
        /**
         * �������ִ��ʱ��ֻ�ṩ�� key ��������ô���ؼ����е�һ�����Ԫ�ء�

         �� Redis 2.6 �汾��ʼ�� SRANDMEMBER ������ܿ�ѡ�� count ������

         ��� count Ϊ��������С�ڼ��ϻ�������ô�����һ������ count ��Ԫ�ص����飬�����е�Ԫ�ظ�����ͬ����� count ���ڵ��ڼ��ϻ�������ô�����������ϡ�
         ��� count Ϊ��������ô�����һ�����飬�����е�Ԫ�ؿ��ܻ��ظ����ֶ�Σ�������ĳ���Ϊ count �ľ���ֵ��
         */
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        System.out.println(jedis.srandmember("set"));//��ͬ��spop��srandmember���Ƴ�
        Set<String> a = jedis.smembers("set");
        Assert.assertTrue(a.size()==3);

        //���Զ�������
        List<String> lista = jedis.srandmember("set",2);//�����������ظ��������
        System.out.println("lista:"+lista.toString());
        List<String> listb = jedis.srandmember("set",5);//5>3 ����ȫ������
        System.out.println("listb:"+listb.toString());
        List<String> listc = jedis.srandmember("set",-2); //����|-2|�������ظ�������
        System.out.println("listc:"+listc.toString());
        List<String> listd = jedis.srandmember("set",-6); //����|-6|�������ظ�������
        System.out.println("listd:"+listd.toString());
        Assert.assertTrue(jedis.del("set")==1);
    }

    @Test
    public void testSRem(){
        //srem �Ƴ����� key �е�һ������ member Ԫ�أ������ڵ� member Ԫ�ػᱻ���ԡ�
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.srem("set","a","d")==1);
        Assert.assertTrue(jedis.del("set")==1);
    }

    @Test
    public void testSunion(){
        //sunion ����һ�����ϵ�ȫ����Ա���ü��������и������ϵĲ�����
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sadd("SET","A","B","C","a","b","c")==6);
        Set<String> set = jedis.sunion("set","SET");
        System.out.println(set.toString());
        Assert.assertTrue(set.size()==6);
        Assert.assertTrue(jedis.del("set","SET")==2);
    }

    @Test
    public void testSunionStore(){
        //sunion ����һ�����ϵ�ȫ����Ա���ü��������и������ϵĲ�����
        Assert.assertTrue(jedis.sadd("set","a","b","c")==3);
        Assert.assertTrue(jedis.sadd("SET","A","B","C","a","b","c")==6);
        Assert.assertTrue(jedis.sunionstore("store","set","SET")==6);
        Assert.assertTrue(jedis.del("set","SET","store")==3);
    }

    @Test
    public void testSScan(){
        Pipeline pipeline = jedis.pipelined();
        for(int i=0;i<1000;i++){
            pipeline.sadd("set",String.valueOf(i));
        }
        pipeline.sync();
        ScanResult<String> result ;//=  jedis.sscan("set",0);
//        System.out.println(result.getResult().size()+"::"+result.getStringCursor());
//        System.out.println(result.getResult().toString());
        int count = 0;
        int cursor = 0;
        do{
//            System.out.println(result.getCursor());
            result = jedis.sscan("set",cursor);
            cursor = Integer.valueOf(result.getStringCursor());
            for (String ss : result.getResult()) {
                count++;
                System.out.print("<"+count+":" + ss + ">");
            }
            System.out.println();
        }
        while(cursor!=0);
        System.out.println(count);
        Assert.assertTrue(count==1000);
        Assert.assertTrue(jedis.del("set","SET","store")==1);
    }
}
