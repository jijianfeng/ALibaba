package com.jjf.swordoffer;
/**
 * ��һ����ά�����У�ÿһ�ж����մ����ҵ�����˳������ÿһ�ж����մ��ϵ��µ�����˳������
 * �����һ������������������һ����ά�����һ���������ж��������Ƿ��и�������
 * @author jjf_lenovo
 * 2017��2��24��18:24:06
 * ʱ�����ƣ�1�� �ռ����ƣ�32768K
 * ����ʱ�䣺143ms ռ���ڴ棺5147k
 */
public class Test1 {
	public static void main(String args[]){
		 int A[][] = { { 1, 2, 8, 9 }, { 2, 4, 9, 12 }, { 4, 7, 10, 13 },  
	                { 6, 8, 11, 15 } };  
	        System.out.println(Find(7,A));  
	}
	public static boolean Find(int target, int [][] array) {
			boolean flag = false;  
	//        int rows = array.length;// ����  
	//        int columns = array[0].length;// ����  
	        int row = 0;  
	        int column = array[0].length - 1;  
	        while (row < array.length && column >= 0) {  
	            // �Ƚ϶�ά�����е�Ԫ����number�Ĺ�ϵ  target
	            if (array[row][column] == target) {  
	                flag = true;  
	                break;// ����ѭ��  
	            } else if (array[row][column] > target) {  
	                // �б�С  
	                column--;  
	            } else {  
	                // �б��  
	                row++;  
	            }  
	        }  
	        return flag;  
    }
}
