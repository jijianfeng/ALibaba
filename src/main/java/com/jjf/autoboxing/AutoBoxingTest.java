package com.jjf.autoboxing;

import org.junit.Test;

/**
 * Created by jijianfeng on 2017/6/22.
 */
public class AutoBoxingTest {

    @Test
    public void testAutoBox(){
        Long sum = 0L ;  //ʹ�����Զ�װ�� ��
//        long sum = 0L ;//ûʹ�ã���
        for(int i=0;i<Integer.MAX_VALUE;i++){
            sum+=i;
        }
        System.out.println(sum);
    }
}
