package com.jjf.swordoffer;

/**
 * Created by jjf_lenovo on 2017/4/21.
 */

/**
 * ��Ҷ�֪��쳲��������У�����Ҫ������һ������n���������쳲��������еĵ�n�
 n<=39
 */
public class Test7 {

    /**
     * �����ݹ��㷨���ظ����������,Ч�ʵ͵�����..
     * @param n
     * @return
     */
    public static int Fibonacci(int n) {
        if(n==1||n==2){
            return 1;
        }
        else{
            return Fibonacci(n-1)+Fibonacci(n-2);
        }
    }

    /**
     * ��ָoffer�Ľ��Ž⣬������õ�
     * @param n
     * @return
     */
    public static int FibonacciByOffer(int n){
        if(n<2){
            return n==0?0:1;
        }
        int a=0,b=1,c=1;
        for(int i=2;i<=n;i++){
            c=a+b;
            a=b;
            b=c;
        }
        return c;
    }

    /**
     * ������
     * @param n
     * @return
     */
    public static int FibonacciByBom(int n){
        if(n<2){
            return n==0?0:1;
        }
        else{
            int tem[] = new int [n];
            tem[0] = 1;
            tem[1] = 1;
            for(int i=2;i<n;i++){
                tem[i] = tem[i-1]+tem[i-2];
            }
            return tem[n-1];
        }
    }

    public static void main(String[] args){
        //�ݹ�Ч�ʵ���
//        Long start = System.currentTimeMillis();
//        System.out.println(Fibonacci(50));
//        Long end = System.currentTimeMillis();//�������ʮ������Ҫ40954ms��40s�����ˣ����� i7-4710mq��cpu......
//        System.out.println(end-start);
        //������ѿռ䣬Ч�ʻ���
//        Long start = System.currentTimeMillis();
//        System.out.println(FibonacciByBom(10000000));
//        Long end = System.currentTimeMillis();//�����1000W����ֻ��26ms
//        System.out.println(end-start);
        //��̬�滮�����Ž�
//        Long start = System.currentTimeMillis();
//        System.out.println(FibonacciByOffer(10000000));
//        Long end = System.currentTimeMillis();//�����1000W����ֻ��7ms
//        System.out.println(end-start);
//        for(int i=1;i<=39;i++){
//            System.out.println(FibonacciByoffer(i));
//        }
//        for(int i=1;i<=39;i++){
//            System.out.println(Fibonacci(i));
//        }
    }
}
