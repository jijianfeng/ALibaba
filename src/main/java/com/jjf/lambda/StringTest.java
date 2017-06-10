package com.jjf.lambda;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jijianfeng on 2017/6/10.
 */
public class StringTest {
    /**
     * ��7��ͨ�����˴���һ��String�б�
     ������Java�������ڴ��ģ�����ϵ�һ�����ò�����������ʹ��lambda���ʽ����API���˴��ģ���ݼ����Ǿ��˵ļ򵥡�
     ���ṩ��һ�� filter() ����������һ�� Predicate ���󣬼����Դ���һ��lambda���ʽ��Ϊ�����߼���
     �������������lambda���ʽ����Java���ϣ���������⡣
     * @param args
     */
    public static void main(String args[]){
        // ����һ���ַ����б�ÿ���ַ������ȴ���2
        List<String> strList = Arrays.asList("Java", "Scala", "C++", "Haskell", "Lisp");
        List<String> filtered = strList.stream().filter(x -> x.length()> 3).collect(Collectors.toList());
        System.out.printf("Original List : %s, filtered list : %s %n", strList, filtered);
    }
}
