package com.jjf.swordoffer;

/**
 * Created by jjf_lenovo on 2017/4/25.
 */
//дһ������������������֮�ͣ�����ʹ�� + - * / �ĸ������
public class Test13 {
    public static void main(String[] args){
        System.out.println(add(2,-1));
    }

    public static int add(int number1,int number2){
        do{
            System.out.println(Integer.toBinaryString(number1)+":"+Integer.toBinaryString(number2));
            int a = number1 ^ number2; //�����Կ�������λ�ļӷ�  1+1=0 0+0=0 1+0=0+1=1
            number2 = (number1 & number2 )<<1; //�����λ������ǰһλ��û��λ0������Ҳû��ϵ
            number1 = a;
        }
        while(number2!=0);
        System.out.println(Integer.toBinaryString(number1)+":"+Integer.toBinaryString(number2));
        return number1 ;
    }
}