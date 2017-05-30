package com.jjf.sort;
//��������
//ʱ�临�Ӷ���
//�ռ临�Ӷ�
//2000W�����ݣ�Exception in thread "main" java.lang.StackOverflowError
//200W�����ݣ�1147 1140 1143
//20W�����ݣ�26 29 39
//2W�����ݣ�4 5 16
public class QuickSort {
	public static void main(String[] args){
//		int lengtg = 200000;
		int[] a = {4,7,0,1,3};
//		int[] a = new int[lengtg];
		for(int i = 0;i<a.length;i++){
			a[i] = (int) (Math.random()*10);
		}
		long start = System.currentTimeMillis();
//		print(a);
		System.out.println("��ʼ������");
		quickSort(a,0,a.length-1);
		long end = System.currentTimeMillis();
		System.out.println("����ʱ�䣺"+(end-start));
//		print(a);
	}
	// һ�˿�������begin��highָ�����е��½���Ͻ磬�ݹ��㷨
	private static void quickSort(int[] table, int begin, int end) {
		if (begin < end) // ������Ч
		{
			int i = begin, j = end;
//			System.out.println(i+":"+j);
			int vot = table[i]; // ��һ��ֵ��Ϊ��׼ֵ
			while (i != j) // һ������
			{
				while (i < j && vot <= table[j]){
					// �Ӻ���ǰѰ�ҽ�Сֵ
					j--;
				}
				if (i < j){
					table[i++] = table[j];// ��СԪ����ǰ�ƶ�
				}
				while (i < j && table[i] <= vot){
					// ��ǰ���Ѱ�ҽϴ�ֵ
					i++;
				}
				if (i < j){
					table[j--] = table[i]; // �ϴ�Ԫ������ƶ�
				}
//				print(table);
			}
			table[i] = vot; // ��׼ֵ��������λ��
//			print(table);
			quickSort(table, begin, j - 1); // ǰ�������������򣬵ݹ����
			quickSort(table, i + 1, end); // ��������������򣬵ݹ����
		}
	}

	public static void print(int a[]) {
		for (int i = 0; i < a.length; i++) {
			System.out.print(" " + a[i]);
		}
		System.out.println();
	}
}
