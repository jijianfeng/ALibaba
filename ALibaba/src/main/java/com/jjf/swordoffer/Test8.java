package com.jjf.swordoffer;

/**
 * Created by jjf_lenovo on 2017/4/21.
 */
//����һ��������������������Ʊ�ʾ��1�ĸ��������и����ò����ʾ��
//����λ����
public class Test8 {
    public static void main(String args[]){
        int a = 9;
        int b = -9;
        int da = 2147483647;
        char ch = 'd';
        double c = Math.pow(2,32);
        System.out.println(Integer.toBinaryString(a));
        System.out.println(Integer.toBinaryString(b));
        System.out.println(c);
        String start = "\\u4e00";
        String end = "\\u9fa5";
        int s = Integer.parseInt(start.substring(2, start.length()), 16);
        int e = Integer.parseInt(end.substring(2, end.length()), 16);
        System.out.println(s+":"+e);
        for (int i = 19968; i <= 40869; i++) {
            System.out.println((char) i);
        }
    }
}
