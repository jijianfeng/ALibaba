package com.jjf.lambda;

import org.junit.Test;

import java.util.Arrays;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by jijianfeng on 2017/6/10.
 */
public class StreamTest {

    /**
     * ��6��Java 8��ʹ��lambda���ʽ��Map��Reduceʾ��
     �����������Ϊ��֪�ĺ���ʽ��̸���map��
     �������㽫�������ת���������ڱ����У����ǽ� costBeforeTax �б��ÿ��Ԫ��ת����Ϊ˰���ֵ��
     ���ǽ� x -> x*x lambda���ʽ���� map() ���������߽���Ӧ�õ����е�ÿһ��Ԫ�ء�
     Ȼ���� forEach() ���б�Ԫ�ش�ӡ������ʹ����API���ռ����࣬���Եõ����к�˰�Ŀ�����
     �� toList() �����ķ����� map ���κ����������Ľ���ϲ������������ռ������������ն˲��������֮��㲻���������ˡ�
     ��������������API�� reduce() �������������ֺϳ�һ������һ�����ӽ��ὲ����
     */
    @Test
    public  void test1(){
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
     *��6.2��Java 8��ʹ��lambda���ʽ��Map��Reduceʾ��
     ���ϸ������У����Կ���map�������ࣨ�����б�Ԫ�ؽ���ת���ġ�
     ����һ�� reduce() �������Խ�����ֵ�ϲ���һ����Map��Reduce�����Ǻ���ʽ��̵ĺ��Ĳ�����
     ��Ϊ�书�ܣ�reduce �ֱ���Ϊ�۵����������⣬reduce ������һ���µĲ��������п����Ѿ���ʹ������
     SQL������ sum()��avg() ���� count() �ľۼ�������ʵ���Ͼ��� reduce ��������Ϊ���ǽ��ն��ֵ������һ��ֵ��
     ��API����� reduceh() �������Խ���lambda���ʽ����������ֵ���кϲ���IntStream�������������� average()��count()��sum() ��
     �ڽ��������� reduce ������Ҳ��mapToLong()��mapToDouble() ��������ת�����Ⲣ���������㣬��������ڽ�������Ҳ�����Լ����塣
     �����Java 8��Map Reduceʾ����������ȶ����м۸�Ӧ�� 12% ��VAT��Ȼ���� reduce() ���������ܺ͡�
     */
    @Test
    public void test2(){
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

    /**
     * ��8�����б��ÿ��Ԫ��Ӧ�ú���
     ����ͨ����Ҫ���б��ÿ��Ԫ��ʹ��ĳ��������������һ����ĳ����������ĳ��������������������
     ��Щ���������ʺ��� map() ���������Խ�ת���߼���lambda���ʽ����ʽ���� map() �����
     �Ϳ��ԶԼ��ϵĸ���Ԫ�ؽ���ת���ˣ�������ʾ��
     */
    @Test
    public void test3(){
        // ���ַ������ɴ�д���ö�����������
        List<String> G7 = Arrays.asList("USA", "Japan", "France", "Germany", "Italy", "U.K.","Canada");
        String G7Countries = G7.stream().map(x -> x.toUpperCase()).collect(Collectors.joining(", "));
        System.out.println(G7Countries);
    }

    /**
     * ��9�����Ʋ�ͬ��ֵ������һ�����б�
     ����չʾ������������� distinct() �������Լ��Ͻ���ȥ�ء�
     */
    @Test
    public void test4(){
        // �����в�ͬ�����ִ���һ���������б�
        List<Integer> numbers = Arrays.asList(9, 10, 3, 4, 7, 3, 4);
        List<Integer> distinct = numbers.stream().map( i -> i*i).distinct().collect(Collectors.toList());
        System.out.printf("Original List : %s,  Square Without duplicates : %s %n", numbers, distinct);
    }

    /**
     * ��10�����㼯��Ԫ�ص����ֵ����Сֵ���ܺ��Լ�ƽ��ֵ
     IntStream��LongStream �� DoubleStream ���������У��и��ǳ����õķ������� summaryStatistics() ��
     ���Է��� IntSummaryStatistics��LongSummaryStatistics ���� DoubleSummaryStatistic s��
     ��������Ԫ�صĸ���ժҪ���ݡ��ڱ����У���������������������б�����ֵ����Сֵ��
     ��Ҳ�� getSum() �� getAverage() ����������б������Ԫ�ص��ܺͼ�ƽ��ֵ��
     */
    @Test
    public void test5(){
        //��ȡ���ֵĸ�������Сֵ�����ֵ���ܺ��Լ�ƽ��ֵ
        List<Integer> primes = Arrays.asList(2, 3, 5, 7, 11, 13, 17, 19, 23, 29);
        IntSummaryStatistics stats = primes.stream().mapToInt((x) -> x).summaryStatistics();
        System.out.println("Highest prime number in List : " + stats.getMax());
        System.out.println("Lowest prime number in List : " + stats.getMin());
        System.out.println("Sum of all prime numbers : " + stats.getSum());
        System.out.println("Average of all prime numbers : " + stats.getAverage());
    }
}
