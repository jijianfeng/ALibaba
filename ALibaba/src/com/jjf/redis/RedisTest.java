package com.jjf.redis;
import redis.clients.jedis.Jedis;
/**
 * ��˿��Ѷ��װ�˸�redis �����ӷ�ʽ����ȷ��ʹ������Ӧ���ܱ������ƽ��
 * @author jjf_lenovo
 * 2017��3��12��22:13:47
 */
public class RedisTest {
	public static void main( String[] args )
    {
    	Jedis jedis = new Jedis("182.254.213.106",6379);
    	jedis.auth("123456");
    	String hello = jedis.get("hello");
    	jedis.append("hello", "123123");
    	//�鿴�����Ƿ�����
    	System.out.println("Server is running: "+jedis.ping());
    	System.out.println(hello);
    }
}
