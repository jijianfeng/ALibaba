package com.jjf.swordoffer;

/**
 * Created by jjf_lenovo on 2017/4/20.
 */

/**
 ��һ�������ʼ�����ɸ�Ԫ�ذᵽ�����ĩβ�����ǳ�֮Ϊ�������ת��
 ����һ���ǵݼ�����������һ����ת�������ת�������СԪ�ء�
 ��������{3,4,5,1,2}Ϊ{1,2,3,4,5}��һ����ת�����������СֵΪ1��
 NOTE������������Ԫ�ض�����0���������СΪ0���뷵��0��
 */
public class Test6 {

    /**
     * �򵥶��ַ�
     * @param array
     * @return
     */
    public int minNumberInRotateArray(int [] array) {
        if(array==null||array.length==0){
            return 0;
        }
        int index1 = 0,indexMid = index1;
        int index2 = array.length-1;
        while(array[index1]>=array[index2]){
            if(index2-index1==1){
                indexMid = index2;
                break;
            }
            indexMid = (index1+index2)/2;
            //����±�Ϊindex1��index2��indexMidָ�������������ȣ���ֻ��˳�����
            if(array[index1]==array[index2]&&array[index2]==array[indexMid]){
                return MinInOrder(array,index1,index2);
            }
            if(array[indexMid]>=array[index1]){
                index1 = indexMid;
            }
            else if(array[indexMid]<=array[index2]){
                index2 = indexMid;
            }
        }
        return array[indexMid];
    }

    public int MinInOrder(int[] array,int index1,int index2){
        int result = array[index1];
        for(int i=index1+1;i<index2;i++){
            if(result>array[i]){
                result = array[i];
            }
        }
        return result;
    }

    /**
     * lowb�취
     * @param array
     * @return
     */
    public int minNumberInRotateArrayLow(int [] array) {
        if(array.length==0){
            return 0;
        }
        for(int i=0;i<array.length-1;i++){
            if(array[i+1]<array[i]){
                return array[i+1];
            }
        }
        return array[0];
    }
}
