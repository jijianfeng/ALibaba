package com.jjf.collection;

import org.apache.commons.lang.ArrayUtils;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Vector;

/**
 * Created by jijianfeng on 2017/6/30.
 */
public class ArraysTest {

  @Test
  public void test() {
    String s[] = {"aa", "bb", "cc"};
    List<String> sList = Arrays.asList(s);
    for (String str : sList) {//�ܱ���������Ԫ��
      System.out.println(str);
    }
    System.out.println(sList.size());//Ϊ3

    System.out.println("- - - - - - - - - - -");

    int i[] = {11, 22, 33};
    List intList = Arrays.asList(i);  //intList�о���һ��Integer�������͵Ķ�������������Ϊһ��Ԫ�ش��ȥ��
    for (Object o : intList) {//��һ��Ԫ��
      System.out.println(o.toString());
    }

    System.out.println("- - - - - - - - - - -");

    List
        intObjectList =
        Arrays.asList(ArrayUtils.toObject(i));  //intList�о���һ��Integer�������͵Ķ�������������Ϊһ��Ԫ�ش��ȥ��
    for (Object o : intObjectList) {//��һ��Ԫ��
      System.out.println(o.toString());
    }

    System.out.println("- - - - - - - - - - -");

    Integer ob[] = {11, 22, 33};
    List<Integer> objList = Arrays.asList(ob);  //�������ÿһ��Ԫ�ض�����Ϊlist�е�һ��Ԫ��
    for (int a : objList) {
      System.out.println(a);
    }

    System.out.println("- - - - - - - - - - -");

    //objList.remove(0);//asList()���ص���arrays��˽�е��ռ�ArrayList���ͣ�����set,get��contains��������û�����Ӻ�ɾ��Ԫ�صķ��������Դ�С�̶�,�ᱨ��
    //objList.add(0);//����asList���ص�list��ʵ��������add���������Իᱨ��
  }

  @Test
  public void testAddAndRemove(){
    List list = new ArrayList();
    list.add(2);
    list.add(3);
    list.add(4);
    list.add(5);
    list.add(0,1);
    System.out.println(list.toString());
  }

  @Test
  public void testAddAndRemove1(){
    List list = new Vector();
    list.add(2);
    list.add(3);
    list.add(4);
    list.add(5);
    list.add(0,1);
    System.out.println(list.toString());
  }
}
