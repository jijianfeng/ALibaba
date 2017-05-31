package com.jjf.regex;

import org.junit.Test;

import java.util.regex.Pattern;

/**
 * Created by jjf_lenovo on 2017/5/31.
 */

/**
 *  �ַ���������ʽ�Ƿ�ƥ��
 */
public class RegexIsMatches {
    /**
     * ��ϰƥ�� + * ?
     */
    @Test
    public void test1(){
        //��ʵ������ �Ƚ�������� runo o+ b
//        String pattern = "runoo+b";//"runoo+.*"//runoo+ //+ �Ŵ���ǰ����ַ��������ٳ���һ�Σ�1�λ��Σ�
//        String pattern = "runoo*b";  //* �Ŵ����ַ����Բ�����,Ҳ���Գ���һ�λ��߶�Σ�0�Ρ���1�Ρ����Σ���
        String pattern = "runo(o)?b"; //?�Ŵ���ǰ����ַ����ֻ���Գ���һ�Σ�0�Ρ���1�Σ���
        System.out.println(isMatches(pattern,"runob"));
        System.out.println(isMatches(pattern,"runoob"));
        System.out.println(isMatches(pattern,"runoooooob"));
    }

    /**
     * ��ϰ�ַ�
     */
    @Test
    public void test2(){
        // �ִ� i   \\s+  am  \\s  .*
        // \s ƥ���κοհ��ַ� .* ��ʾ����
//        String pattern = "i\\s+am\\s.*";
        String pattern = "i(\\s)+am(\\s).*";
        System.out.println(isMatches(pattern,"i am boss"));
        System.out.println(isMatches(pattern,"i            am boss"));
        System.out.println(isMatches(pattern,"I  am boss"));
        System.out.println(isMatches(pattern,"iam boss"));
    }

    /**
     * ��ϰ�޶���
     */
    @Test
    public void test3(){
//        String pattern = "B(o){2}b";
        String pattern = "B(o){1,3}b";
        System.out.println(isMatches(pattern,"Bob"));
        System.out.println(isMatches(pattern,"Boob"));
        System.out.println(isMatches(pattern,"Booob"));
        System.out.println(isMatches(pattern,"Boooob"));
    }

    public static boolean isMatches(String pattern,String content){
        return Pattern.matches(pattern, content);
    }
}
