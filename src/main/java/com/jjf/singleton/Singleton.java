package com.jjf.singleton;

/**
 * Created by jijianfeng on 2017/6/22.
 */
public class Singleton {
    //���� --����
    public static final Singleton singleton = new Singleton(); //2.private

    private Singleton(){
        System.out.println("i init");
    };

    public  void doSomething(){
        //
    }

    //2����
//    public Singleton getInstance(){
//        return singleton;
//    }
}

