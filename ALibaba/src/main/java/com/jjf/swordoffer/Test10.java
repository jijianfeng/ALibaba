package com.jjf.swordoffer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jjf_lenovo on 2017/4/24.
 */
//����һ���������飬ʵ��һ�����������������������ֵ�˳��
//ʹ�����е�����λ�������ǰ�벿�֣����е�ż��λ��λ������ĺ�벿�֣�����֤������������ż����ż��֮������λ�ò��䡣
public class Test10 {
    public static void main(String[] args){
        int[] array = {2,4,1,3,5,7,6};
        reOrderArrayBySwap(array);
        for(int a:array){
            System.out.print(a);
        }
    }

    //�����汾
    public static void reOrderArrayBySwap(int [] array) {
        if(array==null){
            throw new NullPointerException();
        }
        for(int i=0;i<array.length-1;i++){
            if((array[i]&0x1)==0&&(array[i+1]&0x1)==1){//ż������һλΪ����,�򽻻�
                int temp = array[i];
                array[i] = array[i+1];
                array[i+1] = temp;
                if(i>=1&&(array[i-1]&0x1)==0){ //�����һ����Ҳ��ż������i�˵�ǰһλ
                    i=i-2;
                }
            }

        }
    }

    //�򵥰�,ά����������
    public static void reOrderArray(int [] array) {
        List<Integer> i = new ArrayList<>(array.length);
        List<Integer> j = new ArrayList<>(array.length);
        for(int a :array){
            if((a & 0x1)==1){//����
                i.add(a);
            }
            else{
                j.add(a);
            }
        }
        int count = 0;
        for(int a :i){
            array[count] = a;
            count++;
        }
        for(int a :j){
            array[count] = a;
            count++;
        }
    }

}
