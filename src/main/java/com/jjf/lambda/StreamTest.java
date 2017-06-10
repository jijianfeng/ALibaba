package com.jjf.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jijianfeng on 2017/6/10.
 */
public class StreamTest {
    public static void main(String[] args){
        test2();
    }

    /**
     * ��6��Java 8��ʹ��lambda���ʽ��Map��Reduceʾ��
     �����������Ϊ��֪�ĺ���ʽ��̸���map��
     �������㽫�������ת���������ڱ����У����ǽ� costBeforeTax �б��ÿ��Ԫ��ת����Ϊ˰���ֵ��
     ���ǽ� x -> x*x lambda���ʽ���� map() ���������߽���Ӧ�õ����е�ÿһ��Ԫ�ء�
     Ȼ���� forEach() ���б�Ԫ�ش�ӡ������ʹ����API���ռ����࣬���Եõ����к�˰�Ŀ�����
     �� toList() �����ķ����� map ���κ����������Ľ���ϲ������������ռ������������ն˲��������֮��㲻���������ˡ�
     ��������������API�� reduce() �������������ֺϳ�һ������һ�����ӽ��ὲ����
     */
    public static void test1(){
        // ��ʹ��lambda���ʽΪÿ����������12%��˰
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        for (Integer cost : costBeforeTax) {
            double price = cost + .12*cost;
            System.out.println(price);
        }

        // ʹ��lambda���ʽ
        costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        costBeforeTax.stream().map((cost) -> cost + .12*cost).forEach(System.out::println);
    }

    /**
     *
     */
    public static void test2(){
        // Ϊÿ����������12%��˰
        // �Ϸ�����
        List<Integer> costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        double total = 0;
        for (Integer cost : costBeforeTax) {
            double price = cost + .12*cost;
            total = total + price;
        }
        System.out.println("Total : " + total);

        // �·�����
        costBeforeTax = Arrays.asList(100, 200, 300, 400, 500);
        double bill = costBeforeTax.stream().map((cost) -> cost + .12*cost).reduce((sum, cost) -> sum + cost).get();
        System.out.println("Total : " + bill);
    }
}
