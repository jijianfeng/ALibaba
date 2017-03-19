package com.jjf.redis;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import redis.clients.jedis.Jedis;
/**
 * ��˿��Ѷ��װ�˸�redis
 * @author jjf_lenovo
 * 2017��3��12��22:13:47
 */
public class RedisTest {
	public static void main( String[] args )
    {
    	Jedis jedis = new Jedis("182.254.213.106",6379);
    	jedis.auth("123456");
    	System.out.println("Server is running: "+jedis.ping());
    	HashMap<String,String> map = new HashMap<String,String>();
    	map.put("Yahoo", "???");
    	map.put("360", "336600");
    	System.out.println(jedis.hset("hello", "Tencent","BOOM!" ));
    	System.out.println(jedis.hmset("hello", map));
    	//�鿴�����Ƿ�����
    }
}
/*1.��value����������

     exists(key)��ȷ��һ��key�Ƿ����

     del(key)��ɾ��һ��key

     type(key)������ֵ������

     keys(pattern)�������������pattern������key

     randomkey���������key�ռ��һ��key

     rename(oldname, newname)����key��oldname������Ϊnewname����newname������ɾ��newname��ʾ��key

     dbsize�����ص�ǰ���ݿ���key����Ŀ

     expire���趨һ��key�Ļʱ�䣨s��

     ttl�����һ��key�Ļʱ��

     select(index)����������ѯ

     move(key, dbindex)������ǰ���ݿ��е�keyת�Ƶ���dbindex���������ݿ�

     flushdb��ɾ����ǰѡ�����ݿ��е�����key

     flushall��ɾ���������ݿ��е�����key

2.��String����������

     set(key, value)�������ݿ�������Ϊkey��string����ֵvalue

     get(key)���������ݿ�������Ϊkey��string��value

     getset(key, value)��������Ϊkey��string������һ�ε�value

     mget(key1, key2,��, key N)�����ؿ��ж��string�����ǵ�����Ϊkey1��key2������value

     setnx(key, value)���������������Ϊkey��string������������string������Ϊkey��ֵΪvalue

     setex(key, time, value)����������string������Ϊkey��ֵΪvalue��ͬʱ���趨����ʱ��time

     mset(key1, value1, key2, value2,��key N, value N)��ͬʱ�����string��ֵ������Ϊkey i��string��ֵvalue i

     msetnx(key1, value1, key2, value2,��key N, value N)�������������Ϊkey i��string�������ڣ�����������string������key i��ֵΪvalue i

     incr(key)������Ϊkey��string��1����

     incrby(key, integer)������Ϊkey��string����integer

     decr(key)������Ϊkey��string��1����

     decrby(key, integer)������Ϊkey��string����integer

     append(key, value)������Ϊkey��string��ֵ����value

     substr(key, start, end)����������Ϊkey��string��value���Ӵ�

3.��List����������

     rpush(key, value)��������Ϊkey��listβ���һ��ֵΪvalue��Ԫ��

     lpush(key, value)��������Ϊkey��listͷ���һ��ֵΪvalue�� Ԫ��

     llen(key)����������Ϊkey��list�ĳ���

     lrange(key, start, end)����������Ϊkey��list��start��end֮���Ԫ�أ��±��0��ʼ����ͬ��

     ltrim(key, start, end)����ȡ����Ϊkey��list������start��end֮���Ԫ��

     lindex(key, index)����������Ϊkey��list��indexλ�õ�Ԫ��

     lset(key, index, value)��������Ϊkey��list��indexλ�õ�Ԫ�ظ�ֵΪvalue

     lrem(key, count, value)��ɾ��count������Ϊkey��list��ֵΪvalue��Ԫ�ء�countΪ0��ɾ������ֵΪvalue��Ԫ�أ�count>0      ��ͷ��βɾ��count��ֵΪvalue��Ԫ�أ�count<0��β��ͷɾ��|count|��ֵΪvalue��Ԫ�ء�

     lpop(key)�����ز�ɾ������Ϊkey��list�е���Ԫ��

     rpop(key)�����ز�ɾ������Ϊkey��list�е�βԪ��

     blpop(key1, key2,�� key N, timeout)��lpop �����block�汾������timeoutΪ0ʱ������������Ϊkey i��list�����ڻ��listΪ�գ��������������� timeout>0���������������ʱ���ȴ�timeout�룬�������û�н�������key i+1��ʼ��listִ��pop������

     brpop(key1, key2,�� key N, timeout)��rpop��block�汾���ο���һ���

     rpoplpush(srckey, dstkey)�����ز�ɾ������Ϊsrckey��list��βԪ�أ�������Ԫ����ӵ�����Ϊdstkey��list��ͷ��

4.��Set����������

     sadd(key, member)��������Ϊkey��set�����Ԫ��member

     srem(key, member) ��ɾ������Ϊkey��set�е�Ԫ��member

     spop(key) ��������ز�ɾ������Ϊkey��set��һ��Ԫ��

     smove(srckey, dstkey, member) ����memberԪ�ش�����Ϊsrckey�ļ����Ƶ�����Ϊdstkey�ļ���

     scard(key) ����������Ϊkey��set�Ļ���

     sismember(key, member) ������member�Ƿ�������Ϊkey��set��Ԫ��

     sinter(key1, key2,��key N) ���󽻼�

     sinterstore(dstkey, key1, key2,��key N) ���󽻼������������浽dstkey�ļ���

     sunion(key1, key2,��key N) ���󲢼�

     sunionstore(dstkey, key1, key2,��key N) ���󲢼������������浽dstkey�ļ���

     sdiff(key1, key2,��key N) ����

     sdiffstore(dstkey, key1, key2,��key N) ������������浽dstkey�ļ���

     smembers(key) ����������Ϊkey��set������Ԫ��

     srandmember(key) �������������Ϊkey��set��һ��Ԫ��

5.��zset��sorted set������������

     zadd(key, score, member)��������Ϊkey��zset�����Ԫ��member��score�������������Ԫ���Ѿ����ڣ������score���¸�Ԫ�ص�˳��

     zrem(key, member) ��ɾ������Ϊkey��zset�е�Ԫ��member

     zincrby(key, increment, member) �����������Ϊkey��zset���Ѿ�����Ԫ��member�����Ԫ�ص�score����increment�������򼯺�����Ӹ�Ԫ�أ���score��ֵΪincrement

     zrank(key, member) ����������Ϊkey��zset��Ԫ���Ѱ�score��С����������memberԪ�ص�rank����index����0��ʼ������û��memberԪ�أ����ء�nil��

     zrevrank(key, member) ����������Ϊkey��zset��Ԫ���Ѱ�score�Ӵ�С������memberԪ�ص�rank����index����0��ʼ������û��memberԪ�أ����ء�nil��

     zrange(key, start, end)����������Ϊkey��zset��Ԫ���Ѱ�score��С���������е�index��start��end������Ԫ��

     zrevrange(key, start, end)����������Ϊkey��zset��Ԫ���Ѱ�score�Ӵ�С�����е�index��start��end������Ԫ��

     zrangebyscore(key, min, max)����������Ϊkey��zset��score >= min��score <= max������Ԫ��

     zcard(key)����������Ϊkey��zset�Ļ���

     zscore(key, element)����������Ϊkey��zset��Ԫ��element��score

     zremrangebyrank(key, min, max)��ɾ������Ϊkey��zset��rank >= min��rank <= max������Ԫ��

     zremrangebyscore(key, min, max) ��ɾ������Ϊkey��zset��score >= min��score <= max������Ԫ��

     zunionstore / zinterstore(dstkeyN, key1,��,keyN, WEIGHTS w1,��wN, AGGREGATE SUM|MIN|MAX)����N��zset�󲢼��ͽ������������ļ��ϱ�����dstkeyN�С����ڼ�����ÿһ��Ԫ�ص�score���ڽ���AGGREGATE����ǰ����Ҫ���Զ��ڵ�WEIGHT���������û���ṩWEIGHT��Ĭ��Ϊ1��Ĭ�ϵ�AGGREGATE��SUM�������������Ԫ�ص�score�����м��϶�ӦԪ�ؽ��� SUM�����ֵ����MIN��MAX��ָ�����������Ԫ�ص�score�����м��϶�ӦԪ������Сֵ�����ֵ��

6.��Hash����������

     hset(key, field, value)��������Ϊkey��hash�����Ԫ��field<��>value

     hget(key, field)����������Ϊkey��hash��field��Ӧ��value

     hmget(key, field1, ��,field N)����������Ϊkey��hash��field i��Ӧ��value

     hmset(key, field1, value1,��,field N, value N)��������Ϊkey��hash�����Ԫ��field i<��>value i

     hincrby(key, field, integer)��������Ϊkey��hash��field��value����integer

     hexists(key, field)������Ϊkey��hash���Ƿ���ڼ�Ϊfield����

     hdel(key, field)��ɾ������Ϊkey��hash�м�Ϊfield����

     hlen(key)����������Ϊkey��hash��Ԫ�ظ���

     hkeys(key)����������Ϊkey��hash�����м�

     hvals(key)����������Ϊkey��hash�����м���Ӧ��value

     hgetall(key)����������Ϊkey��hash�����еļ���field�������Ӧ��value
     */
