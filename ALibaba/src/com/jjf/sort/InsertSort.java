package com.jjf.sort;
//�������� �ȶ�   ����:��Ʒ�ı��������������������
//ʱ�临�Ӷ���O(n) ~ O(n^2)
//�ռ临�Ӷ�O(1),��Ϊtemp
//20W�����ݣ�10996 12530 10961
//2W�����ݣ�129 111 128
//2k������ ��4 4 6
public class InsertSort {
	public static void main(String args[]){
		int lengtg = 20000000;
		int[] a = new int[lengtg];
		for(int i = 0;i<lengtg;i++){
			a[i] = (int) (Math.random()*1000);
		}
		long start = System.currentTimeMillis();
		sort(a);
		long end = System.currentTimeMillis();
		System.out.println("����ʱ�䣺"+(end-start));
		print(a);
	}
	
	public static void sort(int table[]){
		for(int i=1;i<table.length;i++){
			int temp = table[i];
			int j;
			for(j=i-1;j>=0&&table[j]>temp;j--){
				table[j+1] = table[j];
			}
			table[j+1] = temp;
		}
	}
	
	public static void print(int a[]){
		for(int i =0;i<a.length;i++){
			System.out.print(" "+a[i]);
		}
	}
}
