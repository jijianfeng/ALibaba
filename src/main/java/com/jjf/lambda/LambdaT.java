package com.jjf.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jijianfeng on 2017/6/30.
 */
public class LambdaT {
    //дһ��lambda���ʽ�ƻ����ͽṹ������
    public static void main(String[] args){
        List list;
        list = Factory.drive(ArrayList::new);
        System.out.println(list.toString());
        list = Factory.drive(new Worker<Integer>() {
            @Override
            public <String> List<String> doWork() {
                return new ArrayList<String>();
            }
        });
        System.out.println(list.toString());

        //??????????��������д���İ���������������
        List<String> drive = Factory.drive((Worker<String>) ArrayList::new);
    }
}

//�򵥵ĸ�������
interface Worker<T>{
     <T> List<T> doWork();
}

class Factory{
    public static <T> List<T>  drive(Worker<T> worker){
        return worker.doWork();
    }
}