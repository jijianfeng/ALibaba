package com.jjf.sort;
//������
//ʱ�临�Ӷ���
//�ռ临�Ӷ�
//2�������ݣ�46419 44916 45233
//2000W�����ݣ�4073 4072 4107
//200W�����ݣ�270 259 270
//20W�����ݣ�24 25 25
//2W�����ݣ�5 4 4
public class HeapSort {
	public static void main(String args[]){
		int lengtg = 200000000;
		int[] a = new int[lengtg];
		for(int i = 0;i<lengtg;i++){
			a[i] = (int) (Math.random()*1000);
		}
		long start = System.currentTimeMillis();
		heapSort_max(a);
		long end = System.currentTimeMillis();
		System.out.println("����ʱ�䣺"+(end-start));
//		print(a);
	}
	
	public static void heapSort_max(int[] table) // ���������򣩣�����
	{
//		System.out.println("���ѣ� " + isMaxHeap(table));
//		System.out.println("������������");
		int n = table.length;
		for (int j = n / 2 - 1; j >= 0; j--)
			// ��������
			sift_max(table, j, n - 1);
//		System.out.println("���ѣ� " + isMaxHeap(table));
//		System.out.println("����������");
		for (int j = n - 1; j > 0; j--) // ÿ�˽����ֵ���������棬�ٵ����ɶ�
		{
			int temp = table[0];
			table[0] = table[j];
			table[j] = temp;
			sift_max(table, 0, j - 1);
		}
	}

	// ����beginΪ�����������������ѣ�begin��end�������½���Ͻ�
	private static void sift_max(int[] table, int begin, int end) {
		int i = begin, j = 2 * i + 1; // iΪ�����ĸ���jΪi��������
		int temp = table[i]; // ��õ�i��Ԫ�ص�ֵ
		while (j <= end) // �ؽϴ�ֵ���ӽ������ɸѡ
		{
			if (j < end && table[j] < table[j + 1]) // ����Ԫ�رȽ�
				j++; // jΪ���Һ��ӵĽϴ���
			if (temp < table[j]) // ����ĸ���ֵ��С
			{
				table[i] = table[j]; // ���ӽ���еĽϴ�ֵ����
				i = j; // i��j����һ��
				j = 2 * i + 1;
			} else
				break;
		}
		table[i] = temp; // ��ǰ������ԭ��ֵ�������λ��
//		System.out.print("sift  " + begin + ".." + end + "  ");
//		print(table);
	}
	
//	public static boolean isMinHeap(int[] value) // �ж�һ�����������Ƿ�Ϊ��С��
//	{
//		if (value.length == 0) // �����в��Ƕѡ����޴˾䣬��������Ƕѣ����岻ͬ
//			return false;
//		for (int i = value.length / 2 - 1; i >= 0; i--) // i������һ�������ĸ���㿪ʼ
//		{
//			int j = 2 * i + 1; // j��i�����ӣ��϶�����
//			if (value[i] > value[j] || j + 1 < value.length
//					&& value[i] > value[j + 1])
//				return false;
//		}
//		return true;
//	}
	
//	public static boolean isMaxHeap(int[] value) // �ж�һ�����������Ƿ�Ϊ����
//	{
//		if (value.length == 0) // �����в��Ƕ�
//			return false;
//		for (int i = value.length / 2 - 1; i >= 0; i--) // i������һ�������ĸ���㿪ʼ
//		{
//			int j = 2 * i + 1; // j��i�����ӣ��϶�����
//			if (value[i] < value[j] || j + 1 < value.length
//					&& value[i] < value[j + 1])
//				return false;
//		}
//		return true;
//	}
	
	public static void print(int a[]){
		for(int i =0;i<a.length;i++){
			System.out.print(" "+a[i]);
		}
	}

}
