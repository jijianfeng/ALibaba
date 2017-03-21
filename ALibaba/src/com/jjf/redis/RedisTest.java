package com.jjf.redis;

import org.junit.Assert;
import org.junit.Test;
import redis.clients.jedis.Jedis;
import redis.clients.jedis.ScanResult;

import java.util.List;

/**
 * ��˿��Ѷ��װ�˸�redis
 * @author jjf_lenovo
 * 2017��3��12��22:13:47
 */
public class RedisTest {
    Jedis jedis = null;
    public RedisTest(){
        //��������
        jedis = new Jedis("182.254.213.106",6379);
        jedis.auth("123456");
        jedis.select(1);
    }
    @Test
	public void testSetDel()
    {
		//set del  ɾ��������һ������key ��
		jedis.set("a","123");
        jedis.set("b","123");
        jedis.set("c","123");
        Assert.assertTrue(jedis.del("aaa")==0);
        Assert.assertTrue(jedis.del("a")==1);
        Assert.assertTrue(jedis.del("b","c")==2);
    }

    @Test
    public void testDumpRestore(){
        //dump restore   ���л����� key �������ر����л���ֵ��ʹ�� RESTORE ������Խ����ֵ�����л�Ϊ Redis ����
		jedis.set("dump","dump","NX","EX",10);//  NX/XX-����set/������set   EX/PX-seconds/milliseconds 10�����
		jedis.hset("hashdump","hash","dump");
		byte[] ss = jedis.dump("dump");
		byte[] hashss = jedis.dump("hashdump");
		System.out.println(ss.toString()+":::"+hashss);
        //���� ttl �Ժ���Ϊ��λΪ key ��������ʱ�䣻��� ttl Ϊ 0 ����ô����������ʱ�䡣
        System.out.println(jedis.restore("dump-code",0,ss)+":::"+jedis.restore(hashss,10000,hashss));
        System.out.println(ss.toString()+":::"+hashss);
        Assert.assertTrue(jedis.get("dump-code").equals("dump"));
        Assert.assertTrue(jedis.hget("hashdump","hash").equals("dump"));
        Assert.assertTrue(jedis.del("dump-code")==1);
        Assert.assertTrue(jedis.del("hashdump")==1);
    }

    @Test
    public void testExistsExpireExpireat(){
        //Exists  Expire ExpiteAt  ����ʱ��
        jedis.set("exist","exist");
        jedis.set("existAt","existAt");
        Assert.assertTrue(jedis.exists("exist"));
        Assert.assertTrue(jedis.expire("exist",1)==1);
        // 1.����Ҫ���ǵ���������ʱ���request��ʱ�䣬2.��������Unix timestamp�����Ǻ���
        Assert.assertTrue(jedis.expireAt("existAt",System.currentTimeMillis()/1000+5)==1);
        System.out.println(System.currentTimeMillis()/1000+5);
        try {
            Thread.sleep(5050);
        } catch (InterruptedException e) {
            Assert.fail("�߳������쳣");
        }
        System.out.println(System.currentTimeMillis()/1000);
        Assert.assertTrue(!jedis.exists("exist"));
        Assert.assertTrue(!jedis.exists("existAt"));
    }

    @Test
    public void testKeys(){
        //Keys   �������з��ϸ���ģʽ pattern �� key ��
        jedis.set("ont","one","NX","EX",10);
        jedis.set("two","one","NX","EX",10);
        jedis.set("three","one","NX","EX",10);
        jedis.set("four","one","NX","EX",10);
        Assert.assertTrue(jedis.keys("*").size()==4);
        Assert.assertTrue(jedis.keys("t*").size()==2);
        Assert.assertTrue(jedis.keys("t??").size()==1);
        Assert.assertTrue(jedis.keys("f[ab]ur").size()==0);
        Assert.assertTrue(jedis.keys("f[ou]ur").size()==1);
    }

    @Test
    public void testMigrate(){
        //MIGRATE �� key ԭ���Եشӵ�ǰʵ�����͵�Ŀ��ʵ����ָ�����ݿ���
        // ��һ�����ͳɹ���key ��֤�������Ŀ��ʵ���ϣ�����ǰʵ���ϵ� key �ᱻɾ����
        //�Ʒ������ڴ����ޣ��Ͳ�������ʵ����
    }

    @Test
    public void testMove(){
        //MOVE ͬһ��redis��ͬ��֮��ġ����С�
        jedis.select(1);
        jedis.set("move","123");
        jedis.expire("move",5);//���ǻ���Ч
        Assert.assertTrue(jedis.move("move",2)==1);
    }

