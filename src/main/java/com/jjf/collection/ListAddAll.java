package com.jjf.collection;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jijianfeng on 2017/8/28.
 */
public class ListAddAll {
  public static void main(String[] args){
    /*���Ժϲ�����������ͬ��list*/
    List<String> list1 = new ArrayList<String>();
    List<String> list2 = new ArrayList<String>();
    //��list1��ֵ
    list1.add("1");
    list1.add("2");
    list1.add("3");
    list1.add("4");
    //��list2��ֵ
    list2.add("5");
    list2.add("6");
    list2.add("7");
    list2.add("8");
    //��list1.list2�ϲ�
    list1.addAll(list2);
    //ѭ�����list1 �������
    for (String s : list1) {
      System.out.print(s);
    }
  }
}
