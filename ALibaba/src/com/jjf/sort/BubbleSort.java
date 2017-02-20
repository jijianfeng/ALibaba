package com.jjf.sort;
//ð������ �ȶ� 
//ʱ�临�Ӷ���O(n) ~ O(n^2),�������г�ʼ����Խ�ӽ�����ֱ�Ӳ��������ʱ��Ч��Խ��
//�ռ临�Ӷ�O(1),��Ϊtemp
//20W�����ݣ�50689 50940 50474
//2W�����ݣ�489 485 477
//2k�����ݣ�7 8 7
public class BubbleSort {
	public static void main(String[] args){
		int lengtg = 2000;
		int[] a = new int[lengtg];
		for(int i = 0;i<lengtg;i++){
			a[i] = (int) (Math.random()*1000);
		}
		//20W�����ݣ�10996 12530 10961
		long start = System.currentTimeMillis();
		bubbleSort(a);
		long end = System.currentTimeMillis();
		System.out.println("����ʱ�䣺"+(end-start));
		//20W�����ݣ�10996 12530 10961
//		print(a);
	}
	
	public static void bubbleSort(int[] table){ // ð������{
		System.out.println("ð������");
		boolean exchange = true; // �Ƿ񽻻��ı��
		for (int i = 1; i < table.length && exchange; i++){ // �н���ʱ�ٽ�����һ�ˣ����n-1��
			exchange = false; // �ٶ�Ԫ��δ����
			for (int j = 0; j < table.length - i; j++){
				// һ�˱Ƚϡ�����
				if (table[j] > table[j + 1]){ // ����ʱ������
					int temp = table[j];
					table[j] = table[j + 1];
					table[j + 1] = temp;
					exchange = true; // �н���
				}
			}
//			System.out.print("��" + i + "��: ");
//			print(table);
		}
	}

	public static void print(int a[]){
		for(int i =0;i<a.length;i++){
			System.out.print(" "+a[i]);
		}
		System.out.println();
	}
}
