package com.jjf.gc;

/**
 * 2017��2��18��14:41:27
 * @author jjf_lenovo
 * jvm����(��ӡGC��־): -XX:+PrintGCDetails
 */
public class ReferenceCountingGc {
	public Object instance = null;
//	private static final int _1MB = 1024*1024;
//	private byte[] bigSize = new byte[2 * _1MB];
	
	public static void testGC(){
		ReferenceCountingGc objA = new ReferenceCountingGc();
		ReferenceCountingGc objB = new ReferenceCountingGc();
		objA.instance = objB;
		objB.instance = objA;
		objA = null;
		objB = null;
		//�������ⷽʽGC,objA �� objB �Ƿ��ܱ�����
		System.gc();
	}
	
	public static void main(String args[]){
		testGC();
	}
}
