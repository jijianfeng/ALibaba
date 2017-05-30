package com.jjf.gc;

import java.util.ArrayList;
import java.util.List;
//-Xms512m -Xmx1024m -Xmn10m �� Ĭ�ϡ������С�ڴ�
//-XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=../logs.dump �ڴ����ʱ���ɿ���
//-XX:+PrintGCDetails ���GC��־
public class HeapDumpOnOutOfMemoryErroy {
	public static void main(String args[]){
		RuntimeConstantPoolOutOfMemory();
	}
	
	/**
	 * �����
	 */
	public static void HeapOutOfMemory(){
		int i=1;
		List list = new ArrayList();
		while(true){
			list.add(new String[10000]);
			System.out.println(i++);
		}
	}
	
	/**
	 * ջ���
	 */
	public static void StackOutOfMemory(){
		StackOutOfMemory();
	}
	
	/**
	 * ����ʱ���������
	 */
	public static void RuntimeConstantPoolOutOfMemory(){
		List list = new ArrayList();
		int i = 1;
		while(true){
			list.add(String.valueOf(i++).intern());
		}
	}
}
