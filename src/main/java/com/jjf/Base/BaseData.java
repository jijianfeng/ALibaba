package com.jjf.Base;

/**
 * Created by jijianfeng on 2017/6/26.
 */
public class BaseData {
    static byte a;
    static short b;
    static int c;
    static long d;
    static float e;
    static double f;
    static char g;
    static boolean h;

    static  Integer c1;

    //String���ǻ�������
    static String str1 = "";//����һ��String���͵����ã����ҷ����ڴ�ռ������"";
    static String str2; //ֻ����һ��string���͵����ã��������ڴ�ռ�,Ĭ��Ϊnull

    public static void main(String[] args) {

        System.out.println("byte�Ĵ�С��"+Byte.SIZE+" byte��Ĭ��ֵ��"+a+" byte�����ݷ�Χ��"+Byte.MIN_VALUE+"~"+Byte.MAX_VALUE);
        System.out.println("short�Ĵ�С��"+Short.SIZE+" short��Ĭ��ֵ��"+b+" short�����ݷ�Χ��"+Short.MIN_VALUE+"~"+Short.MAX_VALUE);
        System.out.println("int�Ĵ�С��"+Integer.SIZE+" int��Ĭ��ֵ��"+c+" int�����ݷ�Χ��"+Integer.MIN_VALUE+"~"+Integer.MAX_VALUE);
        System.out.println("long�Ĵ�С��"+Long.SIZE+" long��Ĭ��ֵ��"+d+" long�����ݷ�Χ��"+Long.MIN_VALUE+"~"+Long.MAX_VALUE);
        System.out.println("float�Ĵ�С��"+Float.SIZE+" float��Ĭ��ֵ��"+e+" float�����ݷ�Χ��"+Float.MIN_VALUE+"~"+Float.MAX_VALUE);
        System.out.println("double�Ĵ�С��"+Double.SIZE+" double��Ĭ��ֵ��"+f+" double�����ݷ�Χ��"+Double.MIN_VALUE+"~"+Double.MAX_VALUE);
        System.out.println("char�Ĵ�С��"+Character.SIZE+" char��Ĭ��ֵ��"+g+" char�����ݷ�Χ��"+Character.MIN_VALUE+"~"+Character.MAX_VALUE);
        System.out.println("boolean�Ĵ�С��"+Byte.SIZE+" boolean��Ĭ��ֵ��"+h+" boolean�����ݷ�Χ��"+Byte.MIN_VALUE+"~"+Byte.MAX_VALUE);

        System.out.println("String�ַ�����Ĭ��ֵ��"+str1+"str��Ĭ�ϳ��ȣ�"+str1.length());
        System.out.println("String�ַ�����Ĭ��ֵ��"+str2);

        System.out.println(c1+"::"+c);

        String a = null;

        switch(a){
            case "123":
                System.out.println("123");
            default:
                System.out.println("default");
        }
    }
}
