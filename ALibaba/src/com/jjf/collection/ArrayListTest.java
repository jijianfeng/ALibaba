package com.jjf.collection;

import java.util.ArrayList;
import java.util.List;

public class ArrayListTest {
	public static void main(String args[]){
		final int N = 10000000;
		Object obj = new Object();
		
		//û�õ���ensureCapacity()������ʼ��ArrayList����
		ArrayList list = new ArrayList();
		long startTime = System.currentTimeMillis();
		for(int i=0;i<N;i++){
			list.add(obj);
		}
		long endTime = System.currentTimeMillis();
		System.out.println("û�е���ensureCapacity()��������ʱ�䣺" + (endTime - startTime) + "ms");
		
		//û�õ���ensureCapacity()������ʼ��ArrayList��������
		list = new ArrayList(N);
		startTime = System.currentTimeMillis();
		for(int i=0;i<N;i++){
			list.add(obj);
		}
		endTime = System.currentTimeMillis();
		System.out.println("û�е���ensureCapacity()��������ʼ������������������ʱ�䣺" + (endTime - startTime) + "ms");
		
		//����ensureCapacity()������ʼ��ArrayList��������
		list = new ArrayList(N);
		list.ensureCapacity(N);
		startTime = System.currentTimeMillis();
		for(int i=0;i<N;i++){
			list.add(obj);
		}
		endTime = System.currentTimeMillis();
		System.out.println("�е���ensureCapacity()�������������˳�ʼ����������ʱ�䣺" + (endTime - startTime) + "ms");
		
		//����ensureCapacity()������ʼ��ArrayList����
		list = new ArrayList();
		startTime = System.currentTimeMillis();
		list.ensureCapacity(N);//Ԥ������list�Ĵ�С
		for(int i=0;i<N;i++){
			list.add(obj);
		}
		endTime = System.currentTimeMillis();
		System.out.println("����ensureCapacity()��������ʱ�䣺" + (endTime - startTime) + "ms");
	}
}
