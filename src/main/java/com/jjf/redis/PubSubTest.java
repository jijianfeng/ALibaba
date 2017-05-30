package com.jjf.redis;

import redis.clients.jedis.JedisPubSub;

/**
 * Created by jjf_lenovo on 2017/5/17.
 */
public class PubSubTest  extends JedisPubSub {

    // ȡ�ö��ĵ���Ϣ��Ĵ���
    public void onMessage(String channel, String message) {
        System.out.println("onMessage");
        System.out.println(channel+"::"+message);
    }

    // ȡ�ð����ʽ�ķ�ʽ���ĵ���Ϣ��Ĵ���
    public void onPMessage(String pattern, String channel, String message) {
        System.out.println("onPMessage");
        System.out.println(pattern+"::"+channel+"::"+message);
    }

    // ��ʼ������ʱ��Ĵ���
    public void onSubscribe(String channel, int subscribedChannels) {
        System.out.println("onSubscribe");
        System.out.println(channel+"::"+subscribedChannels);
    }

    // ȡ������ʱ��Ĵ���
    public void onUnsubscribe(String channel, int subscribedChannels) {
        System.out.println("onUnsubscribe");
        System.out.println(channel+"::"+subscribedChannels);
    }

    // ȡ�������ʽ�ķ�ʽ����ʱ��Ĵ���
    public void onPUnsubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPUnsubscribe");
        System.out.println(pattern+"::"+subscribedChannels);
    }

    // ��ʼ�������ʽ�ķ�ʽ����ʱ��Ĵ���
    public void onPSubscribe(String pattern, int subscribedChannels) {
        System.out.println("onPSubscribe");
        System.out.println(pattern+"::"+subscribedChannels);
    }

    public void onPong(String pattern) {
        System.out.println("onPong");
        System.out.println(pattern);
    }
}
