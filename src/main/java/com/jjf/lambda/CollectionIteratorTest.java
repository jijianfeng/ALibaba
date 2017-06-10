package com.jjf.lambda;

import java.util.Arrays;
import java.util.List;

/**
 * Created by jijianfeng on 2017/6/10.
 */
public class CollectionIteratorTest {
    public static void main(String[] args){
        // Java 8֮ǰ��
        List<String> oldFeatures = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        for (String feature : oldFeatures) {
            System.out.println(feature);
        }

        // Java 8֮��
        List<String> features = Arrays.asList("Lambdas", "Default Method", "Stream API", "Date and Time API");
        features.forEach(n -> System.out.println(n));

        // ʹ��Java 8�ķ������ø����㣬����������::˫ð�Ų�������ʾ��
        // ��������C++����������������
        features.forEach(System.out::println);
    }
}