    @Test
    public void testObject(){
        //OBJECT ����������ڲ��쿴���� key �� Redis ����
        jedis.set("object","object111111111111111111111111111111111111111111111111111111111","NX","EX",10);
        String ss = jedis.get("object");
        System.out.println(jedis.objectEncoding("object"));//�ڲ���ʾ
        System.out.println(jedis.objectIdletime("object"));//�Դ��������Ŀ���ʱ��
        System.out.println(jedis.objectRefcount("object"));//���ô���
    }

    @Test
    public void testPersist(){
        //Expire ָ��ʱ���ɾ��  PERSIST  �Ƴ����� key ������ʱ��
        jedis.set("PERSIST","PERSIST","NX","EX",2);
        jedis.persist("PERSIST");
        try {
            Thread.sleep(2200);
        } catch (InterruptedException e) {
            Assert.fail("���߳������쳣");
        }
        Assert.assertTrue(jedis.get("PERSIST")!=null&&jedis.get("PERSIST").equals("PERSIST"));
        Assert.assertTrue(jedis.del("PERSIST")==1);
    }

    @Test
    public void testPexpirePexpireAt(){
        //Pexpire  PexpireAt  ���뼶���expire
        jedis.set("exist","exist");
        jedis.set("existAt","existAt");
        Assert.assertTrue(jedis.pexpire("exist",1000)==1);
        // 1.����Ҫ���ǵ���������ʱ���request��ʱ�䣬2.��������Unix timestamp�����Ǻ���
        Assert.assertTrue(jedis.pexpireAt("existAt",System.currentTimeMillis()+5000)==1);
        System.out.println(System.currentTimeMillis()+5000);
        try {
            Thread.sleep(5050);
        } catch (InterruptedException e) {
            Assert.fail("�߳������쳣");
        }
        System.out.println(System.currentTimeMillis());
        Assert.assertTrue(!jedis.exists("exist"));
        Assert.assertTrue(!jedis.exists("existAt"));
    }

    @Test
    public void testPttlTtl(){
        //ttl �鿴����ʣ��ʱ��  pttl ���뼶
        jedis.set("ttl","ttl","NX","EX",10);
        System.out.println(jedis.ttl("ttl"));
        System.out.println(jedis.pttl("ttl"));
    }

    @Test
    public void testRandomKey(){
        //RANDOMKEY  �ӵ�ǰ���ݿ����������(��ɾ��)һ�� key ��
//        System.setProperty("http.proxySet", "true");
//        System.setProperty("http.proxyHost", "127.0.0.1");
//        System.setProperty("http.proxyPort", "8888");
        //TODO ��û�п���ץ�������ж����ٶ�
        for(int i=0;i<100;i++){
            jedis.set("random"+i,String.valueOf(Math.random()),"NX","EX",10);
        }
        for(int i=0;i<10;i++){
            System.out.println(jedis.get(jedis.randomKey()));
        }
    }

    @Test
    public void testRename(){
        //rename �� key ����Ϊ newkey
        //renamenx ���ҽ��� newkey ������ʱ���� key ����Ϊ newkey ��
        jedis.set("one","1","NX","EX",10);
        jedis.set("two","2","NX","EX",10);
        Assert.assertTrue(jedis.exists("one"));
        jedis.renamenx("one","three");
        Assert.assertTrue(!jedis.exists("one"));
        Assert.assertTrue(jedis.exists("three"));
        jedis.rename("two","three"); //���rename���¾�key�����ڣ��Ǿͱ�����key��ֵ
        Assert.assertTrue(!jedis.exists("two"));
        Assert.assertTrue(jedis.get("three").equals("2"));
    }

    @Test
    public void testSort(){
        //sort ���ػ򱣴�����б����ϡ����򼯺� key �о��������Ԫ�ء�
        for(int i=0;i<100;i++){
            jedis.lpush("sort",String.valueOf((int)(Math.random()*100)));
        }
        List list = jedis.sort("sort");
        System.out.println("�����:"+list.toString());
        Assert.assertTrue(jedis.sort("sort","dstkey")==100);
        jedis.del("sort","dstkey");
    }

    @Test
    public void testType(){
        //type ��������
        jedis.set("a","b");
        Assert.assertTrue(jedis.type("a").equals("string"));
        jedis.del("a");
    }

    @Test
    public void testScan(){
        /**
         SCAN �������ڵ�����ǰ���ݿ��е����ݿ����
         SSCAN �������ڵ������ϼ��е�Ԫ�ء�
         HSCAN �������ڵ�����ϣ���еļ�ֵ�ԡ�
         ZSCAN �������ڵ������򼯺��е�Ԫ�أ�����Ԫ�س�Ա��Ԫ�ط�ֵ����
         */
        //TODO �����
        ScanResult<String> result =  jedis.scan(0);
        for(String key :result.getResult()){
            System.out.println(key);
        }
    }
}
